package ru.courses.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.courses.api.models.ApplicationModel;
import ru.courses.api.repositories.ApplicationsRepo;

import java.util.List;

@Service
public class ApplicationsService {
    private final ApplicationsRepo repo;

    @Autowired
    public ApplicationsService(ApplicationsRepo applicationsRepo) {
        this.repo = applicationsRepo;
    }

    public ApplicationModel createApplication(ApplicationModel application) {
        return repo.save(application);
    }

    public ApplicationModel getApplicationByID(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<ApplicationModel> getAllApplications() {
        return repo.findAll();
    }

    public ApplicationModel updateApplication(ApplicationModel newApplication) {
        ApplicationModel application = getApplicationByID(newApplication.getId());

        if (application == null) {
            throw new RuntimeException("Application id = " + newApplication.getId() + ". Not found");
        } else {
            application.setStatus(newApplication.getStatus());
            application.setClient(newApplication.getClient());
            application.setCourse(newApplication.getCourse());
            application.setSubmissionDate(newApplication.getSubmissionDate());

            return repo.save(application);
        }
    }

    public void removeApplicationByID(int id) {
        repo.deleteById(id);
    }
}
