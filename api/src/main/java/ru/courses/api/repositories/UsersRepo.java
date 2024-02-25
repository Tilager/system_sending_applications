package ru.courses.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.courses.api.models.UserModel;

public interface UsersRepo extends JpaRepository<UserModel, Integer> {
    UserModel getByLogin(String login);
}
