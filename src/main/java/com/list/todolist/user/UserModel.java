package com.list.todolist.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_Users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    public String name;
    public String username;
    public String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
