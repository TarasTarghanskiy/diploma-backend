package server.service;

import server.entity.UserEntity;

public interface UserService {
    UserEntity getUserByUserID(long userID);
}
