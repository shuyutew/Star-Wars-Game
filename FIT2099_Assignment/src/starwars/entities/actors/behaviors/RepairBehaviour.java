package starwars.entities.actors.behaviors;

import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.*;
import starwars.entities.DroidParts;

public class RepairBehaviour extends BehaviourInterface {
	
	/**
	 * This behaviour shows how the repair action is done by <code>SWActor</code>
	 * Repair can be done using droid parts and done on disabled droids
	 * 
	 * @param actor the <code>SWActor</code> that is being called
	 * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
	 */

    public RepairBehaviour(SWActor actor, SWWorld world) {
	super(actor, world);
    }

    /**
     * If the actor does not have droid part and there is droid part lying around its location,
     * if the actor is carrying an item, the actor will drop the item,
     * if the actor is not carrying any item, it will <code>Take</code> 
     * the droid parts
     * 
     * If the droid has droid parts with it and at its location there is a disabled droid
     * that needs to be rebuilt, it will rebuild the droid.
     * 
     * If the droid does not have droid parts with it and at its location there is a
     *  disabled droid, it will dismantle the droid
     *  
     *  If the droid encounters a repairable droid, it will repair the droid.
     */
    @Override
    public boolean ExecuteBehaviour() {
    	boolean hasDroidParts = actor.getItemCarried() instanceof DroidParts;
    	boolean nearDroidParts = getLocalEntityOfType(DroidParts.class) != null;
    	SWEntityInterface dismantleableDroid = getLocalEntityWithAffordance(DissambleDroid.class);
    	SWEntityInterface rebuildableDroid = getLocalEntityWithAffordance(RebuildDroid.class);
    	SWEntityInterface repairableDroid = getLocalEntityWithAffordance(Repair.class);

	if (!hasDroidParts && nearDroidParts) {
	    if (actor.getItemCarried() != null){
	    	actor.schedule(actor.getItemCarried().getAffordance(Leave.class));
	    }
	    else{
	    	actor.schedule(getLocalEntityOfType(DroidParts.class).getAffordance(Take.class));
		    return true;
	    }
	}

	if (hasDroidParts && rebuildableDroid != null) {
	    actor.schedule(rebuildableDroid.getAffordance(RebuildDroid.class));
	    return true;
	}

	if (!hasDroidParts && dismantleableDroid != null) {
	    actor.schedule(dismantleableDroid.getAffordance(DissambleDroid.class));
	    return true;
	}

	if (repairableDroid != null) {
	    actor.schedule(repairableDroid.getAffordance(Repair.class));
	    return true;
	}

	return false;
    }
}
