package starwars.entities.actors.behaviors;

import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;

public class TrainerBehaviour extends BehaviourInterface{

	public TrainerBehaviour(SWActor actor, SWWorld world) {
		super(actor, world);
	}
	
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
