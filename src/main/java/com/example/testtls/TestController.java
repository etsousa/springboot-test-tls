package com.example.testtls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    
    @GetMapping("/hello")
    public ResponseEntity<String> getProperty() {
    	logger.info("helllloooo");
        return ResponseEntity.ok("Hello");
    }

}