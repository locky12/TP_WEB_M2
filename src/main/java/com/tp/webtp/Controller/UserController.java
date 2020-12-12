package com.tp.webtp.Controller;

import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
public class UserController {

    @Autowired
    UserDAO userDAO;


    @PostMapping(path = "/users")
    public ResponseEntity<Void> createUser(HttpServletResponse response, @RequestBody User userR) {
        User user;
        if (userR == null){
            return ResponseEntity.badRequest().build();
        }
        user = userDAO.save(userR);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(user.getId()).toUri();

        Cookie cookie = new Cookie("user", user.getId().toString());
        cookie.setMaxAge(24 * 60); // 1 day
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return  ResponseEntity.created(location).build();
    }


    @GetMapping(path = "/users/test1")
    public String readCookie(@CookieValue(value = "user") String uuid){
        return  uuid;
    }

}
