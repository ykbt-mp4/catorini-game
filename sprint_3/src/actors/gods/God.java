package actors.gods;

import actions.ActionList;

public abstract class God {

    protected String name;
    protected String description;
    protected String imagePath;
    protected ActionList actions;

    public God(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.actions = new ActionList();
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ActionList getActions() {
        return actions;
    }

}