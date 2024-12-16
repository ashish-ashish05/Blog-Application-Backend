package com.blogapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    private int Id;

    private String name;
}
