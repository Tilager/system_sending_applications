package ru.courses.api.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.courses.api.models.*;
import ru.courses.api.services.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Log4j2
public class ApiController {
    private final ApplicationsService applicationsService;
    private final ClientsService clientsService;
    private final CoursesService coursesService;
    private final GroupsService groupsService;
    private final TeachersService teachersService;
    private final UsersService usersService;

    @Autowired
    public ApiController(ApplicationsService applicationsService, ClientsService clientsService,
                         CoursesService coursesService, GroupsService groupsService,
                         TeachersService teachersService, UsersService usersService) {
        this.applicationsService = applicationsService;
        this.clientsService = clientsService;
        this.coursesService = coursesService;
        this.groupsService = groupsService;
        this.teachersService = teachersService;
        this.usersService = usersService;
    }

    // teachers
    @GetMapping(path = "/teachers/all")
    public List<TeacherModel> getAllTeachers() {
        return teachersService.getAllTeachers();
    }
    @PostMapping(path = "/teachers/create")
    public TeacherModel createTeacher(@RequestBody TeacherModel teacher) {
        return teachersService.createTeacher(teacher);
    }

    @GetMapping(path = "/teachers/{id}")
    public TeacherModel getTeacher(@PathVariable("id") Integer id) {
        return teachersService.getTeacherByID(id);
    }

    @PostMapping(path = "/teachers/{id}")
    public TeacherModel updateTeacher(@PathVariable("id") Integer id, @RequestBody TeacherModel teacher) {
        return teachersService.updateTeacher(id, teacher);
    }

    @DeleteMapping(path = "/teachers/{id}")
    public void removeTeacher(@PathVariable("id") Integer id) {
        teachersService.removeTeacherByID(id);
    }

    // courses
    @GetMapping(path = "/courses/all")
    public List<CourseModel> getAllCourses() {
        return coursesService.getAllCourses();
    }
    @PostMapping(path = "/courses/create")
    public CourseModel createCourses(@RequestBody CourseModel course) {
        return coursesService.createCourse(course);
    }

    @GetMapping(path = "/courses/{id}")
    public CourseModel getCourses(@PathVariable("id") Integer id) {
        return coursesService.getCourseByID(id);
    }

    @PostMapping(path = "/courses/{id}")
    public CourseModel updateCourses(@PathVariable("id") Integer id, @RequestBody CourseModel course) {
        return coursesService.updateCourse(id, course);
    }

    @DeleteMapping(path = "/courses/{id}")
    public void removeCourse(@PathVariable("id") Integer id) {
        coursesService.removeCourseByID(id);
    }


    // groups
    @GetMapping(path = "/groups/all")
    public List<GroupModel> getAllGroups() {
        return groupsService.getAllGroups();
    }

    @GetMapping(path = "/groups/allByCourse/{id}")
    public List<GroupModel> getAllGroups(@PathVariable("id") int courseId) {
        return groupsService.getAllGroupsByCourse(courseId);
    }
    @PostMapping(path = "/groups/create")
    public GroupModel createGroups(@RequestBody GroupModel group) {
        return groupsService.createGroup(group);
    }

    @GetMapping(path = "/groups/{id}")
    public GroupModel getGroups(@PathVariable("id") Integer id) {
        return groupsService.getGroupByID(id);
    }

    @PostMapping(path = "/groups/{id}")
    public GroupModel updateGroups(@PathVariable("id") Integer id, @RequestBody GroupModel group) {
        return groupsService.updateGroup(id, group);
    }

    @DeleteMapping(path = "/groups/{id}")
    public void removeGroup(@PathVariable("id") Integer id) {
        groupsService.removeGroupByID(id);
    }

    // clients
    @GetMapping(path = "/clients/all")
    public List<ClientModel> getAllClients() {
        return clientsService.getAllClients();
    }
    @PostMapping(path = "/clients/create")
    public ClientModel createClient(@RequestBody ClientModel client) {
        return clientsService.createClient(client);
    }

    @GetMapping(path = "/clients/{id}")
    public ClientModel getClients(@PathVariable("id") Integer id) {
        return clientsService.getClientByID(id);
    }

    @PostMapping(path = "/clients/{id}")
    public ClientModel updateClients(@PathVariable("id") Integer id, @RequestBody ClientModel client) {
        return clientsService.updateClient(id, client);
    }

    @DeleteMapping(path = "/clients/{id}")
    public void removeClient(@PathVariable("id") Integer id) {
        clientsService.removeClientByID(id);
    }

    // applications
    @GetMapping(path = "/applications/all")
    public List<ApplicationModel> getAllApplications() {
        return applicationsService.getAllApplications();
    }

    @GetMapping(path = "/applications/{id}")
    public ApplicationModel getApplications(@PathVariable("id") Integer id) {
        return applicationsService.getApplicationByID(id);
    }

    @PostMapping(path = "/applications/{id}")
    public ResponseEntity<?> updateApplication(@PathVariable("id") Integer id,
                                            @RequestBody ApplicationModel application) {
        try {
            return ResponseEntity.ok().body(applicationsService.updateApplication(id, application));
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "capacity error" -> ResponseEntity.status(HttpStatus.CONFLICT).body("В группе нет мест");
                case null, default -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            };
        }
    }

    @DeleteMapping(path = "/applications/{id}")
    public void removeApplication(@PathVariable("id") Integer id) {
        applicationsService.removeApplicationByID(id);
    }

    // applications
    @GetMapping(path = "/users/all")
    public List<UserModel> getAllUsers() {
        return usersService.getAllUsers();
    }
    @PostMapping(path = "/users/create")
    public ResponseEntity<?> createUser(@RequestBody UserModel user) {
        if (usersService.getUserByLogin(user.getLogin()) == null) {
            return ResponseEntity.ok().body(usersService.createUser(user));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с таким логином уже существует");
        }
    }

    @GetMapping(path = "/users/{id}")
    public UserModel getUser(@PathVariable("id") Integer id) {
        return usersService.getUserByID(id);
    }

    @PostMapping(path = "/users/{id}")
    public UserModel updateUser(@PathVariable("id") Integer id,
                                              @RequestBody UserModel user) {
        return usersService.updateUser(id, user);
    }

    @DeleteMapping(path = "/users/{id}")
    public void removeUser(@PathVariable("id") Integer id) {
        usersService.removeUserByID(id);
    }

    @PostMapping(path = "/application/create")
    public ResponseEntity<?> createApplication(@RequestBody ApplicationModel application) {
        try {
            applicationsService.createApplication(application);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return switch (e.getMessage()) {
                case "application exists" ->
                        ResponseEntity.status(HttpStatus.CONFLICT).body("Данная заявка уже была отправлена, если вы хотите отправить ее еще раз, то обратитесь в поддержку.");
                case null, default -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            };
        }
    }
}