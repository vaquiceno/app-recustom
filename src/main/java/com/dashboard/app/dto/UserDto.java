package com.dashboard.app.dto;

import com.dashboard.app.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Role is required")
    private UserRole role;
}