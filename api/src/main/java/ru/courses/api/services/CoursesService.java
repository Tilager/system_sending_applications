package ru.courses.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.courses.api.models.CourseModel;
import ru.courses.api.repositories.CoursesRepo;

import java.util.List;

@Service
public class CoursesService {
    private final CoursesRepo repo;

    @Autowired
    public CoursesService(CoursesRepo coursesRepo) {
        this.repo = coursesRepo;
    }

    public CourseModel createCourse(CourseModel course) {
        return repo.save(course);
    }

    public CourseModel getCourseByID(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<CourseModel> getAllCourses() {
        return repo.findAll();
    }

    public CourseModel updateCourse(Integer id, CourseModel newCourse) {
        CourseModel course = getCourseByID(id);

        if (course == null) {
            throw new RuntimeException("Course id = " + newCourse.getId() + ". Not found");
        } else {
            course.setName(newCourse.getName());
            course.setDescription(newCourse.getDescription());
            course.setDuration(newCourse.getDuration());
            course.setLanguage(newCourse.getLanguage());
            course.setPrice(newCourse.getPrice());

            course.setTeacher(newCourse.getTeacher());

            return repo.save(course);
        }
    }

    public void removeCourseByID(int id) {
        repo.deleteById(id);
    }
}
