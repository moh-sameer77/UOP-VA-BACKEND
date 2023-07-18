package com.flatdevs.virtualassistant.user.api;

import com.flatdevs.virtualassistant.contract.Result;
import com.flatdevs.virtualassistant.user.model.EnrolledStudent;
import com.flatdevs.virtualassistant.user.model.User;
import com.flatdevs.virtualassistant.user.service.UserService;
import com.flatdevs.virtualassistant.user.service.auth.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/user")
public class UserApi
{
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserApi(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Result> register(@RequestBody User user)
    {
        return ResponseEntity.ok().body(userService.register(user));
    }

    @PostMapping("/registerAdmin")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Result> registerAdmin(@RequestBody User user)
    {
        return ResponseEntity.ok().body(userService.registerAdmin(user));
    }


    @PostMapping("/add/registerEnrolled")
    public ResponseEntity<Result> registerEnrolled(@RequestHeader("Authorization") String token, @RequestBody EnrolledStudent enrolledStudent) {
        System.out.println(enrolledStudent);
        return ResponseEntity.ok().body( userService.registerEnrolled(enrolledStudent, jwtUtil.getTokenClaims(token).getSubject()));
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Result> login(@RequestBody User user)
    {

        return ResponseEntity.ok().body(userService.login(user));
    }

    @PostMapping("/upload/profile/image")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Result> uploadProfileImage(@RequestHeader("Authorization") String token, @RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok(userService.uploadProfileImage(file, jwtUtil.getTokenClaims(token).getSubject()));
    }

    @GetMapping("/get")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Result> getUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUser(jwtUtil.getTokenClaims(token).getSubject()));
    }
}
