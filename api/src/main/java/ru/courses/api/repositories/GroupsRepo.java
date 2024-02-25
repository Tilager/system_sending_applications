package ru.courses.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.courses.api.models.GroupModel;

import java.util.List;

public interface GroupsRepo extends JpaRepository<GroupModel, Integer> {
    List<GroupModel> getAllByCourse_Id(int courseId);
}
