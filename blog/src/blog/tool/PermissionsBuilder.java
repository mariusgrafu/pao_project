package src.blog.tool;

import src.blog.domain.entity.Permissions;

public class PermissionsBuilder {
    private final Permissions target = new Permissions();

    public PermissionsBuilder canChangeName () {
        this.target.setChangeName(true);
        return this;
    }

    public PermissionsBuilder canSeeArticle () {
        this.target.setSeeArticle(true);
        return this;
    }

    public PermissionsBuilder canPostArticle () {
        this.target.setPostArticle(true);
        return this;
    }

    public PermissionsBuilder canEditArticle () {
        this.target.setEditArticle(true);
        return this;
    }

    public PermissionsBuilder setAll () {
        this.target.setChangeName(true);
        this.target.setSeeArticle(true);
        this.target.setPostArticle(true);
        this.target.setEditArticle(true);
        return this;
    }

    public Permissions build () {
        return this.target;
    }
}
