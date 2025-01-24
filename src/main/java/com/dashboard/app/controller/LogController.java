package com.dashboard.app.controller;

import com.dashboard.app.model.Log;
import com.dashboard.app.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@Tag(name = "Log Controller", description = "Endpoints for managing logs")
public class LogController {

    private final LogService logService;

    @Operation(summary = "Retrieve a log by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Log not found for the given user ID")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Log> getLogByUserId(@PathVariable Long userId) {
        Log log = logService.getLogByUserId(userId);
        return ResponseEntity.ok(log);
    }
}
