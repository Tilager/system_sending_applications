package ru.courses.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.courses.api.models.GroupModel;

public interface GroupsRepo extends JpaRepository<GroupModel, Integer> {
}
