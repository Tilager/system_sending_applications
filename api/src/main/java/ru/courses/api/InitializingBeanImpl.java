package ru.courses.api;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.courses.api.models.UserModel;
import ru.courses.api.services.UsersService;

@Component
public class InitializingBeanImpl implements InitializingBean {
    private final UsersService usersService;

    public InitializingBeanImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(usersService.getUserByLogin("admin") == null) {
            UserModel admin = new UserModel();
            admin.setType("admin");
            admin.setLogin("admin");
            admin.setPassword("admin");
            usersService.createUser(admin);
        }
    }
}
