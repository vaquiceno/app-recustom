package com.dashboard.app.service;

import com.dashboard.app.exception.AppException;
import com.dashboard.app.model.Log;
import com.dashboard.app.model.User;
import com.dashboard.app.repository.LogRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final UserService userService;

    @Autowired
    public LogService(LogRepository logRepository, UserService userService) {
        this.logRepository = logRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        List<User> users = userService.getAllUsers();

        for (User user : users) {
            Log log = Log.builder()
                    .user(user)
                    .numberOfLoggings(0)
                    .numberOfDownloads(0)
                    .build();
            logRepository.save(log);
        }
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    public Log createLog(Log log) {
        return logRepository.save(log);
    }

    public Log getLogById(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() -> new AppException("Log not found with id: " + id));
    }

    public Log updateLog(Log log) {
        if (log.getId() == null) {
            throw new AppException("Log ID is required for update");
        }
        return logRepository.save(log);
    }

    public Log getLogByUserId(Long userId) {
        return logRepository.findByUser_Id(userId)
                .orElseThrow(() -> new AppException("Log not found for user with id: " + userId));
    }

    public void increaseDownloadCount(Long userId) {
        Log log = getLogByUserId(userId);
        log.setNumberOfDownloads(log.getNumberOfDownloads() + 1);
        updateLog(log);
    }
}