package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWRobots;
import starwars.SWRobotsInterface;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class Owned extends SWAffordance {

	public Owned(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}
	
	/**
	 * Returns if or not this <code>Owned</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is has not owned any droids already.
	 *  
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can own this droid, false otherwise
	 */
	
    @Override
    public boolean canDo(SWActor actor) {
    	assert(target instanceof SWRobotsInterface);
    	SWRobotsInterface droid = (SWRobotsInterface)target;
    	return !droid.hasOwner();
    }

	/**
	 * Perform the <code>Owned</code> action by setting the droids by the <code>SWActor</code> to the target (
	 * the <code>SWActor a</code>'s droids would be the target of this <code>Owned</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 

	 * @param 	a the <code>SWActor</code> that is taking the target

	 */
	@Override
	public void act(SWActor actor) {
		((SWRobotsInterface)target).setOwner(actor);
		actor.say(String.format("%s owns %s now.", actor.getShortDescription(), target.getShortDescription()));
	}
	
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author ram
	 * @return String comprising "Own " and the short description of the target of this <code>Own</code>
	 */
	@Override
	public String getDescription() {
		return "Own " + target.getShortDescription();
	}


}
