package com.pks.blog.controller;

import com.pks.blog.dto.LoginDTO;
import com.pks.blog.dto.SignUpDTO;
import com.pks.blog.entity.Role;
import com.pks.blog.entity.User;
import com.pks.blog.repository.RoleRepository;
import com.pks.blog.repository.UserRepository;
import com.pks.blog.security.JwtAuthResponse;
import com.pks.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    ResponseEntity<JwtAuthResponse>autenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get token from tokenProvider class
        String token = jwtTokenProvider.generateToken(authentication);
        return  ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/signup")
    ResponseEntity<String>registerUser(@RequestBody SignUpDTO signUpDTO){
        if(userRepository.existsByUserName(signUpDTO.getUsername())){
            return new ResponseEntity<>("user Name already taken",HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDTO.getEmail())){
            return new ResponseEntity<>(" email already taken",HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setUserName(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully",HttpStatus.OK);
    }


}
