package com.milmertka.beesocial.group;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {
    private static final String GROUP_NOT_FOUND_MESSAGE = "Group with name %s does not exist";
    private static final String GROUP_ALREADY_EXISTS_MESSAGE = "Group with name %s already exists";

    private final GroupRepository groupRepository;

    public Group getGroupByName(String name) {
        return groupRepository.findByName(name)
                .orElseThrow(() -> new IllegalStateException(String.format(GROUP_NOT_FOUND_MESSAGE, name)));
    }

    public List<Group> findGroupsByName(String name) {
        return groupRepository.findAllByNameIsLike(name);
    }

    public void createGroup(GroupRequest groupRequest) {
        final String name = groupRequest.name();

        if (groupExists(name)) {
            throw new IllegalStateException(String.format(GROUP_ALREADY_EXISTS_MESSAGE, name));
        }

        Group group = new Group(name, groupRequest.description());
        groupRepository.save(group);
    }

    private Boolean groupExists(String name) {
        return groupRepository.findByName(name).isPresent();
    }
}
