package src.blog.domain.repository;

import src.blog.domain.entity.Role;

import java.util.ArrayList;

public class RoleRepository {
    private ArrayList<Role> roles = new ArrayList<Role>();
    private int lastId;

    public Role addNewRole(Role newRole) {
        newRole.setId(lastId++);
        roles.add(newRole);

        return newRole;
    }

    public Role getRoleById(int id) {
        for(Role role : roles) {
            if(role.getId() == id) return role;
        }
        return null;
    }

    public Role getDefaultRole () {
        for(Role role: roles) {
            if(role.isDefaultRole()) return role;
        }

        return null;
    }

    public ArrayList<Role> getAllRoles () {
        return roles;
    }
}
