import processing.core.PImage;
import java.util.List;

public abstract class AnimatedEntity extends Entity implements Animating{
    private final double animationPeriod;

    protected AnimatedEntity(String id, Point position, List<PImage> images, double animationPeriod){
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    @Override
    public double getAnimationPeriod(){
        return animationPeriod;
    }
    protected void scheduleAnimation(EventScheduler scheduler){
        scheduler.scheduleEvent(this, new AnimationAction(this,0), animationPeriod);
    }
    @Override
    public void updateImage(){
        setImageIndex(getImageIndex() + 1);
    }
}
