package com.tp.webtp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @GetMapping("/test")
    public String get () {
        return "salut le monde";
    }
}
