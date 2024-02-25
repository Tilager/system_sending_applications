package ru.courses.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.courses.api.models.UserModel;
import ru.courses.api.repositories.UsersRepo;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepo repo;

    @Autowired
    public UsersService(UsersRepo usersRepo) {
        this.repo = usersRepo;
    }

    public UserModel createUser(UserModel user) {
        return repo.save(user);
    }

    public UserModel getUserByID(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<UserModel> getAllUsers() {
        return repo.findAll();
    }

    public UserModel updateUser(int id, UserModel newUser) {
        UserModel user = getUserByID(id);

        if (user == null) {
            throw new RuntimeException("User id = " + newUser.getId() + ". Not found");
        } else {
            user.setClient(newUser.getClient());
            user.setType(newUser.getType());
            user.setLogin(newUser.getLogin());
            user.setPassword(newUser.getPassword());

            return repo.save(user);
        }
    }

    public void removeUserByID(int id) {
        repo.deleteById(id);
    }

    public UserModel getUserByLogin(String login) {
        return repo.getByLogin(login);
    }
}
