package com.api.elevator.web;

import com.api.elevator.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ElevatorControllerTest extends IntegrationTest {
    @Value("${spring.application.floors}")
    private int totalFlooors;
    @Test
    void listElevators() throws Exception {
        mvc.perform((get("/elevator", 1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[:1].name", hasItem("A")));
    }

    @Test
    void getElevatorDetails() throws Exception {
        mvc.perform((get("/elevator/{id}", 1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", equalTo("A")));
    }

    @Test
    void getNonExistingElevator() throws Exception {
        mvc.perform((get("/elevator/{id}", 30, 6666)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createElevator() throws Exception {
        mvc.perform(
                        post("/elevator/create", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"C\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", greaterThan(0)));
    }
    @Test
    void createElevatorThatExist() throws Exception {
        mvc.perform(
                        post("/elevator/create", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"A\"}")
                )
                .andExpect(status().isBadRequest());
    }
    @Test
    void detailsMissing() throws Exception {
        mvc.perform(
                        post("/elevator/create", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }
    @Test
    void callElevator() throws Exception {
        mvc.perform(
                        post("/elevator/call", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"elevatorName\":\"A\",\"currentFloor\":\"2\",\"direction\":\"UP\"}")
                )
                .andExpect(status().isOk());
    }
    @Test
    void callElevatorToNonExistentFloor() throws Exception {
        totalFlooors=totalFlooors+1;
        mvc.perform(
                        post("/elevator/call", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"elevatorName\":\"A\",\"currentFloor\":\""+totalFlooors+"\",\"direction\":\"UP\"}")
                )
                .andExpect(status().isNotFound());
    }


}
