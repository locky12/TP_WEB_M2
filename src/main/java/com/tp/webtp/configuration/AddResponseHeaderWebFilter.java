//package com.tp.webtp.configuration;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter("/*")
//public class AddResponseHeaderWebFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.addHeader("Cache-Control" ,"max-age=800");
////        httpServletResponse.addHeader("Cache-Control", "no-store");
//        //httpServletResponse.setHeader("headerName", "headerValue");
//        chain.doFilter(request, response);
//    }
//}
