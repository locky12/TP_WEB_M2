package com.tp.webtp;

import com.tp.webtp.dao.*;
import com.tp.webtp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

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

	@Bean
	public void test(){

		User user1 = userDao.save(new User(UUID.fromString("9c06c672-4079-11eb-b378-0242ac130002"),"email user1"));
		User user2 = userDao.save(new User(UUID.fromString("ede87c00-407a-11eb-b378-0242ac130002"), "email user2"));

		Serie serie1User1 = serieDao.save(new Serie("Titre série 1 user 1", "desc"));
		Serie serie2User1 = serieDao.save(new Serie("Titre série 2 user 1", "desc"));

		Serie serie1User2 = serieDao.save(new Serie("Titre série 1 user 2", "desc"));
		Serie serie2User2 = serieDao.save(new Serie("Titre série 2 user 2", "desc"));

		Share share = shareDao.save(new Share(user1, serie1User1, Role.OWNER));
		Share share2 = shareDao.save(new Share(user1, serie2User1, Role.OWNER));

		Share share5 = shareDao.save(new Share(user2, serie1User2, Role.OWNER));
		Share share6 = shareDao.save(new Share(user2, serie2User2, Role.OWNER));

		Share share7 = shareDao.save(new Share(user2, serie1User1, Role.WRITE));
		Share share8 = shareDao.save(new Share(user1, serie1User2, Role.READ));

		Event eventSerie1User1 = eventDao.save(new Event(serie1User1, "valeur 54", "commentaire"));

		Event event1Serie1User2 = eventDao.save(new Event(serie1User2, "valeur 67", "commentaire"));
		Event event2Serie1User2 = eventDao.save(new Event(serie1User2, "valeur 12", "commentaire"));

		Event event4 = eventDao.save(new Event(serie1User1, "valeur 4", "commentaire"));
		Event event5 = eventDao.save(new Event(serie1User2, "valeur 86", "commentaire"));

		Tag tag = tagDao.save(new Tag("tag", eventSerie1User1));
		Tag tag5 = tagDao.save(new Tag("tag", event5));

		Tag tag2 = tagDao.save(new Tag("tag1", event1Serie1User2));

		Tag tag3 = tagDao.save(new Tag("tag2", event1Serie1User2));
		Tag tag4 = tagDao.save(new Tag("tag2", event4));
	}

	public static void main(String[] args) {
		SpringApplication.run(WebtpApplication.class, args);
	}
}
