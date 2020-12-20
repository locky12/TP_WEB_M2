package com.tp.webtp;

import com.tp.webtp.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebtpApplication {

	@Autowired
	UserDAO userDao;
	@Autowired
	ShareDao shareDao;
	@Autowired
	SerieDao serieDao;
	@Autowired
	EventDao eventDao;
	@Autowired
	TagDao tagDao;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WebtpApplication.class, args);
	}
}
