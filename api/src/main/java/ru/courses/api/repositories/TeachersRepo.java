package ru.courses.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.courses.api.models.TeacherModel;

public interface TeachersRepo extends JpaRepository<TeacherModel, Integer> {
}
