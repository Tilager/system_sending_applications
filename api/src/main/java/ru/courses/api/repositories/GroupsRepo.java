package ru.courses.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.courses.api.models.CourseModel;

public interface CoursesRepo extends JpaRepository<CourseModel, Integer> {
}
