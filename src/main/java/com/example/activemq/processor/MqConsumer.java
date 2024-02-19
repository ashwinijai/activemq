package com.example.activemq.processor;


import com.example.activemq.model.FileRequest;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@Slf4j
public class MqConsumer {

    @Value("${input.queue}")
    String queueName;

    @JmsListener(destination = "${input.queue}")
    public void onMessageReceived(final Message message) throws JMSException, IOException {
        log.info("Message consumed from {} queue", queueName);
        byte[] content = null;
        String fileName = null;
        if (message instanceof ObjectMessage) {
            FileRequest fileModel = message.getBody(FileRequest.class);
            content = fileModel.getContent();
            fileName = fileModel.getFileName();
        } else if (message instanceof BytesMessage) {
            BytesMessage byteMessage = (BytesMessage) message;
            content = new byte[(int) byteMessage.getBodyLength()];
            byteMessage.readBytes(content);
        }
        if (null == fileName)
            fileName = "uploadedfile.pdf";
        if (null != content) {
            File outputFile = new File(fileName);
            log.info("File created with name - {}", fileName);
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(content);
                log.info("File written successfully");
            }
        }
    }
}

