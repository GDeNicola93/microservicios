package com.tutorial.bikeservice.controller;

import com.tutorial.bikeservice.entity.Bike;
import com.tutorial.bikeservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> listAll(){
        List<Bike> cars = bikeService.getAll();
        if(cars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> findById(@PathVariable Integer id){
        Bike bike = bikeService.getUserById(id);
        if(bike == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity<Bike> save(@RequestBody Bike bike){
        Bike bike_new = bikeService.save(bike);
        return ResponseEntity.ok(bike_new);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable int userId){
        List<Bike> bikes = bikeService.findByUserId(userId);
        if(bikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }
}
