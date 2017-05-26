package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.DroidParts;

public class RebuildDroid extends SWAffordance {
	
	/**
	 * Creates an action that enables a droid with a droid_mechanic capability
	 * to rebuilt a droid using droid parts to a disabled droid
	 * so that it is no longer immobile.
	 * 
	 * @param theTarget the target being rebuilt
	 * @param m <code>MessageRenderer</code> to display messages.
	 */

	public RebuildDroid(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}
	
	@Override
    public boolean canDo(SWActor actor) {
		return actor.hasCapability(Capability.DROID_MECHANIC) && actor.getItemCarried() instanceof DroidParts;
    }
	
	/**
	 * As the target is being rebuilt, it will get its hitpoints restored by half of its maxhitpoints
	 */
    @Override
    public void act(SWActor actor) {
    	actor.setItemCarried(null);
    	getTarget().heal(getTarget().getmaxHitpoints() / 2);
    	actor.say(String.format("%s rebuilds %s.", actor.getShortDescription(), target.getShortDescription()));
    }
    
    @Override
    public String getDescription() {
    	return String.format("Rebuild %s with droid parts", target.getShortDescription());
    }

}
