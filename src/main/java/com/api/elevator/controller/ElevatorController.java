package com.api.elevator.controller;

import com.api.elevator.model.ElevatorInfo;
import com.api.elevator.model.Request;
import com.api.elevator.model.ResponseHandler;
import com.api.elevator.service.ElevatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/elevator", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElevatorController {
    private static final Logger logger = LoggerFactory.getLogger(ElevatorController.class);
    @Value("${spring.application.floors}")
    private int totalFlooors;
    @Autowired
    ElevatorService elevatorService;
    @PostMapping("/call")
    ResponseEntity<Object> callElevator(@Validated @RequestBody Request request) {
        ResponseEntity<Object> myresponse;
        logger.info("Received elevator Details:" + request.toString());
        try {
            if(request.getCurrentFloor()>totalFlooors || request.getCurrentFloor()==0){
                myresponse=ResponseHandler.generateResponse("Wrong floor number the building has only between 1 and "+totalFlooors+" floors", HttpStatus.NOT_FOUND, null);
            }else{
                ElevatorInfo elevatorInfo=elevatorService.getOneElevatorByName(request.getElevatorName());
                if(elevatorInfo !=null){
                    elevatorInfo.setCurrentFloor(request.getCurrentFloor());
                    elevatorInfo.setDirection(request.getDirection());
                    logger.info("You have called elevator successfully:");
                    myresponse=ResponseHandler.generateResponse("You have called elevator successfully!", HttpStatus.OK, elevatorService.saveElevator(elevatorInfo));
                }else{
                    logger.info("No Such Elevator exists!:");
                    myresponse=ResponseHandler.generateResponse("No Such Elevator exists!", HttpStatus.BAD_REQUEST, null);
                }

            }
            } catch (Exception e) {
            logger.error("A system error occured while calling the elevator:" + e.getMessage());
            myresponse=ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return myresponse;
    }
    @GetMapping("{id}")
    ResponseEntity<Object> getElevatorOneInfo(@PathVariable Long id) {
        try {
            ElevatorInfo elevatorInfo = elevatorService.getOneElevatorById(id);
            if (elevatorInfo == null) {
                logger.info("Elevator Not Found:");
                return ResponseHandler.generateResponse("Not Found", HttpStatus.NOT_FOUND, null);
            }
            logger.info("Elevator Found:");
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, elevatorInfo);
        } catch (Exception e) {
            logger.error("An error occured while getting elevator info:"+e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    @GetMapping
    ResponseEntity<Object> getElevatorInfo() {
        try{
            logger.info("Elevator records Found:");
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, elevatorService.getAllElevators());
        }catch(Exception e){
            logger.error("An error occured while getting elevator info:"+e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }
    @PostMapping("/create")
    ResponseEntity<Object> createElevator(@Validated @RequestBody ElevatorInfo elevatorInfo) {
          try {
            if (elevatorInfo.getName() == null) {
                logger.info("No elevator name found");
                return ResponseHandler.generateResponse("No elevator name!", HttpStatus.BAD_REQUEST, null);
            }
              if (elevatorService.getOneElevatorByName(elevatorInfo.getName() )!=null) {
                  logger.info("Elevator name already exists!!");
                  return ResponseHandler.generateResponse("Elevator name already exists!!", HttpStatus.BAD_REQUEST, null);
              }
            elevatorInfo.setCurrentFloor(1);
            elevatorInfo.setDirection("UP");
              logger.info("New elevator created successfully");
            return ResponseHandler.generateResponse("You have created new elevator successfully!", HttpStatus.CREATED, elevatorService.saveElevator(elevatorInfo));
        }catch(Exception e){
              logger.error("An error occured while creating elevator info:"+e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

}
