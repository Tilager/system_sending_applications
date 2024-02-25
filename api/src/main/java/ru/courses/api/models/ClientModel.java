package ru.courses.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

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

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;
    private String patronymic;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    private String phone;
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ApplicationModel> applications;
}
