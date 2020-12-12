package com.tp.webtp.Controller;

import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/tags")
public class TagController {


    @Autowired
    TagDao tagDao;

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable("id") UUID id) {
        if ( !StringUtils.hasText(String.valueOf(id)) )
            return ResponseEntity.badRequest().build();
        Optional<Tag> tag;
        tag =  tagDao.findById(id);

        if ( tag == null )
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tag.get()) ;
    }

    @PostMapping()
    public ResponseEntity<Void> createTag(@RequestBody Tag tag) {
        if ( tag == null )
            return ResponseEntity.badRequest().build();
        Tag savedTag = tagDao.save(tag);
        return ResponseEntity.created(URI.create("/Tags/" + savedTag.getTagName())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTag(@PathVariable("id") UUID id, @RequestBody Tag tag) {
        if ( !StringUtils.hasText(String.valueOf(id)) )
            return ResponseEntity.badRequest().build();
        if ( tag == null )
            return ResponseEntity.badRequest().build();
        if ( !tag.getTagName().equals(id) )
            return ResponseEntity.badRequest().build();



        if ( tagDao.findById(id) == null )
            return ResponseEntity.notFound().build();


        tagDao.save(tag);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") UUID uuid) {
        if ( !StringUtils.hasText(String.valueOf(uuid)) )
            return ResponseEntity.badRequest().build();

        tagDao.deleteById(uuid);
        return ResponseEntity.ok().build();

    }
}
