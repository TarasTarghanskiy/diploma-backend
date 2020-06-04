package server.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import server.DTO.AccountDTO;
import server.DTO.UserDTO;
import server.entity.UserEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import static server.util.Constant.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        System.out.println(IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8));
        try(BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            JSONObject jsonObject = new JSONObject(streamReader.readLine());
//            AccountDTO accountDTO = new ObjectMapper().readValue(request.getInputStream(), AccountDTO.class);
//            System.out.println(jsonObject.get("accountName"));
//            System.out.println(jsonObject.get("accountPassword"));
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jsonObject.get("name"),
                            jsonObject.get("password"),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY.getBytes()));
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(new JSONObject("{" +
                "\"" + HEADER_STRING + "\":\"" + TOKEN_PREFIX + token + "\"," +
                "\"expiresIn\":\"" + EXPIRATION_TIME + "\" " +
                "}"));
        printWriter.flush();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
