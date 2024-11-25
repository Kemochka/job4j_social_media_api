package ru.job4j.socialmedia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true, name = "email")
    @Email(message = "введите корректный email-адрес")
    private String login;
    @Length(min = 8, message = "пароль должен содержать не менее 8 символов")
    private String password;
    @NotBlank(message = "имя не может быть пустым")
    @Length(min = 4, max = 15, message = "имя должно быть не менее 4 и не более 15 символов")
    private String name;
}
