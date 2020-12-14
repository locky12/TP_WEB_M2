package com.tp.webtp.Controller;

import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Share;
import com.tp.webtp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    SerieDao serieDao;
    @Autowired
    ShareDao shareDao;
    @Autowired
    UserDAO userDao;

    /**
     * Toutes les séries d'un user (possédées et partagées)
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Serie>> getSeries(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        //ajout des listes possédées
        List<Serie> listSerie = new ArrayList<>(serieDao.findByIdOwner(idUser));

        //ajout des listes partagées
        List<Share> shareList = shareDao.findByUserId(idUser);
        for(Share s : shareList){
            listSerie.add(s.getSerie());
        }

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(listSerie);
    }

    /**
     * Séries possédées
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/owned", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Serie>> getSeriesOwned(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(serieDao.findByIdOwner(UUID.fromString(cookie.getValue())));
    }

    /**
     * Séries partagées à cet user
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/shared", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Serie>> getSeriesShared(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        List<Serie> listSerie = new ArrayList<>();

        List<Share> shareList = shareDao.findByUserId(idUser);
        for(Share s : shareList){
            listSerie.add(s.getSerie());
        }

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(listSerie);
    }

    /**
     * Série d'id id
     * @param idSerie
     * @return
     */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Serie> getSerie(HttpServletResponse response, HttpServletRequest request, @PathVariable("id") UUID idSerie) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        if ( !StringUtils.hasText(idSerie.toString()) )
            return ResponseEntity.badRequest().build();

        UUID idUser = UUID.fromString(cookie.getValue());

        Optional<Serie> serie = serieDao.findById(idSerie);

        //la série existe ?
        if (!serie.isPresent())
            return ResponseEntity.notFound().build();

        List<Share> shareList = shareDao.findByUserId(idUser);
        for(Share s : shareList){
            //la série est partagée à l'user
            if(idSerie.equals(s.getSerie().getId())) {
                cookie.setMaxAge(5000);
                cookie.setPath("/");
                response.addCookie(cookie);

                return ResponseEntity.ok(serie.get());
            }
        }

        //la série n'est pas partagée et n'appartient pas à l'user
        if(!idUser.equals(serie.get().getIdOwner()))
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        //la série appartient à l'user
        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);

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

        serieR.setIdOwner(UUID.fromString(cookie.getValue()));
        Serie serie = serieDao.save(serieR);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return  ResponseEntity.created(URI.create("/series/" + serie.getId())).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> shareSerie(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, String> idAndWrite, @PathVariable("id") UUID id) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUserSharing = UUID.fromString(cookie.getValue());

        String idUserToShare = idAndWrite.get("idUser");
        String write = idAndWrite.get("write");
        if (!StringUtils.hasText(idUserToShare) || !StringUtils.hasText(write))
            return ResponseEntity.badRequest().build();

        Optional<User> userToShare = userDao.findById(UUID.fromString(idUserToShare));
        Optional<Serie> serieToShare = serieDao.findById(id);

        if(!userToShare.isPresent() || !serieToShare.isPresent())
            return ResponseEntity.notFound().build();

        if(!idUserSharing.equals(serieToShare.get().getIdOwner()))
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        Share share = shareDao.save(new Share(userToShare.get(), serieToShare.get(), Boolean.valueOf(write)));

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/t/test1", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE })
    public Serie readCookie(){
        Serie serie = new Serie();
        serie.setId(UUID.randomUUID());
        serie.setDescription("desc");
        serie.setTitle("titre");
        return serie;
    }
}
