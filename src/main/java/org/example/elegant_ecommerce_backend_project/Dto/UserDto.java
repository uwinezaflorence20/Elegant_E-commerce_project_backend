package org.example.elegant_ecommerce_backend_project.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    @Schema(required = true, maxLength = 50)
    private String fullName;
    @NotBlank
    @Schema(required = true, maxLength =50)
    private String UserName;
    @NotBlank
    @Schema(required = true, maxLength = 50)
    @Column(unique = true)
    private String email;
    private String password;
}