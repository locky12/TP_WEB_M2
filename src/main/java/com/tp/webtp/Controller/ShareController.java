package com.tp.webtp.Controller;

import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.entity.Share;
import com.tp.webtp.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/shares")
public class ShareController {

    @Autowired
    ShareDao shareDao;

    @GetMapping("/{id}")
    public ResponseEntity<Share> getTag(@PathVariable("id") UUID id) {

        if ( !StringUtils.hasText(String.valueOf(id)) )
            return ResponseEntity.badRequest().build();

        Optional<Share> share;
        share =  shareDao.findById(id);

        if ( share == null )
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(share.get());
    }
}
