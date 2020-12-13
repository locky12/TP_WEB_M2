package com.tp.webtp.Controller;

import com.tp.webtp.dao.EventDao;
import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.entity.*;
import org.apache.logging.log4j.util.SystemPropertiesPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    SerieDao serieDao;
    @Autowired
    ShareDao shareDao;
    @Autowired
    UserDAO userDao;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Serie>> getSeries(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        List<Share> shareList = shareDao.findByUserId(idUser);

        List<Serie> listSerie = new ArrayList<>();
        for(Share s : shareList){
            listSerie.add(s.getSerie());
        }

        return ResponseEntity.ok(listSerie);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Serie> getSerie(@PathVariable("id") UUID id) {

        if ( !StringUtils.hasText(id.toString()) )
            return ResponseEntity.badRequest().build();

        Optional<Serie> serie;
        serie =  serieDao.findById(id);

        if (!serie.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(serie.get());
    }

    @PostMapping()
    public ResponseEntity<Void> createSerie(HttpServletResponse response, HttpServletRequest request, @RequestBody Serie serieR) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        if (serieR == null)
            return ResponseEntity.badRequest().build();

        Serie serie;
        serie = serieDao.save(serieR);

        shareDao.save(new Share(userDao.findById(UUID.fromString(cookie.getValue())).get(), serie, true));

        cookie.setMaxAge(5000);
        response.addCookie(cookie);

        return  ResponseEntity.created(URI.create("/series/" + serie.getId())).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> shareSerie(HttpServletResponse response, HttpServletRequest request, @RequestBody UUID idUser, @RequestBody Boolean write, @PathVariable("id") UUID id) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        if (idUser == null)
            return ResponseEntity.badRequest().build();

        Optional<User> user;
        user = userDao.findById(idUser);

        if(!user.isPresent())
            return ResponseEntity.notFound().build();

        Share share = shareDao.save(new Share(user.get(), serieDao.findById(id).get(), write));

        cookie.setMaxAge(5000);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/t/test1", produces = { "application/json", "application/xml" })
    public Serie readCookie(){
        Serie serie = new Serie();
        serie.setDescription("desc");
        serie.setTitle("titre");
        return serie;
    }
}
