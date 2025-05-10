package actors.gods;

/**
 * Represents the Artemis God card, which has a unique build and move behaviour.
 */
public class Artemis extends God {

    /**
     * Constructs an Artemis God and loads its image.
     */
    public Artemis() {
        super("Artemis");
        loadGodImage("/godImages/artemis.png");
    }

    /**
     * Executes Artemis-specific build behaviour.
     */
    @Override
    public void build() {
        // Artemis-specific build behavior
    }

    /**
     * Executes Artemis-specific move behaviour.
     */
    @Override
    public void move() {
        // Artemis-specific move behavior
    }
}