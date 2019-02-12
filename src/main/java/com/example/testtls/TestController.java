package com.example.testtls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testtls.config.SecurityContextUtils;

@RestController
@RequestMapping("/api")
public class TestController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;
	
	@Value("${microservice.name}")
	private String svcName;
	
	@Value("${microservice.next.endpoint}")
	private String nextEndpoint;

	@GetMapping(path = "/hello")
	@PreAuthorize("hasAnyAuthority('ROLE_USER')")
	public ResponseEntity<String> getAuthorizedResponse() {
		return ResponseEntity.ok("Hello " + SecurityContextUtils.getUserName() + " from " + svcName);
	}
	
	
	@GetMapping(path = "/helloForAll")
	@PreAuthorize("hasAnyAuthority('ROLE_USER')")
	public ResponseEntity<String> getAuthorizedResponseFromBothServices() {
		String svc2Response = oAuth2RestTemplate.getForObject(nextEndpoint, String.class);
		StringBuilder bd = new StringBuilder();
		bd.append(getAuthorizedResponse().getBody());
		bd.append("\n");
		bd.append(svc2Response);
		return ResponseEntity.ok(bd.toString());
	}

}