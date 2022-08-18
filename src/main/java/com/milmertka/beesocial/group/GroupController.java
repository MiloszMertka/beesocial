package com.milmertka.beesocial.group;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private static final String GROUP_CREATED_MESSAGE = "Group successfully created";

    private final GroupService groupService;

    @GetMapping("/{name}")
    public ResponseEntity<Group> getGroupByName(@PathVariable String name) {
        Group group = groupService.getGroupByName(name);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Group>> getListOfGroups(@RequestBody String name) {
        List<Group> groups = groupService.findGroupsByName(name);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createGroup(@RequestBody GroupRequest groupRequest) {
        groupService.createGroup(groupRequest);
        return new ResponseEntity<>(GROUP_CREATED_MESSAGE, HttpStatus.CREATED);
    }
}
