package com.example.activemq.controller;

import com.example.activemq.processor.MqProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileController {

    @Autowired
    MqProducer mqProducer;

    @PostMapping("/sendObjectToMq")
    public ResponseEntity<String> uploadObject(@RequestPart("file") MultipartFile file) {
        String response;
        try {
            mqProducer.sendObjectMessageToMq(file);
            response = "File sent to MQ successfully";
        } catch (Exception e) {
            log.error("Error while processing the file");
            response = "Error while processing the file";
        }
        return ResponseEntity.ok().body(response);
    } @PostMapping("/sendBytesToMq")
    public ResponseEntity<String> uploadBytes(@RequestPart("file") MultipartFile file) {
        String response;
        try {
            mqProducer.sendBytesMessageToMq(file);
            response = "File sent to MQ successfully";
        } catch (Exception e) {
            log.error("Error while processing the file");
            response = "Error while processing the file";
        }
        return ResponseEntity.ok().body(response);
    }


}


