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

	public static void main(String[] args) {
		SpringApplication.run(WebtpApplication.class, args);
	}

	@Bean
	public void test(){

		User user = userDao.save(new User(UUID.fromString("9c06c672-4079-11eb-b378-0242ac130002"),"email user1"));
		User user2 = userDao.save(new User(UUID.fromString("ede87c00-407a-11eb-b378-0242ac130002"), "email user2"));
		System.out.println("User 1 : " + user.getId());

		Serie serie = serieDao.save(new Serie("Titre série 1 user 1", "desc"));
		Serie serie2 = serieDao.save(new Serie("Titre série 2 user 1", "desc"));

		Serie serie5 = serieDao.save(new Serie("Titre série 1 user 2", "desc"));
		Serie serie6 = serieDao.save(new Serie("Titre série 2 user 2", "desc"));

		Share share = shareDao.save(new Share(user, serie, Role.OWNER));
		Share share2 = shareDao.save(new Share(user, serie2, Role.OWNER));

		Share share5 = shareDao.save(new Share(user2, serie5, Role.OWNER));
		Share share6 = shareDao.save(new Share(user2, serie6, Role.OWNER));

		Share share7 = shareDao.save(new Share(user2, serie, Role.WRITE));
		Share share8 = shareDao.save(new Share(user, serie5, Role.READ));

		Event event = eventDao.save(new Event(serie, "valeur 54", "commentaire"));

		Event event2 = eventDao.save(new Event(serie5, "valeur 67", "commentaire"));
		Event event3 = eventDao.save(new Event(serie5, "valeur 12", "commentaire"));

		Event event4 = eventDao.save(new Event(serie, "valeur 4", "commentaire"));
		Event event5 = eventDao.save(new Event(serie5, "valeur 86", "commentaire"));

		Tag tag = tagDao.save(new Tag("tag", event));
		Tag tag5 = tagDao.save(new Tag("tag", event5));

		Tag tag2 = tagDao.save(new Tag("tag1", event2));

		Tag tag3 = tagDao.save(new Tag("tag2", event2));
		Tag tag4 = tagDao.save(new Tag("tag2", event4));

	}

}
