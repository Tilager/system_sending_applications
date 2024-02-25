package ru.courses.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.courses.api.models.ClientModel;

public interface ClientsRepo extends JpaRepository<ClientModel, Integer> {
}
