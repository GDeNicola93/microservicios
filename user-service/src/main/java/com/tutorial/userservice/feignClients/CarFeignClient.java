package com.tutorial.userservice.feignClients;

import com.tutorial.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service",path = "/car")//No hacer falta poner URL completa porque ya esta en eureka
public interface CarFeignClient {
    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/user/{userId}")
    List<Car> getCarsByUserId(@PathVariable int userId);
}
