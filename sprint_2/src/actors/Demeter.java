package actors;

public class Demeter extends God {

    public Demeter() {
        super("Demeter");
        loadGodImage("/godImages/demeter.png");
    }

    @Override
    public void build() {
        // Demeter-specific build behavior
    }

    @Override
    public void move() {
        // Demeter-specific move behavior
    }
}