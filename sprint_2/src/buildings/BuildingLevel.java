package buildings;

    public enum BuildingLevel {
        GROUND(0, null),
        LEVEL_ONE(1, "/images/block1.png"),
        LEVEL_TWO(2, "/images/block2.png"),
        LEVEL_THREE(3, "/images/block3.png"),
        DOME(4, "/images/dome.png");


        private final int height;
        private final String imagePath;

        BuildingLevel(int height, String imagePath) {
            this.height = height;
            this.imagePath = imagePath;
        }

        public int getHeight() {
            return height;
        }

        public String getImagePath() {
            return imagePath;
        }

        public boolean isBuildable() {
            return this != DOME;
        }

        public BuildingLevel getNextLevel() {
            switch (this) {
                case GROUND: return LEVEL_ONE;
                case LEVEL_ONE: return LEVEL_TWO;
                case LEVEL_TWO: return LEVEL_THREE;
                case LEVEL_THREE: return DOME;
                default: return null; // DOME has no next level
            }
        }
    }

}
