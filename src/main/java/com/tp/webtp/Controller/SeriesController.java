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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.core.ControllerEntityLinks;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Controller
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    SerieDao serieDao;
    @Autowired
    ShareDao shareDao;
    @Autowired
    UserDAO userDao;

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ModelAndView getSeries(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);

        UUID idUser = UUID.fromString(cookie.getValue());

        Series series = new Series(shareDao.getSeriesByUserId(idUser));
        for (Serie serie : series.getList()){
            UUID idSerie = serie.getId();
            Link thisLink = linkTo(this.getClass()).slash(serie.getId()).withSelfRel();
            Link serieLink = linkTo(methodOn(this.getClass()).getSeries(request,response)).slash(serie.getId()).slash("events").withRel("serie");
            serie.add(serieLink);
            serie.add(thisLink);
        }

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        ModelAndView modelAndView = new ModelAndView("series");
        modelAndView.addObject("series",series);


        return modelAndView;
    }

//    @GetMapping(value = "/{id}")
////    @RequestMapping(value = "/{id}")
//    public CollectionModel<Serie> getSerieH(HttpServletResponse response, HttpServletRequest request, @PathVariable("id") UUID idSerie) {
//
//        Cookie cookie = WebUtils.getCookie(request, "user");
////
////        if(cookie == null)
////            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);
////
////        if ( !StringUtils.hasText(idSerie.toString()) )
////            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);
//
//        UUID idUser = UUID.fromString(cookie.getValue());
//
//        Optional<Serie> serie = shareDao.getFromUserIdAndSerieId(idUser, idSerie);
//
////        if(!serie.isPresent())
////            return ErrorModel.createErrorModel(HttpStatus.NOT_FOUND);
//
//        ModelAndView modelAndView = new ModelAndView("serie");
//        modelAndView.addObject("serie", serie.get());
//        Serie seriez = serie.get();
//        seriez.
//        cookie.setMaxAge(5000);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        Link link = linkTo(methodOn(SeriesController.class).getSerieH(response,request,idSerie));
//        CollectionModel<Serie> result = new CollectionModel<Serie>;
//        return  result ;
////        return ResponseEntity.ok(modelAndView);
//    }
//    @GetMapping(value = "/{Id}/orders", produces = { "application/hal+json" })
//    public CollectionModel<Order> getOrdersForCustomer(@PathVariable final String customerId) {
//        List<Order> orders = orderService.getAllOrdersForCustomer(customerId);
//        for (final Order order : orders) {
//            Link selfLink = linkTo(methodOn(CustomerController.class)
//                    .getOrderById(customerId, order.getOrderId())).withSelfRel();
//            order.add(selfLink);
//        }
//
//        Link link = linkTo(methodOn(CustomerController.class)
//                .getOrdersForCustomer(customerId)).withSelfRel();
//        CollectionModel<Order> result = new CollectionModel<>(orders, link);
//        return result;
//    }

    @GetMapping(value = "/owned", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Serie>> getSeriesOwned(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(shareDao.getSeriesByUserIdAndRole(UUID.fromString(cookie.getValue()), Role.OWNER));
    }

    @GetMapping(value = "/shared", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Serie>> getSeriesShared(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(shareDao.getSeriesByUserIdAndNotRole(UUID.fromString(cookie.getValue()), Role.OWNER));
    }

    @GetMapping(value = "/{id}")
//    @RequestMapping(value = "/{id}")
    public ModelAndView getSerie(HttpServletResponse response, HttpServletRequest request, @PathVariable("id") UUID idSerie) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);

        if ( !StringUtils.hasText(idSerie.toString()) )
            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);

        UUID idUser = UUID.fromString(cookie.getValue());

        Optional<Serie> serie = shareDao.getFromUserIdAndSerieId(idUser, idSerie);

        if(!serie.isPresent())
            return ErrorModel.createErrorModel(HttpStatus.NOT_FOUND);
        Serie serie1 = serie.get();
        serie1.add(linkTo(methodOn(SeriesController.class).getSerie(response,request,idSerie)).withSelfRel());
        ModelAndView modelAndView = new ModelAndView("serie");
        modelAndView.addObject("serie", serie.get());

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return modelAndView;
//        return ResponseEntity.ok(modelAndView);
    }

//    @GetMapping(value = "/{id}")
////    @RequestMapping(value = "/{id}")
//    public HttpEntity<Serie> getSerie(HttpServletResponse response, HttpServletRequest request, @PathVariable("id") UUID idSerie) {
//
//        Cookie cookie = WebUtils.getCookie(request, "user");
//
////        if(cookie == null)
////            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);
////
////        if ( !StringUtils.hasText(idSerie.toString()) )
////            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);
//
//        UUID idUser = UUID.fromString(cookie.getValue());
//
//        Optional<Serie> serie = shareDao.getFromUserIdAndSerieId(idUser, idSerie);
//
////        if(!serie.isPresent())
////            return ErrorModel.createErrorModel(HttpStatus.NOT_FOUND);
//        Serie serie1 = serie.get();
//        serie1.add(linkTo(methodOn(SeriesController.class).getSerie(response,request,idSerie)).withSelfRel());
//        cookie.setMaxAge(5000);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        return new ResponseEntity<>(serie1,HttpStatus.OK);
////        return ResponseEntity.ok(modelAndView);
//    }




    @PostMapping()
    public ResponseEntity<Void> createSerie(HttpServletResponse response, HttpServletRequest request, @RequestBody Serie serieR) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (serieR == null)
            return ResponseEntity.badRequest().build();

        UUID userId = UUID.fromString(cookie.getValue());

        Serie serie = serieDao.save(serieR);
        shareDao.save(new Share(userDao.findById(userId).get(), serie, Role.OWNER));

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return  ResponseEntity.created(URI.create("/series/" + serie.getId())).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> shareSerie(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, String> idAndRole, @PathVariable("id") UUID id) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID idUserSharing = UUID.fromString(cookie.getValue());

        String idUserToShare = idAndRole.get("idUser");
        String role = idAndRole.get("role");
        if (!StringUtils.hasText(idUserToShare) || !StringUtils.hasText(role))
            return ResponseEntity.badRequest().build();

        Optional<User> userToShare = userDao.findById(UUID.fromString(idUserToShare));
        Optional<Serie> serieToShare = serieDao.findById(id);

        if(!userToShare.isPresent() || !serieToShare.isPresent())
            return ResponseEntity.notFound().build();

        Share share = shareDao.save(new Share(userToShare.get(), serieToShare.get(), Role.valueOf(role.toUpperCase())));

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);

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
