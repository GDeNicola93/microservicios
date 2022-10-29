package com.tutorial.userservice.feignClients;

import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "bike-service",url = "http://localhost:8003/bike")
public interface BikeFeignClient {
    @PostMapping
    Bike save(@RequestBody Bike bike);

    @GetMapping("/user/{userId}")
    List<Bike> getBikesByUserId(@PathVariable int userId);
}
