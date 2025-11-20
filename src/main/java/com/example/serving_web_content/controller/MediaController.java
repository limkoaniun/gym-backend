package com.example.serving_web_content.controller;

import com.example.serving_web_content.model.Media;
import com.example.serving_web_content.repository.MediaRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static com.example.serving_web_content.model.MediaType.*;

// Annotation
@RestController
@RequestMapping("/api/medias/")
public class MediaController {

    private static final Set<String> ALLOWED = Set.of("image/png", "image/jpeg", "image/svg+xml", "image/webp", "media/MP4");
    private final Tika tika = new Tika();
    @Autowired
    private MediaRepository mediaRepository;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam(required = false) MultipartFile file, @RequestParam(required = false) String name, @RequestParam(required = false) String url) throws IOException {
        if (url != null && !url.isEmpty()) {
            Media urlMedia = new Media();
            urlMedia.setType(WEB);
            urlMedia.setUrl(url);
            if (name != null) {
                urlMedia.setName(name);
            } else {
                int indexOfSlash = url.lastIndexOf('/');
                urlMedia.setName(url.substring(indexOfSlash + 1));
            }
            mediaRepository.save(urlMedia);
            return "Success";
        }
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty.");
        }
        String fileUploadStatus;
        String detectedType = tika.detect(file.getInputStream(), file.getOriginalFilename());
        if (!ALLOWED.contains(detectedType)) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "File type not allowed: " + detectedType);
        }

        String nextFileName = getNextFileName(file.getOriginalFilename());
        String filePath = System.getProperty("user.dir") + "/media/" + nextFileName;

        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());
            fout.close();
            Media media = new Media();
            if (detectedType.contains("image")) {
                media.setType(IMAGE);
            } else {
                media.setType(VIDEO);
            }
            media.setOriginalFileName(nextFileName);
            if (name != null && !name.isEmpty()) {
                media.setName(name);
            } else {
                media.setName(nextFileName.substring(0, nextFileName.lastIndexOf('.')));
            }
            mediaRepository.save(media);
            fileUploadStatus = "File Uploaded Successfully" + detectedType;
        } catch (Exception e) {
            fileUploadStatus = "Error in uploading file: " + e;
        }
        return fileUploadStatus;
    }

    @GetMapping(value = "/getFiles")
    public String[] getFiles() {
        String folderPath = System.getProperty("user.dir") + "/media";
        File directory = new File(folderPath);
        return directory.list();
    }

    @GetMapping(value = "/{mediaId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long mediaId) throws IOException {
        String filePath = System.getProperty("user.dir") + "/media";
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new RuntimeException("Media not found"));
        Path imagePath = Paths.get(filePath).resolve(media.getOriginalFileName());
        String contentType = Files.probeContentType(imagePath); // 自动判断 MIME 类型

        byte[] imageBytes = Files.readAllBytes(imagePath);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(imageBytes);
    }

    @GetMapping(value = "/download/{path:.+}")
    public ResponseEntity downloadFile(@PathVariable("path") String filename) throws IOException {
        String fileUploadpath = System.getProperty("user.dir") + "/media";
        Path path = Paths.get(fileUploadpath).resolve(filename).normalize();

        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(path);
        if (contentType == null) contentType = "application/octet-stream";

        UrlResource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(path.getFileName().toString(), StandardCharsets.UTF_8) + "\"").contentLength(Files.size(path)).body(resource);

    }


    private String getNextFileName(String fileName) {
        List<String> allOriginalFileNames = mediaRepository.findAll().stream().map(Media::getOriginalFileName).toList();
        int n = 1;
        String newFileName = fileName;
        int indexOfDot = newFileName.lastIndexOf('.');
        String prefix = newFileName.substring(0, indexOfDot);
        String postfix = newFileName.substring(indexOfDot);
        while (true) {
            if (allOriginalFileNames.contains(newFileName)) {
                newFileName = prefix + "_" + n + postfix;
            } else {
                return newFileName;
            }
            n++;
        }
    }
}
