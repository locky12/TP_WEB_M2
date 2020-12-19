package com.tp.webtp.Controller;

import com.tp.webtp.entity.Share;
import com.tp.webtp.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/shares")
public class ShareController {

    @Autowired
    ShareService shareService;

    @GetMapping("/{idShare}")
    public ResponseEntity<Share> getShare(@PathVariable("idShare") UUID idShare) {

        if ( !StringUtils.hasText(String.valueOf(idShare)) )
            return ResponseEntity.badRequest().build();

        Share share =  shareService.getShareByShareId(idShare);

        if (share == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(share);
    }
}
