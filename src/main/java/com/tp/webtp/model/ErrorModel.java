package com.tp.webtp.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorModel {

    public static ModelAndView createErrorModel(HttpStatus status){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(status);
        modelAndView.addObject("errorCode", status.value());
        modelAndView.addObject("errorMsg", status.getReasonPhrase());
        return modelAndView;

    }



}
