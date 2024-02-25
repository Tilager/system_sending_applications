package ru.courses.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name="applications")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ApplicationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date submissionDate;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private ClientModel client;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseModel course;

    @ManyToOne ()
    @JoinColumn(name="group_id")
    private GroupModel group;


}
