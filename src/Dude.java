import processing.core.PImage;
import java.util.Optional;
import java.util.List;

public class Dude extends ActiveEntity {
    private final int resourceLimit;
    private int resourceCount;

    public Dude(String id,
                Point position,
                List<PImage> images,
                double animationPeriod,
                double behaviorPeriod,
                int resourceCount,
                int resourceLimit) {

        super(id, position, images, animationPeriod, behaviorPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> target = findTarget(world);

        if (target.isEmpty()
                || !moveTo(world, target.get(), scheduler)
                || !transformIfNeeded(world, scheduler, imageLibrary)) {

            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    private Optional<Entity> findTarget(World world) {
        if (resourceCount >= resourceLimit) {
            return world.findNearest(getPosition(), List.of(House.class));
        } else {
            return world.findNearest(getPosition(), List.of(Tree.class, Sapling.class));
        }
    }

    /**
     * Move one step toward target; return true if we're already adjacent (aka "arrived").
     */
    private boolean moveTo(World world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacentTo(target.getPosition())) {
            // If adjacent to a Tree/Sapling, "collect" from it (decrease its health)
            if (target instanceof Tree tree) {
                tree.decreaseHealth();
            } else if (target instanceof Sapling sapling) {
                sapling.decreaseHealth();
            }
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    /**
     * Same movement rule as old code: step horizontally or vertically,
     * avoid occupied cells unless it's a Stump.
     */
    private Point nextPosition(World world, Point destination) {
        int deltaX = destination.x - getPosition().x;
        int deltaY = destination.y - getPosition().y;

        Point horiz = new Point(getPosition().x + Integer.signum(deltaX), getPosition().y);
        Point vert = new Point(getPosition().x, getPosition().y + Integer.signum(deltaY));

        boolean horizBlocked = isBlockedForDude(world, horiz);
        boolean vertBlocked = isBlockedForDude(world, vert);

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            if (!horizBlocked) return horiz;
            if (!vertBlocked) return vert;
        } else {
            if (!vertBlocked) return vert;
            if (!horizBlocked) return horiz;
        }

        return getPosition();
    }

    private boolean isBlockedForDude(World world, Point p) {
        if (!world.inBounds(p)) return true;
        if (!world.isOccupied(p)) return false;

        Optional<Entity> occ = world.getOccupant(p);
        return occ.isPresent() && !(occ.get() instanceof Stump);
    }

    /**
     * Handles the "carry" and "deposit" transforms by swapping the Dude entity
     * (same as old code, just class-based now).
     */
    private boolean transformIfNeeded(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (resourceCount < resourceLimit) {
            resourceCount++;

            if (resourceCount == resourceLimit) {
                Dude carrying = new Dude(
                        getId(),
                        getPosition(),
                        imageLibrary.get(Entity.DUDE_KEY + "_carry"),
                        getAnimationPeriod(),
                        getBehaviorPeriod(),
                        resourceCount,
                        resourceLimit
                );

                world.removeEntity(scheduler, this);
                world.addEntity(carrying);
                carrying.scheduleActions(scheduler, world, imageLibrary);
                return true;
            }
        } else {
            Dude empty = new Dude(
                    getId(),
                    getPosition(),
                    imageLibrary.get(Entity.DUDE_KEY),
                    getAnimationPeriod(),
                    getBehaviorPeriod(),
                    0,
                    resourceLimit
            );

            world.removeEntity(scheduler, this);
            world.addEntity(empty);
            empty.scheduleActions(scheduler, world, imageLibrary);
            return true;
        }

        return false;
    }
}

