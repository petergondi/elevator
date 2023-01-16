package com.api.elevator.service;

import com.api.elevator.model.ElevatorInfo;
import com.api.elevator.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElevatorServiceImpl implements ElevatorService{
    @Autowired
    ElevatorRepository elevatorRepository;
    @Override
    public ElevatorInfo saveElevator(ElevatorInfo elevatorInfo) {
        return elevatorRepository.save(elevatorInfo);
    }
    @Override
    public ElevatorInfo getOneElevatorByName(String name) {
        Optional<ElevatorInfo> elevator=elevatorRepository.findByName(name);
        return elevator.orElse(null);
    }

    @Override
    public ElevatorInfo getOneElevatorById(Long id) {
        Optional<ElevatorInfo> elevator = elevatorRepository.findById(id);
        return elevator.orElse(null);
    }
    @Override
    public List<ElevatorInfo> getAllElevators() {
        return elevatorRepository.findAll();
    }
}
