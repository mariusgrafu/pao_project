package src.blog.domain.entity;

import java.sql.ResultSet;
import java.util.Objects;

public class Role {
    private String id;
    private String title;
    private Permissions permissions;

    private boolean defaultRole;
    private boolean staff;


    public Role(String title, Permissions permissions, boolean isDefault, boolean isStaff) {
        this.title = title;
        this.permissions = permissions;
        this.defaultRole = isDefault;
        this.staff = isStaff;
    }

    public static Role getRoleFromCSV (String line) {
        String[] values = line.split(",");
        String title = values[1];
        boolean isDefault = Boolean.parseBoolean(values[2]);
        boolean isStaff = Boolean.parseBoolean(values[3]);
        Role newRole = new Role(title, new Permissions(
                Boolean.parseBoolean(values[4]),
                Boolean.parseBoolean(values[5]),
                Boolean.parseBoolean(values[6]),
                Boolean.parseBoolean(values[7])
        ), isDefault, isStaff);
        newRole.setId(values[0]);
        return newRole;
    }

    public static Role getRoleFromResultSet (ResultSet resultSet) throws Exception {
        String title = resultSet.getString("title");
        boolean isDefault = resultSet.getBoolean("defaultRole");
        boolean isStaff = resultSet.getBoolean("staff");
        Role newRole = new Role(title, new Permissions(
                resultSet.getBoolean("can_changeName"),
                resultSet.getBoolean("can_seeArticle"),
                resultSet.getBoolean("can_postArticle"),
                resultSet.getBoolean("can_editArticle")
        ), isDefault, isStaff);
        newRole.setId(resultSet.getString("id"));
        return newRole;
    }

    public Role(String title, Permissions permissions) {
        this.title = title;
        this.permissions = permissions;
        this.defaultRole = false;
        this.staff = false;
    }

    public Role(String title) {
        this.title = title;
        this.permissions = new Permissions();
        this.defaultRole = false;
        this.staff = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public boolean isDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(boolean defaultRole) {
        this.defaultRole = defaultRole;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public String toCSV () {
        return id +
                "," + title +
                "," + defaultRole +
                "," + staff +
                "," + permissions.toCSV();
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder(title);
        if(staff) r.append(" [staff]");
        return r.toString();
////                "[" + id + "] " +
//                        title +
////                " [" + permissions + "]" +
//                " [default role = " + defaultRole + "]" +
//                " [staff = " + staff + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return defaultRole == role.defaultRole &&
                staff == role.staff &&
                Objects.equals(id, role.id) &&
                Objects.equals(title, role.title) &&
                Objects.equals(permissions, role.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, permissions, defaultRole, staff);
    }
}
