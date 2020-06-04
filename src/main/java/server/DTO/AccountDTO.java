package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {

    private long accountID;

    private String accountName;

    private String accountPassword;

    private boolean enable;

    private byte[] accountAvatar;
}
