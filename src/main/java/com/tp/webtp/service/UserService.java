package com.tp.webtp.service;

import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    public User getUserById(UUID userId){
        Optional<User> user = userDAO.findById(userId);
        return user.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Objects.requireNonNull(s);
        System.out.println();
        UserDetails user = userDAO.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return user;
    }
}
