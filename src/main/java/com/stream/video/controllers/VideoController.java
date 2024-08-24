package com.stream.video.controllers;


import com.stream.video.entities.Video;
import com.stream.video.payload.CustomMessage;
import com.stream.video.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description
            ){

        if(file.isEmpty()){
            new ResponseEntity<>("Please select the file to be uploaded", HttpStatus.BAD_REQUEST);
        }

        String contentType = file.getContentType();
        if(!"video/mp4".equals(contentType)){
            return new ResponseEntity<>("Only .mp4 files are allowed", HttpStatus.BAD_REQUEST);
        }

        String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.toLowerCase().endsWith(".mp4")) {
            return new ResponseEntity<>("Only .mp4 files are allowed.", HttpStatus.BAD_REQUEST);
        }

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoId(UUID.randomUUID().toString());
        videoService.save(video,file);

        return new ResponseEntity<>(video,HttpStatus.OK);
    }

}
