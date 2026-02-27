import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Fairy extends ActiveEntity {

    public Fairy(String id,
                 Point position,
                 List<PImage> images,
                 double animationPeriod,
                 double behaviorPeriod) {

        super(id, position, images, animationPeriod, behaviorPeriod);
    }

    @Override
    public void updateImage() {
        setImageIndex(getImageIndex() + 1);
    }

    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(getPosition(), List.of(Stump.class));

        if (target.isPresent()) {
            Entity stump = target.get();

            if (moveTo(world, stump, scheduler)) {
                Point position = stump.getPosition();

                world.removeEntity(scheduler, stump);

                Entity sapling = new Sapling(
                        Entity.SAPLING_KEY + "_" + stump.getId(),
                        position,
                        imageLibrary.get(Entity.SAPLING_KEY)
                );

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageLibrary);
            }
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    private boolean moveTo(World world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacentTo(target.getPosition())) {
            return true;
        }

        Point nextPos = nextPosition(world, target.getPosition());
        if (!getPosition().equals(nextPos)) {
            world.moveEntity(scheduler, this, nextPos);
        }

        return false;
    }

    private Point nextPosition(World world, Point destination) {
        int deltaX = destination.x - getPosition().x;
        int deltaY = destination.y - getPosition().y;

        Point horiz = new Point(getPosition().x + Integer.signum(deltaX), getPosition().y);
        Point vert  = new Point(getPosition().x, getPosition().y + Integer.signum(deltaY));

        boolean horizBlocked = isBlocked(world, horiz);
        boolean vertBlocked  = isBlocked(world, vert);

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            if (!horizBlocked) return horiz;
            if (!vertBlocked)  return vert;
        } else {
            if (!vertBlocked)  return vert;
            if (!horizBlocked) return horiz;
        }

        return getPosition();
    }

    private boolean isBlocked(World world, Point p) {
        if (!world.inBounds(p)) return true;
        return world.isOccupied(p);
    }
}
