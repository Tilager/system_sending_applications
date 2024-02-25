package ru.courses.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.courses.api.models.TeacherModel;
import ru.courses.api.repositories.TeachersRepo;

import java.util.List;

@Service
public class TeachersService {
    private final TeachersRepo repo;

    @Autowired
    public TeachersService(TeachersRepo teachersRepo) {
        this.repo = teachersRepo;
    }

    public TeacherModel createTeacher(TeacherModel teacher) {
        return repo.save(teacher);
    }

    public TeacherModel getTeacherByID(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<TeacherModel> getAllTeachers() {
        return repo.findAll();
    }

    public TeacherModel updateTeacher(TeacherModel newTeacher) {
        TeacherModel teacher = getTeacherByID(newTeacher.getId());

        if (teacher == null) {
            throw new RuntimeException("Teacher id = " + newTeacher.getId() + ". Not found");
        } else {
            teacher.setName(newTeacher.getName());
            teacher.setSurname(newTeacher.getSurname());
            teacher.setPatronymic(newTeacher.getPatronymic());
            teacher.setEmail(newTeacher.getEmail());
            teacher.setPhone(newTeacher.getPhone());
            teacher.setPicture(newTeacher.getPicture());

            return repo.save(teacher);
        }
    }

    public void removeTeacherByID(int id) {
        repo.deleteById(id);
    }
}
