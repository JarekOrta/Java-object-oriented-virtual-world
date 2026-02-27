public interface Behavior {
    double getBehaviorPeriod();
    void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler);
}
