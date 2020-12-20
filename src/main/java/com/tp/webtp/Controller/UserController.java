package com.tp.webtp.Controller;

import com.tp.webtp.configuration.WebSecurityConfig;
import com.tp.webtp.entity.User;
import com.tp.webtp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    WebSecurityConfig webSecurityConfig;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(HttpServletResponse response, @PathVariable("id") UUID id) {

        if ( !StringUtils.hasText(id.toString()) )
            return ResponseEntity.badRequest().build();

        User user = userService.getUserById(id);

        if ( user == null )
            return ResponseEntity.notFound().build();

        Cookie cookie = new Cookie("user", user.getId().toString());
        cookie.setMaxAge(5000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/cookie/deletecookie")
    public ResponseEntity<Void> deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping()
    public ResponseEntity<Void> createUser(HttpServletResponse response, @RequestBody User userR) {
        User user;
        if (userR == null){
            return ResponseEntity.badRequest().build();
        }
        userR.setPassword(bCryptPasswordEncoder.encode(userR.getPassword()));
        user = userService.saveUser(userR);

        Cookie cookie = new Cookie("user", user.getId().toString());
        cookie.setMaxAge(5000); // 1 day
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.created(URI.create("/users/" + user.getId())).build();
    }
    /*
    @GetMapping(path = "/users/test1")
    public String readCookie(@CookieValue(value = "user") String uuid){
        return  uuid;
    }
    */
}
