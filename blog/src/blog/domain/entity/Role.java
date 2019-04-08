package src.blog.domain.entity;

public class Role {
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "[" + id + "] " + title +
                " [" + permissions + "]" +
                " [default role = " + defaultRole + "]" +
                " [staff = " + staff + ']';
    }
}
