package com.dashboard.app.repository;

import com.dashboard.app.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {
    Optional<Log> findByUser_Id(Long userId);
    void deleteByUser_Id(Long userId);
}