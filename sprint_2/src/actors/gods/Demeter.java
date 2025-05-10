package actors.gods;

/**
 * Represents the Demeter God card, which has a unique build and move behaviour.
 */
public class Demeter extends God {

    /**
     * Constructs a Demeter God and loads its image.
     */
    public Demeter() {
        super("Demeter");
        loadGodImage("/godImages/demeter.png");
    }

    /**
     * Executes Demeter-specific build behaviour.
     */
    @Override
    public void build() {
        // Demeter-specific build behavior
    }

    /**
     * Executes Demeter-specific move behaviour.
     */
    @Override
    public void move() {
        // Demeter-specific move behavior
    }
}