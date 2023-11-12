package com.example.kafkaprovider.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.example.kafkaprovider.config.KafkaTopicConfig.SANDBOX_TOPIC;

@RestController
@RequestMapping("kafka/")
public class KafkaProviderController {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("message")
    ResponseEntity sendMessage(@RequestBody MessageRequest message){
        if(Objects.isNull(message) || Objects.isNull(message.getMessage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message was not correctly sent");
        }
        kafkaTemplate.send(SANDBOX_TOPIC, message.getMessage());
        return ResponseEntity.ok("Message was sent");
    }

}
