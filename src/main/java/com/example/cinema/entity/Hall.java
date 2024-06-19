package com.example.cinema.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    int capacity;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "hall",fetch = FetchType.EAGER)
    List<Session> sessions;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "hall",fetch = FetchType.EAGER)
    List<Seat> seats;

}
