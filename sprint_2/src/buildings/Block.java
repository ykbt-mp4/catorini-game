package buildings;

public class Block extends Building {

    private String name = "Block";
    private int height;

    public Block(String name) {
        super(name);
        loadBuildingImage("/images/block.png");

    }
    
    public int getHeight() {
        return height;
    }
    
}
