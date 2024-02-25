package ru.courses.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name="clients")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String surname;
    private String name;
    private String patronymic;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private String phone;
    private String email;
}
