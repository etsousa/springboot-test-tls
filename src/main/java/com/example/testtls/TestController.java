package com.example.testtls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testtls.config.SecurityContextUtils;

@RestController
@RequestMapping("/api")
public class TestController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(path = "/hello")
	@PreAuthorize("hasAnyAuthority('ROLE_USER')")
	public ResponseEntity<String> getAuthorizedUserName() {
		return ResponseEntity.ok("Hello " + SecurityContextUtils.getUserName());
	}

}