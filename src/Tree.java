import processing.core.PImage;
import java.util.List;


public class Tree extends ActiveEntity {
    private int health;

    public Tree(String id, Point position, List<PImage>images, double animationPeriod, double behaviorPeriod, int health){
        super(id, position, images, animationPeriod, behaviorPeriod);
        this.health = health;
    }
//commenting to push
    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (!transformTree(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    private boolean transformTree(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (health <= 0) {
            Entity stump = Entity.createStump(
                    Entity.STUMP_KEY + "_" + getId(),
                    getPosition(),
                    imageLibrary.get(Entity.STUMP_KEY)
            );

            world.removeEntity(scheduler, this);
            world.addEntity(stump);
            return true;
        }
        return false;
    }

    public void decreaseHealth() {
        health = health - 1;
    }
}




