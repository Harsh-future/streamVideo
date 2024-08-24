package com.stream.video.impl;

import com.stream.video.entities.Video;
import com.stream.video.repositories.VideoRepository;
import com.stream.video.services.VideoService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);


    @Value("${files.video}")
    String DIR;

    @Autowired
    private VideoRepository videoRepository;

    @PostConstruct
    public void init(){

        File file = new File(DIR);

        if(!file.exists()){
            file.mkdir();
            System.out.println("Folder created");
        }else{
            System.out.println("folder already exists");
        }
    }

    @Override
    public Video save(Video video, MultipartFile file) {

        //original file name
        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();

            //file path
            String cleanFileName = StringUtils.cleanPath(fileName);

            //folder path
            String cleanFolder = StringUtils.cleanPath(DIR);

            //folder path with filename
            Path path = Paths.get(cleanFolder,cleanFileName);

            System.out.println("Path for the uploaded file " + path);

            //copy file to the folder
            Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING);

            //save the video metadata
            video.setContentType(contentType);
            video.setFilePath(path.toString());
            videoRepository.save(video);

            return video;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Video get(String videoId) {
        return null;
    }

    @Override
    public Video getByTitle(String title) {
        return null;
    }

    @Override
    public List<Video> getAll() {
        return List.of();
    }
}
