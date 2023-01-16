package com.api.elevator.repository;

import com.api.elevator.model.ElevatorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElevatorRepository extends JpaRepository<ElevatorInfo,Long> {
    @Query(value = "SELECT * FROM elevator_info WHERE name=:name", nativeQuery = true)
    Optional<ElevatorInfo> findByName(@Param("name") String name);
}
