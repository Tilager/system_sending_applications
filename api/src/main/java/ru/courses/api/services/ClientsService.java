package ru.courses.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.courses.api.models.ClientModel;
import ru.courses.api.repositories.ClientsRepo;

import java.util.List;

@Service
public class ClientsService {
    private final ClientsRepo repo;

    @Autowired
    public ClientsService(ClientsRepo clientsRepo) {
        this.repo = clientsRepo;
    }

    public ClientModel createClient(ClientModel client) {
        return repo.save(client);
    }

    public ClientModel getClientByID(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<ClientModel> getAllClients() {
        return repo.findAll();
    }

    public ClientModel updateClient(int id, ClientModel newClient) {
        ClientModel client = getClientByID(id);

        if (client == null) {
            throw new RuntimeException("Client id = " + newClient.getId() + ". Not found");
        } else {
            client.setName(newClient.getName());
            client.setSurname(newClient.getSurname());
            client.setPatronymic(newClient.getPatronymic());
            client.setPhone(newClient.getPhone());
            client.setEmail(newClient.getEmail());
            client.setBirthday(newClient.getBirthday());

            return repo.save(client);
        }
    }

    public void removeClientByID(int id) {
        repo.deleteById(id);
    }
}
