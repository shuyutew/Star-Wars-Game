package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWRobots;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class Owned extends SWAffordance {

	public Owned(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	/**
	 * Returns if or not this <code>Owned</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is has not owned any droids already.
	 *  
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can own this droid, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	
	@Override
	public boolean canDo(SWActor a) {
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWRobots;
		SWRobots targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWRobots) target;
		}
		
		if (a.getDroidOwned() == null && !(targetActor.getStatus())){
			return true;
		}
		return false;
	}
	
	/**
	 * Perform the <code>Owned</code> action by setting the droids by the <code>SWActor</code> to the target (
	 * the <code>SWActor a</code>'s droids would be the target of this <code>Owned</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 

	 * @param 	a the <code>SWActor</code> that is taking the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof SWRobots) {
			SWEntityInterface theDroid = (SWEntityInterface) target;
			a.setDroidOwned(theDroid, theDroid.getHitpoints(), theDroid.getShortDescription(), this);
			
		}
	}
	
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author ram
	 * @return String comprising "take " and the short description of the target of this <code>Take</code>
	 */
	@Override
	public String getDescription() {
		return "Own " + target.getShortDescription();
	}


}
