package com.callmextrm.user_service.role;

import com.callmextrm.user_service.exception.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
    private final RolesRepo rolesdao;

    public RolesService(RolesRepo rolesdao) {
        this.rolesdao = rolesdao;
    }

    //Show all roles SERVICE
    public List<Role> showAllRoles() {
        return rolesdao.findAll();
    }

    //Add role SERVICE
    public Role addRole(Role role) {
        return rolesdao.save(role);
    }

    //Update role SERVICE
    public Role updateRole(Long id, Role newRole) {
        Role role = rolesdao.findById(id).orElseThrow(() -> new ResourceNotFound("Wrong role id"));
        role.setRolename(newRole.getRolename());
        return rolesdao.save(role);

    }

}
