package src.blog.domain.entity;

public class Preferences {
    private boolean compactView;

    public Preferences() {
        this.compactView = false;
    }

    public boolean hasCompactView() {
        return compactView;
    }

    public void setCompactView(boolean compactView) {
        this.compactView = compactView;
    }
}
