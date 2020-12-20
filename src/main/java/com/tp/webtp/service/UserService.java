package com.tp.webtp.service;

import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDAO userDao;

    public User getUserById(UUID userId){
        Assert.notNull(userId, "idUser cannot be null");
        Optional<User> user = userDao.findById(userId);
        return user.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Assert.hasText(userName, "userName cannot be null");
        UserDetails user = userDao.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        return user;
    }

    public User saveUser(User user){
        Assert.notNull(user, "user cannot be null");
        return userDao.save(user);
    }

    public void deleteUser(User user){
        Assert.notNull(user, "user cannot be null");
        userDao.delete(user);
    }
}
