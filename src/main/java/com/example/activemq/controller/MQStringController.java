package com.example.activemq.controller;

import com.example.activemq.processor.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MQStringController {
    @Autowired
    MqProducer producer;

    @PostMapping("/sendString")
    public ResponseEntity<String> sendStringMessage(@RequestBody String message) throws IOException {
        producer.sendStringMessageToMq(message);
        return new ResponseEntity<>("Message sent successfully", HttpStatusCode.valueOf(200));

    }
}
