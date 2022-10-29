package com.tutorial.carservice.service;

import com.tutorial.carservice.entity.Car;
import com.tutorial.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Car getUserById(Integer id){
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car){
        return carRepository.save(car);
    }

    public List<Car> findByUserId(int userId){
        return carRepository.findByUserId(userId);
    }
}
