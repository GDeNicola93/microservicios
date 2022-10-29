package com.tutorial.userservice.feignClients;

import com.tutorial.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service",url = "http://localhost:8002/car")
public interface CarFeignClient {
    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/user/{userId}")
    List<Car> getCarsByUserId(@PathVariable int userId);
}
