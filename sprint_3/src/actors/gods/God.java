package actors.gods;

import actions.ActionList;

public abstract class God {

    protected String name;
    protected String description;
    protected ActionList actions;

    public God(String name, String description) {
        this.name = name;
        this.description = description;
        this.actions = new ActionList(); // default empty, subclasses should populate
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return name;
    }

    public ActionList getActions() {
        return actions;
    }

}