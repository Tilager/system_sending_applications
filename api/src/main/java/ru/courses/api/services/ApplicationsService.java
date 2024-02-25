package ru.courses.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.courses.api.models.ApplicationModel;
import ru.courses.api.repositories.ApplicationsRepo;

import javax.swing.*;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationsService {
    private final ApplicationsRepo repo;
    private final GroupsService gService;

    @Autowired
    public ApplicationsService(ApplicationsRepo applicationsRepo, GroupsService gService) {
        this.repo = applicationsRepo;
        this.gService = gService;
    }

    private ApplicationModel getApplicationByClientAndCourse(int clientId, int courseId) {
        return  repo.getByClient_IdAndCourse_Id(clientId, courseId);
    }

    public ApplicationModel createApplication(ApplicationModel application) throws Exception {
        application.setStatus("consideration");
        application.setSubmissionDate(new Date());

        if (getApplicationByClientAndCourse(application.getClient().getId(), application.getCourse().getId()) != null) {
            throw new Exception("application exists");
        }

        return repo.save(application);
    }

    public ApplicationModel getApplicationByID(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<ApplicationModel> getAllApplications() {
        return repo.findAll();
    }

    public ApplicationModel updateApplication(int id, ApplicationModel newApplication) throws Exception {
        ApplicationModel application = getApplicationByID(id);

        if (application == null) {
            throw new NullPointerException("Application id = " + newApplication.getId() + ". Not found");
        } else if (newApplication.getGroup() != null && application.getGroup() != newApplication.getGroup() &&
                   gService.getStudentsCount(newApplication.getGroup().getId()) + 1 > newApplication.getGroup().getCapacity()) {
            throw new Exception("capacity error");
        }
        else {
            application.setStatus(newApplication.getStatus());
            application.setClient(newApplication.getClient());
            application.setCourse(newApplication.getCourse());
            application.setSubmissionDate(newApplication.getSubmissionDate());
            application.setGroup(newApplication.getGroup());

            return repo.save(application);
        }
    }

    public void removeApplicationByID(int id) {
        repo.deleteById(id);
    }
}
