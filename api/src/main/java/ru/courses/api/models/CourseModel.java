package ru.courses.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="courses")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private int duration; // IN HOURS

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private TeacherModel teacher;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<GroupModel> groups;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ApplicationModel> applications;
}
