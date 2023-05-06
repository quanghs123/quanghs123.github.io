package com.example.backend.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@Controller
public class ImageUploadController {
    @RequestMapping(value = "getimage/brand/{image}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getBrandImage(@PathVariable String image){
        return getByteArrayResourceResponseEntity(image,"brand");
    }

    @RequestMapping(value = "getimage/product/{image}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getProductImage(@PathVariable String image){
        return getByteArrayResourceResponseEntity(image,"product");
    }

    private static ResponseEntity<ByteArrayResource> getByteArrayResourceResponseEntity(String image,String str) {
        if(!image.equals("")|| image != null){
            try {
                Path fileName = Paths.get("uploads/"+str, image);
                byte[] buffer = Files.readAllBytes(fileName);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/png"))
                        .body(byteArrayResource);
            }catch (Exception ex){

            }
        }
        return ResponseEntity.badRequest().build();
    }
}
