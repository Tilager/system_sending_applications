package ru.courses.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.courses.api.models.ClientModel;
import ru.courses.api.models.UserModel;
import ru.courses.api.services.ClientsService;
import ru.courses.api.services.UsersService;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsersService usersService;
    private final ClientsService clientsService;

    @Autowired
    public AuthController(UsersService usersService, ClientsService clientsService) {
        this.usersService = usersService;
        this.clientsService = clientsService;
    }

    @PostMapping(path = "/login")
    public UserModel getAuthUser(@RequestBody UserModel user) {
        UserModel u = usersService.getUserByLogin(user.getLogin());

        if (u != null)
            return Objects.equals(u.getPassword(), user.getPassword()) ? u : null;
        else
            return null;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody ObjectNode json) {
        ObjectMapper mapper = new ObjectMapper();
        UserModel user = mapper.convertValue(json.get("user"), UserModel.class);
        ClientModel client = mapper.convertValue(json.get("client"), ClientModel.class);

        if (usersService.getUserByLogin(user.getLogin()) == null) {
            ClientModel clientDB = clientsService.createClient(client);

            user.setClient(clientDB);
            user.setType("user");

            return ResponseEntity.ok(usersService.createUser(user));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с таким логином уже существует");
        }
    }
}
