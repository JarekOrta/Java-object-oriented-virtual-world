public class AnimationAction extends Action {
    private final int repeatCount;

    public AnimationAction(Entity entity, int repeatCount) {
        super(entity);
        this.repeatCount = repeatCount;
    }

    @Override
    public void execute(EventScheduler scheduler) {
        Entity entity = getEntity();
        if (entity instanceof Animating animating){
          animating.updateImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(entity, new AnimationAction(entity, Math.max(repeatCount - 1, 0)), animating.getAnimationPeriod());
        }}}}


