package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWRobots;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;

public class Disowned extends SWAffordance {

	public Disowned(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}
	
	@Override
	/**
	 * This should always returns true -- anything that's got a visible disowned affordance
	 * should be being held by the actor that can see the affordance.
	 * 
	 * @param a the SWActor we are querying
	 * @return true
	 */
	public boolean canDo(SWActor a) {
		return a.getDroidOwned().equals(target);
	}
	
	@Override
	/**
	 * Perform the disowned action.
	 * 
	 * Release the droid from the actor's control
	 * 
	 * @param a the SWActor that is disowning the target
	 */
	public void act(SWActor a) {
		if (a.getDroidOwned() == null) { // the actor does not own any droids
			// This should really throw an exception, but let's just use a message for now.
			a.say("Disowned affordance called by actor that does not own any droids. This should never happen");
		}
		else {
			if (target instanceof SWRobots) {
				((SWRobots) target).disowned();
				a.setNotOwner();
				target.removeAffordance(this);
			}
		}
	}


	@Override
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author dsquire
	 * @return String comprising "leave " and the short description of the target of this Affordance
	 */
	public String getDescription() {
		return "Disowned " + target.getShortDescription();
	}
	
}
