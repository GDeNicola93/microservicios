package com.tutorial.userservice.controller;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listAll(){
        List<User> users = userService.getAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        User user_new = userService.save(user);
        return ResponseEntity.ok(user_new);
    }

    @GetMapping("/{userId}/cars")
    public ResponseEntity<List<Car>> getCarsFromUserId(@PathVariable Integer userId){
        List<Car> cars = userService.getCars(userId);
        if(cars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{userId}/bikes")
    public ResponseEntity<List<Bike>> getBikesFromUserId(@PathVariable Integer userId){
        List<Bike> bikes = userService.getBikes(userId);
        if(bikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }

    @PostMapping("/{userId}/cars")
    public ResponseEntity<Car> saveCar(@PathVariable Integer userId,@RequestBody Car car){
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveCar(userId,car));
    }

    @PostMapping("/{userId}/bikes")
    public ResponseEntity<Bike> saveCar(@PathVariable Integer userId,@RequestBody Bike bike){
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveBike(userId,bike));
    }

    @GetMapping("/{userId}/vehicles")
    public ResponseEntity<Map<String,Object>> getUserAndVehicles(@PathVariable int userId){
        return ResponseEntity.ok(userService.getUserAndVehicles(userId));
    }
}
