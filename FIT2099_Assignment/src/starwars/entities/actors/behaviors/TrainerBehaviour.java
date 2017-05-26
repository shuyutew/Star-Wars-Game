package starwars.entities.actors.behaviors;

import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;

public class TrainerBehaviour extends BehaviourInterface{
	
	/**
	 * This behaviour is held by <code>SWActor</code>s with high Force Ability Level 
	 * and is capable of training the <code>Player</code>
	 * 
	 * If the target of this <code>SWActor</code> has Jedi Student capability,
	 * this <code>SWActor</code> will train this target to increase his force ability level
	 * according to this behaviour.
	 * 
	 * @param actor the <code>SWActor</code> that is being called
	 * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
	 */

	public TrainerBehaviour(SWActor actor, SWWorld world) {
		super(actor, world);
	}
	
	/**
	 * Checks for any entities at the actor's location and if the entity has jedi_student capability
	 * means that the entity can be trained, hence this behaviour will be practiced on
	 * that entity by this actor
	 */
    @Override
    public boolean ExecuteBehaviour() {
    	for (SWEntityInterface entity : getLocalEntites()){
    		if(entity.hasCapability(Capability.JEDI_Student)){
    			actor.say(actor.getShortDescription() + " says: " + entity.getShortDescription() + ", I can train you in the way of The Force. ");
    			return true;
    		}
    	}
    	return false;
    }

}
