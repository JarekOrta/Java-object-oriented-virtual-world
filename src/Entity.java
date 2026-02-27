import processing.core.PImage;
import java.util.List;


public abstract class Entity {

    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;

    protected Entity(String id, Point position, List<PImage> images){ //protected bc if its private, other subclasses wont be able to use super
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
}
public String log() {
    if (id.isEmpty()) {
        return null;
    }
    return String.format("%s %d %d %d", id, position.x, position.y, imageIndex);
}

public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
}

public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
    throw new UnsupportedOperationException(
            String.format("executeBehavior not supported for %s", getClass().getSimpleName()));
}

    public String getId(){
        return id;
    }
    public Point getPosition(){
        return position;
    }

    public void setPosition(Point position){
        this.position = position;
    }

    public List<PImage> getImages(){
        return images;
    }
    public int getImageIndex(){
        return imageIndex;
    }
    protected void setImageIndex(int imageIndex){
        this.imageIndex = imageIndex;
    }

    public static Entity createHouse(String id, Point position, List<PImage> images){
        return new House(id, position, images);
}
    public static Entity createWater(String id, Point position, List<PImage> images){
        return new Water(id, position, images);
}
    public static Entity createStump(String id, Point position, List<PImage> images){
        return new Stump(id, position, images);
}
    public static Entity createSapling(String id, Point position, List<PImage> images){
        return new Sapling(id, position, images);
    }
    public static Entity createTree(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int health){
        return new Tree(id, position, images, animationPeriod, behaviorPeriod, health);
    }
    public static Entity createMushroom(String id, Point position, List<PImage> images, double behaviorPeriod){
        return new Mushroom(id, position, images, behaviorPeriod);
    }
    public static Entity createDude(String id,
                                    Point position,
                                    List<PImage> images,
                                    double animationPeriod,
                                    double behaviorPeriod,
                                    int resourceCount,
                                    int resourceLimit) {
        return new Dude(id, position, images, animationPeriod, behaviorPeriod, resourceCount, resourceLimit);
    }

    public static Entity createFairy(String id,
                                     Point position,
                                     List<PImage> images,
                                     double animationPeriod,
                                     double behaviorPeriod) {
        return new Fairy(id, position, images, animationPeriod, behaviorPeriod);
    }


    public static final int DUDE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int DUDE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int DUDE_PARSE_PROPERTY_RESOURCE_LIMIT_INDEX = 2;
    public static final int DUDE_PARSE_PROPERTY_COUNT = 3;

    public static final int FAIRY_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int FAIRY_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int FAIRY_PARSE_PROPERTY_COUNT = 2;

    public static final int HOUSE_PARSE_PROPERTY_COUNT = 0;

    public static final int ENTITY_PROPERTY_KEY_INDEX = 0;
    public static final int ENTITY_PROPERTY_ID_INDEX = 1;
    public static final int ENTITY_PROPERTY_POSITION_X_INDEX = 2;
    public static final int ENTITY_PROPERTY_POSITION_Y_INDEX = 3;
    public static final int ENTITY_PROPERTY_COLUMN_COUNT = 4;

    public static final int MUSHROOM_PARSE_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int MUSHROOM_PARSE_PROPERTY_COUNT = 1;

    public static final int SAPLING_PARSE_PROPERTY_COUNT = 0;
    public static final int STUMP_PARSE_PROPERTY_COUNT = 0;
    public static final int WATER_PARSE_PROPERTY_COUNT = 0;

    public static final int TREE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int TREE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int TREE_PARSE_PROPERTY_HEALTH_INDEX = 2;
    public static final int TREE_PARSE_PROPERTY_COUNT = 3;

    public static final String TREE_KEY = "tree";
    public static final String STUMP_KEY = "stump";
    public static final String SAPLING_KEY = "sapling";
    public static final String WATER_KEY = "water";
    public static final String HOUSE_KEY = "house";
    public static final String MUSHROOM_KEY = "mushroom";
    public static final String DUDE_KEY = "dude";
    public static final String FAIRY_KEY = "fairy";


}