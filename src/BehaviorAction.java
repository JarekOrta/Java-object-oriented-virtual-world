public class BehaviorAction extends Action{
    private final World world;
    private final ImageLibrary imageLibrary;

    public BehaviorAction(Entity entity, Behavior behavior,  World world, ImageLibrary imageLibrary){
        super(entity);
        this.world = world;
        this.imageLibrary = imageLibrary;
    }
    @Override
    public void execute(EventScheduler scheduler){
        Entity entity = getEntity();

        if (entity instanceof Behavior behavior) {
            behavior.executeBehavior(world, imageLibrary, scheduler);
        }
    }
}
