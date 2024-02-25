package ru.courses.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.courses.api.models.ApplicationModel;

public interface ApplicationsRepo extends JpaRepository<ApplicationModel, Integer> {
}
