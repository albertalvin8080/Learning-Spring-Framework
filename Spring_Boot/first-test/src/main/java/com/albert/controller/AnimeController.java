package com.albert.controller;

import com.albert.domain.Anime;
import com.albert.util.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("anime")
@Log4j2
public class AnimeController {
    private final DateUtil dateUtil;

    @Autowired
    public AnimeController(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @GetMapping("list")
    public List<Anime> list() {
        //localhost:8080/anime/list
        log.info(dateUtil.formatLocalDateTimeToDBPattern(LocalDateTime.now()));
        return List.of(
                new Anime("Berserk"),
                new Anime("Shinsekai Yori"),
                new Anime("Naruto Shippuden"),
                new Anime("Uma Musume")
        );
    }
}
