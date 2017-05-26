package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Fillable;
import starwars.Capability;

public class Repair extends SWAffordance{

	public Repair(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}
	
    @Override
    public boolean canDo(SWActor actor) {
    	boolean canRepair = actor.hasCapability(Capability.DROID_REPAIR) || (actor.getItemCarried() != null && actor.getItemCarried().hasCapability(Capability.DROID_REPAIR));
    	return getTarget().getHitpoints() < getTarget().getmaxHitpoints() && !getTarget().isDead() && canRepair;
    }
	
    @Override
    public void act(SWActor actor) {
    	getTarget().heal(20);
    	if (actor.hasCapability(Capability.DROID_REPAIR)) {
    		actor.say(String.format("%s repairs %s.", actor.getShortDescription(), target.getShortDescription()));
	    return;
    	}
    	
    	if (actor.getItemCarried() instanceof Fillable) { //TODO Some kind of Use method/interface? Why would this class think the repair item was Fillable?
    	    actor.say(String.format("%s drinks from the %s.", actor.getShortDescription(), target.getShortDescription()));
			actor.schedule(new Healing(actor, messageRenderer));
    	}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author shuyu
	 * @return String comprising "repair " and the short description of the target of this <code>Take</code>
	 */
	@Override
	public String getDescription() {
		return "Repair " + target.getShortDescription();
	}

}
