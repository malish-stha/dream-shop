package com.dailycodework.dreamshops.controller;


import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try{
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
        }
        return ResponseEntity.ok(new ApiResponse("Images uploaded successfully!", null));
    }

    public ResponseEntity<ApiResponse> downloadImage(@PathVariable Long imageId){}
}
