import java.util.List;
import processing.core.PImage;

public abstract class ActiveEntity extends AnimatedEntity implements Behavior {
    private final double behaviorPeriod;

    protected ActiveEntity(String id, Point position, List<PImage> images,
                           double animationPeriod, double behaviorPeriod) {
        super(id, position, images, animationPeriod);
        this.behaviorPeriod = behaviorPeriod;
    }

    @Override
    public double getBehaviorPeriod() {
        return behaviorPeriod;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        if (getAnimationPeriod() > 0) {
            scheduleAnimation(scheduler);
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    protected void scheduleBehavior(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(
                this,
                new BehaviorAction(this, this, world, imageLibrary),
                behaviorPeriod);
    }
}
