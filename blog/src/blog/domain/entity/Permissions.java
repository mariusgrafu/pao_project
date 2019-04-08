package src.blog.domain.entity;

public class Permissions {
    private boolean changeName;
    private boolean seeArticle;
    private boolean postArticle;
    private boolean editArticle;

    public Permissions() {
        this.changeName = false;
        this.seeArticle = false;
        this.postArticle = false;
        this.editArticle = false;
    }

    public Permissions(boolean changeName, boolean seeArticle, boolean postArticle, boolean editArticle) {
        this.changeName = changeName;
        this.seeArticle = seeArticle;
        this.postArticle = postArticle;
        this.editArticle = editArticle;
    }

    public boolean getChangeName() {
        return changeName;
    }

    public void setChangeName(boolean changeName) {
        this.changeName = changeName;
    }

    public boolean getSeeArticle() {
        return seeArticle;
    }

    public void setSeeArticle(boolean seeArticle) {
        this.seeArticle = seeArticle;
    }

    public boolean getPostArticle() {
        return postArticle;
    }

    public void setPostArticle(boolean postArticle) {
        this.postArticle = postArticle;
    }

    public boolean getEditArticle() {
        return editArticle;
    }

    public void setEditArticle(boolean editArticle) {
        this.editArticle = editArticle;
    }

    @Override
    public String toString() {
        return "" +
                "changeName : " + changeName +
                ", seeArticle : " + seeArticle +
                ", postArticle : " + postArticle +
                ", editArticle : " + editArticle;
    }
}
