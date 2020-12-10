package com.tp.webtp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class Controller {


    @GetMapping("/test")
    public String get () {
        return  new Date().toString();
    }
}
