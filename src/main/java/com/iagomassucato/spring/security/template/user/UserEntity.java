package com.iagomassucato.spring.security.template.user;

import lombok.Getter;
import javax.persistence.*;

@Table(name = "users")
@Entity
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;
}
