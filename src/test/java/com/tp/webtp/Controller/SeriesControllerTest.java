package com.tp.webtp.Controller;

import com.tp.webtp.dao.SerieDao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SeriesControllerTest {

    @Mock
    SerieDao service;

    @InjectMocks
    SeriesController controller;


    @Test
    void getSeriesOwned() {
    }

    @Test
    void getSeriesShared() {
    }

    @Test
    void getSerie() {
    }

    @Test
    void createSerie() {
    }

    @Test
    void shareSerie() {
    }
}