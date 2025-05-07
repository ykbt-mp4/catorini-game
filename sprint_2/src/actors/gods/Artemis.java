package actors.gods;

public class Artemis extends God {

    public Artemis() {
        super("Artemis");
        loadGodImage("/godImages/artemis.png");
    }

    @Override
    public void build() {
        // Artemis-specific build behavior
    }

    @Override
    public void move() {
        // Artemis-specific move behavior
    }
}