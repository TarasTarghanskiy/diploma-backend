package server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.DTO.UserDTO;
import server.entity.UserEntity;
import server.repository.UserRepository;
import server.service.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
@Autowired
    private UserRepository userRepository;


    @Override
    public UserEntity getUserByUserID(long userID) {
        return userRepository.getOne(userID);
    }
}
