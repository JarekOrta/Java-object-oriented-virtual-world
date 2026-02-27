/** A scheduled action to be carried out by a specific entity. */
public abstract class Action {
    /**
     * Enumerated type defining different kinds of actions that entities take in the world.
     * Specific values are assigned to the action's 'kind' instance variable at initialization.
     * There are two types of actions: animations (image updates) and behaviors (logic updates).
     */
    private final Entity entity;

    public Action(Entity entity){
        this.entity = entity;
    }

    public Entity getEntity(){
        return entity;
    }

    public abstract void execute(EventScheduler scheduler);
}
