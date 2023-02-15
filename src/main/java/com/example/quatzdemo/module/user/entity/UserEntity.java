package com.example.quatzdemo.module.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(unique = true, updatable = false)
    private UUID id;

    private String name;

    private String email;

    private String phoneNumber;
}
