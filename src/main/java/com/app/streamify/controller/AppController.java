package com.app.streamify.controller;

import com.app.streamify.dto.MediaDTO;
import com.app.streamify.service.AppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/search/{keyword}")
    public List<MediaDTO> searchLibrary(@PathVariable String keyword){
        return appService.searchLibrary(keyword);
    }
}
