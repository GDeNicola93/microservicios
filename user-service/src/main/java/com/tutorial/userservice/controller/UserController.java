package com.tutorial.userservice.controller;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @CircuitBreaker(name="carsCB",fallbackMethod = "fallBackGetCarsFromUserId")
    public ResponseEntity<List<Car>> getCarsFromUserId(@PathVariable Integer userId){
        List<Car> cars = userService.getCars(userId);
        if(cars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/{userId}/cars")
    @CircuitBreaker(name="carsCB",fallbackMethod = "fallBackSaveCar")
    public ResponseEntity<Car> saveCar(@PathVariable Integer userId,@RequestBody Car car){
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveCar(userId,car));
    }

    @GetMapping("/{userId}/bikes")
    @CircuitBreaker(name="bikesCB",fallbackMethod = "fallBackGetBikesFromUserId")
    public ResponseEntity<List<Bike>> getBikesFromUserId(@PathVariable Integer userId){
        List<Bike> bikes = userService.getBikes(userId);
        if(bikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }

    @PostMapping("/{userId}/bikes")
    @CircuitBreaker(name="bikesCB",fallbackMethod = "fallBackSaveBike")
    public ResponseEntity<Bike> saveBike(@PathVariable Integer userId,@RequestBody Bike bike){
        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveBike(userId,bike));
    }

    @GetMapping("/{userId}/vehicles")
    @CircuitBreaker(name="allCB",fallbackMethod = "fallBackGetUserAndVehicles")
    public ResponseEntity<Map<String,Object>> getUserAndVehicles(@PathVariable int userId){
        return ResponseEntity.ok(userService.getUserAndVehicles(userId));
    }

    private ResponseEntity<List<Car>> fallBackGetCarsFromUserId(@PathVariable Integer userId,RuntimeException e){
        return new ResponseEntity("El usuario " + userId + " tiene los autos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(@PathVariable Integer userId,@RequestBody Car car,RuntimeException e){
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para autos", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikesFromUserId(@PathVariable Integer userId,RuntimeException e){
        return new ResponseEntity("El usuario " + userId + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallBackSaveBike(@PathVariable Integer userId,@RequestBody Bike bike,RuntimeException e){
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String,Object>> fallBackGetUserAndVehicles(@PathVariable int userId,RuntimeException e){
        return new ResponseEntity("El usuario " + userId + " tiene los vehiculos en el taller", HttpStatus.OK);
    }

}
