package com.example.testtls.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.example.testtls.security.OAuth2Filter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    public OAuth2Filter myFilter() {
        final OAuth2Filter filter = new OAuth2Filter("/login");
        filter.setRestTemplate(restTemplate);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
        .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterAfter(myFilter(), OAuth2ClientContextFilter.class)
        .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        .and()
        .authorizeRequests()
       // .antMatchers("/","/index*").permitAll()
        .anyRequest().authenticated()
        ;

     // @formatter:on
    }
}