package com.stream.video.services;

import com.stream.video.entities.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    //save video
    Video save(Video video, MultipartFile file);

    //get video  By id
    Video get(String videoId);

    //get video by title
    Video getByTitle(String title);

    //get All videos
    List<Video> getAll();
}


