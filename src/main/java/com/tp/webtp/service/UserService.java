package com.tp.webtp.service;

import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User getUserById(UUID userId){
        Optional<User> user = userDAO.findById(userId);
        return user.orElse(null);
    }
}
