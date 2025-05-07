package buildings;

// Santorini dome that is placed on top of a block to prevent the block from being built upon again.
public class Dome extends Building{
    
    private String name = "Dome";

    public Dome(String name) {
        super(name);
        loadBuildingImage("/images/dome.png");
    }
}
