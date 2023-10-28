package com.kafka.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.kafka.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class MessageController {

    private final ProducerService service;

    @PostMapping(path = "/cat/{partition}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendCatMessage(@RequestBody JsonNode catsInfo, @PathVariable int partition){
        service.sendCatMessage(catsInfo.toString(), partition);
        return ResponseEntity.ok(catsInfo.toString());
    }
    @PostMapping(path = "/dog", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendDogMessage(@RequestBody JsonNode dogsInfo){
        service.sendDogMessage(dogsInfo.toString());
        return ResponseEntity.ok(dogsInfo.toString());
    }

}
