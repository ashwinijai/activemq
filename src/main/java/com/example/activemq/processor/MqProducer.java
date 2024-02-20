package com.example.activemq.processor;


import com.example.activemq.model.FileRequest;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Slf4j
public class MqProducer {
    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${input.queue}")
    String queueName;

    public void sendObjectMessageToMq(MultipartFile file) throws IOException{
        FileRequest fileModel = new FileRequest();
        fileModel.setContent(file.getBytes());
        fileModel.setFileName(file.getOriginalFilename());
        jmsTemplate.convertAndSend(queueName,fileModel);
        log.info("Message sent to MQ");
    }

    public void sendBytesMessageToMq(MultipartFile file) throws IOException, JMSException {
        byte[] content = file.getBytes();
        jmsTemplate.send(queueName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                BytesMessage bytesMessage = session.createBytesMessage();
                bytesMessage.setStringProperty("fileName", file.getOriginalFilename());
                bytesMessage.writeBytes(content);
                return bytesMessage;
            }
        });
        log.info("Message sent to MQ");
    }
}
