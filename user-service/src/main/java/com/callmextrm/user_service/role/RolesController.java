package com.callmextrm.user_service.role;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {
    private final RolesService service;

    public RolesController(RolesService service) {
        this.service = service;
    }

    //Show all roles CONTROLLER
    @GetMapping("")
    public List<Role> showAllRoles() {
        return service.showAllRoles();
    }

    //Add role CONTROLLER
    @PostMapping("/add")
    public Role addController(@Valid @RequestBody Role role) {
        return service.addRole(role);
    }

    //Update role CONTROLLER
    @PutMapping("/update/{id}")
    public Role updateRole(@Valid @PathVariable Long id, @RequestBody Role role) {
        return service.updateRole(id, role);
    }
}
