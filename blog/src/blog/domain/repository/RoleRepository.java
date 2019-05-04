package src.blog.domain.repository;

import src.blog.domain.entity.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoleRepository {
    private Map<String, Role> roles = new HashMap<>();

    public Role addNewRole(Role newRole) {
        String id = UUID.randomUUID().toString();
        newRole.setId(id);
        roles.put(id, newRole);

        return newRole;
    }

    public Role loadRole(Role newRole) {
        roles.put(newRole.getId(), newRole);
        return newRole;
    }

    public Role getRoleById(String id) {
//        for(Role role : roles) {
//            if(role.getId() == id) return role;
//        }
        return roles.get(id);
//        return null;
    }

    public Role getRoleByTitle (String roleTitle) {
        for(Map.Entry<String, Role> entry : roles.entrySet()) {
            Role role = entry.getValue();
            if(role.getTitle().equals(roleTitle)) return role;
        }

        return null;
    }

    public Role getDefaultRole () {
        for (Map.Entry<String,Role> entry : roles.entrySet())
            if(entry.getValue().isDefaultRole()) return entry.getValue();

        return null;
    }

    public Map<String, Role> getAllRoles () {
        return roles;
    }
}
