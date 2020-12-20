package com.tp.webtp.Controller;

import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.entity.Role;
import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Share;
import com.tp.webtp.entity.User;
import com.tp.webtp.model.ErrorModel;
import com.tp.webtp.model.Series;
import com.tp.webtp.service.SerieService;
import com.tp.webtp.service.ShareService;
import com.tp.webtp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Controller
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    SerieDao serieDao;
    @Autowired
    ShareDao shareDao;
    @Autowired
    UserDAO userDao;
    @Autowired
    ShareService shareService;
    @Autowired
    UserService userService;
    @Autowired
    SerieService serieService;

    private static final String  CACHE_CONTROL_CHAMPS = "Cache-control";
    private static final String  CACHE_CONTROL_VALUE = CacheControl.maxAge(Duration.ofDays(1)).cachePrivate().noTransform().mustRevalidate().getHeaderValue();

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ModelAndView getSeries(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response) {

        Series series = new Series(shareService.getSeriesByUserId(user.getId()));
        for (Serie serie : series.getList()){
            Link thisLink = linkTo(this.getClass()).slash(serie.getId()).withSelfRel();
            Link serieLink = linkTo(methodOn(this.getClass()).getSeries(user,request,response)).slash(serie.getId()).slash("events").withRel("serie");
            serie.add(serieLink);
            serie.add(thisLink);
        }

        ModelAndView modelAndView = new ModelAndView("series");
        modelAndView.addObject("series",series);
        response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
        return modelAndView;
    }

    @GetMapping(value = "/owned", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ModelAndView getSeriesOwned(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response) {

        Series series = new Series(shareService.getSeriesByUserIdAndRole(user.getId(), Role.OWNER));
        for (Serie serie : series.getList()){
            Link thisLink = linkTo(this.getClass()).slash(serie.getId()).withSelfRel();
            Link serieLink = linkTo(methodOn(this.getClass()).getSeries(user,request,response)).slash(serie.getId()).slash("events").withRel("serie");
            serie.add(serieLink);
            serie.add(thisLink);
        }

        ModelAndView modelAndView = new ModelAndView("series");
        modelAndView.addObject("series",series);
        response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
        return modelAndView;
    }

    @GetMapping(value = "/shared", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ModelAndView getSeriesShared(@AuthenticationPrincipal User user,HttpServletRequest request, HttpServletResponse response) {

        Series series = new Series(shareService.getSeriesByUserIdAndNotRole(user.getId(), Role.OWNER));

        for (Serie serie : series.getList()){
            Link thisLink = linkTo(this.getClass()).slash(serie.getId()).withSelfRel();
            Link serieLink = linkTo(methodOn(this.getClass()).getSeries(user,request,response)).slash(serie.getId()).slash("events").withRel("serie");
            serie.add(serieLink);
            serie.add(thisLink);
        }

        ModelAndView modelAndView = new ModelAndView("series");
        modelAndView.addObject("series",series);
        response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
        return modelAndView;
    }

    @GetMapping(value = "/{idSerie}")
    public ModelAndView getSerie(@AuthenticationPrincipal User user, HttpServletResponse response, HttpServletRequest request, @PathVariable("idSerie") UUID idSerie) {

        if ( !StringUtils.hasText(idSerie.toString()) )
            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);

        Serie serie = shareService.getFromUserIdAndSerieId(user.getId(), idSerie);

        if(serie == null)
            return ErrorModel.createErrorModel(HttpStatus.NOT_FOUND);

        serie.add(linkTo(methodOn(SeriesController.class).getSerie(user,response,request,idSerie)).withSelfRel());
        ModelAndView modelAndView = new ModelAndView("serie");
        modelAndView.addObject("serie", serie);
        response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
        return modelAndView;
    }

    @PostMapping()
    public ResponseEntity<Void> createSerie(@AuthenticationPrincipal User user, HttpServletResponse response, HttpServletRequest request, @RequestBody Serie serieR) {

        if (serieR == null)
            return ResponseEntity.badRequest().build();

        Serie serie = serieDao.save(serieR);
        shareDao.save(new Share(userService.getUserById(user.getId()), serie, Role.OWNER));

        return  ResponseEntity.created(URI.create("/series/" + serie.getId())).build();
    }

    @PostMapping("/{idSerie}")
    public ResponseEntity<Void> shareSerie(@AuthenticationPrincipal User user,HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, String> idAndRole, @PathVariable("idSerie") UUID idSerie) {

        String idUserToShare = idAndRole.get("idUser");
        String role = idAndRole.get("role");
        if (!StringUtils.hasText(idUserToShare) || !StringUtils.hasText(role))
            return ResponseEntity.badRequest().build();

        User userToShare = userService.getUserById(UUID.fromString(idUserToShare));
        Serie serieToShare = serieService.getSerieBySerieId(idSerie);

        if(userToShare == null || serieToShare == null)
            return ResponseEntity.notFound().build();

        if(shareService.getFromUserIdAndSerieIdAndRole(user.getId(), idSerie, Role.OWNER) == null)
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        shareDao.save(new Share(userToShare, serieToShare, Role.valueOf(role.toUpperCase())));

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/t/test1", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE })
    public List<Share> readCookie(){
        Serie serie = new Serie();
        serie.setId(UUID.randomUUID());
        serie.setDescription("desc");
        serie.setTitle("titre");

        return shareDao.getAll();
    }
}