package ru.courses.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.courses.api.models.GroupModel;
import ru.courses.api.repositories.GroupsRepo;

import java.util.List;

@Service
public class GroupsService {
    private final GroupsRepo repo;

    @Autowired
    public GroupsService(GroupsRepo groupsRepo) {
        this.repo = groupsRepo;
    }

    public GroupModel createGroup(GroupModel group) {
        return repo.save(group);
    }

    public GroupModel getGroupByID(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<GroupModel> getAllGroups() {
        return repo.findAll();
    }

    public GroupModel updateGroup(GroupModel newGroup) {
        GroupModel group = getGroupByID(newGroup.getId());

        if (group == null) {
            throw new RuntimeException("Group id = " + newGroup.getId() + ". Not found");
        } else {
            group.setCapacity(newGroup.getCapacity());
            group.setName(newGroup.getName());
            group.setCourse(newGroup.getCourse());

            return repo.save(group);
        }
    }

    public void removeGroupByID(int id) {
        repo.deleteById(id);
    }
}
