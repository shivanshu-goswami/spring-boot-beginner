package com.demo.first.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private Map<Integer,User> userDb = new HashMap<Integer,User>();

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        System.out.println(user.getEmail());
        userDb.putIfAbsent(user.getId(),user);
        //return ResponseEntity.status(HttpStatus.CREATED).body(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (!userDb.containsKey(user.getId())) {
           // return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //return ResponseEntity.status(HttpStatus.OK).body(user);
        return ResponseEntity.ok(user);
    }
    // /user/1   /user/2  /user/3
    @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteUser(@PathVariable int id) {
           if(!userDb.containsKey(id)) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
           }
           userDb.remove(id);
           //return ResponseEntity.noContent().build();
           //no content will give status code 204 which is used in this place
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    //two url mapped to one annotation i.e, both endpoint can be written into
    //one annotation this way
    //@GetMapping({"/user","/{userId}"})

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<User>(userDb.values());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(
            @PathVariable(value="userId", required = false) int id) {
              if(!userDb.containsKey(id)){
                  return ResponseEntity.notFound().build();
              }
              return ResponseEntity.ok(userDb.get(id));
    }

    @GetMapping("/{userId}/order/{orderId}")
    public ResponseEntity<User> getUserOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        System.out.println("ORDER_ID : " + orderId);
        if(!userDb.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDb.get(id));
    }

    //RequestParam
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required=false,defaultValue ="Shivanshu" ) String name,
            @RequestParam(required=false, defaultValue = "email") String email
    ){
        System.out.println(name);
        List<User> users = userDb.values().stream()
                .filter(u->u.getName().equalsIgnoreCase(name))
                .filter(u->u.getEmail().equalsIgnoreCase(email))
                .toList();
        return ResponseEntity.ok(users);
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
