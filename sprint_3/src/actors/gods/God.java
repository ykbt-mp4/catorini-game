package actors.gods;

import actions.ActionList;

/**
 * Abstract representation of a God card used by a player.
 * Each God provides a unique set of actions and attributes such as name,
 * description, and image path.
 */
public abstract class God {

    protected String name;
    protected String description;
    protected String imagePath;
    protected ActionList actions;

    /**
     * Constructs a God instance with a specified name, description, and image path.
     * @param name        name of the god
     * @param description the description of the god's abilities
     * @param imagePath   the path to the god's image resource
     */
    public God(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.actions = new ActionList();
        this.imagePath = imagePath;
    }

    /**
     * Gets the name of the god.
     * @return the god's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the god.
     * @return the god's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the image path of the god.
     * @return the path to the god's image resource
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Gets the list of actions associated with this god.
     * @return an ActionList representing the god's abilities
     */
    public ActionList getActions() {
        return actions;
    }

}