package com.seatig.dao;

import com.seatig.domain.User;

import java.util.Map;

public interface UserDao {
    void registerUser(User user);

    User loadUserByUsername(String username);

    int findUserByUserName(String username);

    int updateAvatar(Map<String, Object> map);
}
