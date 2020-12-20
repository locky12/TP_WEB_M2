package com.tp.webtp.Auth;

import com.tp.webtp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppAuthProvider extends DaoAuthenticationProvider {
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

        UserDetails user = userService.loadUserByUsername(auth.getName());

        if (user == null || !bCryptPasswordEncoder.matches(auth.getCredentials().toString(),user.getPassword())) {
            throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
        }
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
