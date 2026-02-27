import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mushroom extends Entity implements Behavior {
    private final double behaviorPeriod;

    public Mushroom(String id, Point position, List<PImage> images, double behaviorPeriod) {
        super(id, position, images);
        this.behaviorPeriod = behaviorPeriod;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new BehaviorAction(this, this, world, imageLibrary), behaviorPeriod);
    }

    @Override
    public double getBehaviorPeriod(){
        return behaviorPeriod;
    }

    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        executeMushroomBehavior(world, imageLibrary, scheduler);
        scheduler.scheduleEvent(this, new BehaviorAction(this, this, world, imageLibrary), behaviorPeriod);
    }

    private void executeMushroomBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Point position = getPosition();

        List<Point> adjacentPositions = new ArrayList<>(List.of(
                new Point(position.x - 1, position.y),
                new Point(position.x + 1, position.y),
                new Point(position.x, position.y - 1),
                new Point(position.x, position.y + 1)
        ));
        Collections.shuffle(adjacentPositions);

        List<Point> mushroomBackgroundPositions = new ArrayList<>();
        List<Point> mushroomEntityPositions = new ArrayList<>();
        for (Point adjacentPosition : adjacentPositions) {
            if (world.inBounds(adjacentPosition) && !world.isOccupied(adjacentPosition) && world.hasBackground(adjacentPosition)) {
                Background bg = world.getBackgroundCell(adjacentPosition);
                if (bg.getId().equals("grass")) {
                    mushroomBackgroundPositions.add(adjacentPosition);
                } else if (bg.getId().equals("grass_mushrooms")) {
                    mushroomEntityPositions.add(adjacentPosition);
                }
            }
        }

        if (!mushroomBackgroundPositions.isEmpty()) {
            Point backgroundPosition = mushroomBackgroundPositions.get(0);

            Background background = new Background("grass_mushrooms", imageLibrary.get("grass_mushrooms"), 0);
            world.setBackgroundCell(backgroundPosition, background);
        } else if (!mushroomEntityPositions.isEmpty()) {
            Point newMushroomPosition = mushroomEntityPositions.get(0);

            Mushroom mushroom = new Mushroom(Entity.MUSHROOM_KEY, newMushroomPosition, imageLibrary.get(Entity.MUSHROOM_KEY), behaviorPeriod * 4.0);
            world.addEntity(mushroom);
            mushroom.scheduleActions(scheduler, world, imageLibrary);
        }

    }
}


