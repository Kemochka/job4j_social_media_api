package ru.job4j.socialmedia.security.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDro {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
