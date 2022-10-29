package com.tutorial.userservice.service;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.feignClients.BikeFeignClient;
import com.tutorial.userservice.feignClients.CarFeignClient;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    //Esta es otra forma de hacerlo sin usar FeignClient
    //FeignClient es mejor
    public List<Car> getCars(int userId){
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/user/"+userId,List.class);
        return cars;
    }

    //Esta es otra forma de hacerlo sin usar FeignClient
    //FeignClient es mejor
    public List<Bike> getBikes(int userId){
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bike/user/"+userId,List.class);
        return bikes;
    }

    public Car saveCar(int userId,Car car){
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Bike saveBike(int userId,Bike bike){
        bike.setUserId(userId);
        return bikeFeignClient.save(bike);
    }

    public Map<String, Object> getUserAndVehicles(int userId){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            result.put("mensaje","No existe el usuario");
            return result;
        }
        result.put("user",user);
        List<Car> cars = carFeignClient.getCarsByUserId(userId);
        List<Bike> bikes = bikeFeignClient.getBikesByUserId(userId);
        result.put("cars",cars);
        result.put("bikes",bikes);
        return result;
    }
}
