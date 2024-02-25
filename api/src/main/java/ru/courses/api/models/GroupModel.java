package ru.courses.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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

    private String surname;
    private String name;
    private String patronymic;
    private String phone;
    private String email;

    @Lob
    private Byte[] picture;
}
