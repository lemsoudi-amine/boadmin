package com.wegrowup.boadmin.controller;

import com.wegrowup.boadmin.services.MyUserDetailsService;
import com.wegrowup.boadmin.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws  Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        } catch(BadCredentialsException e){
            throw new Exception("incorrect username or password");
        }
        final String jwt = jwtUtil.generateToken(myUserDetailsService.loadUserByUsername(authenticationRequest.getUserName()));

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";
    }

}
