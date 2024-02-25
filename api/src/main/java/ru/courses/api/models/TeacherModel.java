package ru.courses.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="teachers")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TeacherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;
    private String patronymic;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<CourseModel> courses;

    @PreRemove
    private void preRemove() {
        for (CourseModel c : courses) {
            c.setTeacher(null);
        }
    }
}
