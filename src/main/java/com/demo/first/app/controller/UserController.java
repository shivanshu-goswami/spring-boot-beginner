package com.demo.first.app.controller;

import com.demo.first.app.model.User;
import com.demo.first.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService=new UserService();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser= userService.createUser(user);
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
            User updated= userService.updateUser(user);
            if(updated==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(updated);
    }
    // /user/1   /user/2  /user/3
    @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteUser(@PathVariable int id) {
           Boolean isdeleted= userService.deleteUser(id);
           if(!isdeleted){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
           }
           return ResponseEntity.noContent().build();
        }
    //two url mapped to one annotation i.e, both endpoint can be written into
    //one annotation this way
    //@GetMapping({"/user","/{userId}"})

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(
            @PathVariable(value="userId", required = false) int id) {
        User user= userService.getUserById(id);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
              return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/order/{orderId}")
    public ResponseEntity<User> getUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        User user= userService.getUserById(id);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    //RequestParam
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required=false,defaultValue ="Shivanshu" ) String name,
            @RequestParam(required=false, defaultValue = "email") String email
    ){

        return ResponseEntity.ok(userService.searchUsers(name,email));
    }

    //request header and combining it with path variable and request param
    @GetMapping("/info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent
    ){
        return "userAgent: " + userAgent +" : " + id + " : " + name;
    }


}
