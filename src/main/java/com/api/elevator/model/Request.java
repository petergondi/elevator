package com.api.elevator.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Request {
    private String elevatorName;
    private int currentFloor;
    private String direction;
}
