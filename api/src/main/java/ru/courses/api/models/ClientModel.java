package ru.courses.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Courses")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String language;
    private int duration; // IN HOURS
    private double price;

    @Lob
    private Byte[] image;
}
