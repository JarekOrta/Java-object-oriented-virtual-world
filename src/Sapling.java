import processing.core.PImage;
import java.util.List;
//done
public class Sapling extends ActiveEntity {
        public static final int HEALTH_LIMIT = 5;
        public static final double ANIMATION_PERIOD = 0.0125;
        public static final double BEHAVIOR_PERIOD = 2.0;

        public static final double TREE_RANDOM_ANIMATION_PERIOD_MIN = 0.1;
        public static final double TREE_RANDOM_ANIMATION_PERIOD_MAX = 1.0;
        public static final double TREE_RANDOM_BEHAVIOR_PERIOD_MIN = 0.01;
        public static final double TREE_RANDOM_BEHAVIOR_PERIOD_MAX = 0.10;
        public static final int TREE_RANDOM_HEALTH_MIN = 1;
        public static final int TREE_RANDOM_HEALTH_MAX = 3;

        private int health;

        public Sapling(String id, Point position, List<PImage> images) {
            super(id, position, images, ANIMATION_PERIOD, BEHAVIOR_PERIOD);
            this.health = 0;
        }

        @Override
        public void updateImage() {
            if (health <= 0) {
                setImageIndex(0);
            } else if (health < HEALTH_LIMIT) {
                int idx = getImages().size() * health / HEALTH_LIMIT;
                setImageIndex(idx);
            } else {
                setImageIndex(getImages().size() - 1);
            }
        }
        public void decreaseHealth() {
        health = health - 1;
    }

        @Override
        public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
            health = health + 1;

            if (!transformSapling(world, scheduler, imageLibrary)) {
                scheduleBehavior(scheduler, world, imageLibrary);
            }
        }

        private boolean transformSapling(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
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

            if (health >= HEALTH_LIMIT) {
                Entity tree = Entity.createTree(
                        Entity.TREE_KEY + "_" + getId(),
                        getPosition(),
                        imageLibrary.get(Entity.TREE_KEY),
                        NumberUtil.getRandomDouble(TREE_RANDOM_ANIMATION_PERIOD_MIN, TREE_RANDOM_ANIMATION_PERIOD_MAX),
                        NumberUtil.getRandomDouble(TREE_RANDOM_BEHAVIOR_PERIOD_MIN, TREE_RANDOM_BEHAVIOR_PERIOD_MAX),
                        NumberUtil.getRandomInt(TREE_RANDOM_HEALTH_MIN, TREE_RANDOM_HEALTH_MAX)
                );

                world.removeEntity(scheduler, this);

                world.addEntity(tree);
                tree.scheduleActions(scheduler, world, imageLibrary);
                return true;
            }

            return false;
        }
    }

