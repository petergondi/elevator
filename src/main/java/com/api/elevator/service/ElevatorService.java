package com.api.elevator.service;

import com.api.elevator.model.ElevatorInfo;

import java.util.List;

public interface ElevatorService {

    ElevatorInfo saveElevator(ElevatorInfo elevatorInfo);

    ElevatorInfo getOneElevatorByName(String name);

    ElevatorInfo getOneElevatorById(Long id);

    List<ElevatorInfo> getAllElevators();
}
