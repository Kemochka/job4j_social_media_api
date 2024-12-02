package ru.job4j.socialmedia.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.job4j.socialmedia.security.models.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User Model Information")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true, name = "email")
    @Email(message = "введите корректный email-адрес")
    @Schema(description = "login", example = "mediator@mail.com")
    private String login;
    @Length(min = 8, message = "пароль должен содержать не менее 8 символов")
    @Schema(description = "password", example = "password")
    private String password;
    @NotBlank(message = "имя не может быть пустым")
    @Length(min = 4, max = 15, message = "имя должно быть не менее 4 и не более 15 символов")
    @Schema(description = "User name", example = "Mediator")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
