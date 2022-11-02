package com.tutorial.userservice.feignClients;

import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bike-service",path = "/bike")//No hacer falta poner URL Completa porque ya esta en eureka
public interface BikeFeignClient {
    @PostMapping
    Bike save(@RequestBody Bike bike);

    @GetMapping("/user/{userId}")
    List<Bike> getBikesByUserId(@PathVariable int userId);
}
