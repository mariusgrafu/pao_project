package blog;

public class Role {
    private int id;
    private String title;
    private Permissions permissions;

    private boolean defaultRole;
    private boolean staff;


    static private int count;

    public Role(String title, Permissions permissions, boolean isDefault, boolean isStaff) {
        this.id = count++;
        this.title = title;
        this.permissions = permissions;
        this.defaultRole = isDefault;
        this.staff = isStaff;
    }

    public Role(String title, Permissions permissions) {
        this.id = count++;
        this.title = title;
        this.permissions = permissions;
        this.defaultRole = false;
        this.staff = false;
    }

    public Role(String title) {
        this.id = count++;
        this.title = title;
        this.permissions = new Permissions();
        this.defaultRole = false;
        this.staff = false;
    }
}
