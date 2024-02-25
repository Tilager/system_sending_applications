package ru.courses.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="groups")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name="course_id", nullable = false)
    private CourseModel course;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<ApplicationModel> applications; // ученики

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupModel that = (GroupModel) o;
        return id == that.id && capacity == that.capacity && Objects.equals(name, that.name) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, course);
    }

    @PreRemove
    private void preRemove() {
        for (ApplicationModel a : applications) {
            a.setGroup(null);
        }
    }
}
