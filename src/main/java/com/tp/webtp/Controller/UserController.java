package com.tp.webtp.Controller;

import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.entity.User;
import com.tp.webtp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    UserDAO userDAO;
    @Autowired
    UserService userService;

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
        user = userDAO.save(userR);

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
