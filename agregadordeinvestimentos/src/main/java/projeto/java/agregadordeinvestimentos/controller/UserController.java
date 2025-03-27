package projeto.java.agregadordeinvestimentos.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.java.agregadordeinvestimentos.dto.CreateUserDto;
import projeto.java.agregadordeinvestimentos.dto.UpdateUserDto;
import projeto.java.agregadordeinvestimentos.entity.User;
import projeto.java.agregadordeinvestimentos.service.UserService;

import java.net.URI;
import java.security.URIParameter;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {

        var user = userService.getUserById(userId);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
       var users = userService.getUsersAll();
       return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUser(userId,updateUserDto);
        return ResponseEntity.ok().build();
    }

}
