package com.api.elevator.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
@Entity
@Table(name = "elevator_info")
@Data
public class ElevatorInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        private String name;
        private int currentFloor;
        private int intitialFloor;
        private int floorHops;
        private String direction;
        @CreationTimestamp
        private Timestamp createdAt;
}
