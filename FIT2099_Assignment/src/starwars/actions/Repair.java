package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWRobots;
import starwars.entities.actors.Droid;
import starwars.Capability;
import starwars.SWAction;

public class Repair extends SWAffordance{

	public Repair(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	@Override
	public boolean canDo(SWActor a) {
		if (a.getItemCarried().hasCapability(Capability.SPAREPARTs) && this.getTarget().getHitpoints() == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Perform the <code>Repair</code> action by placing droid parts to the disabled droids
	 * (the <code>SWActor a</code>'s item carried would be the target of this <code>Repair</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	ram
	 * @author 	Asel (26/01/2017)
	 * @param 	a the <code>SWActor</code> that is repairing the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	
	@Override
	public void act(SWActor a) {
			if (a.getItemCarried() != null) {//if the actor is carrying an item 
			
			if (a.getItemCarried().hasCapability(Capability.SPAREPARTs)) {
				
				if (((Droid) a).getParts() < ((Droid) a).getmaxParts()){
					;
				}
				}
		}
	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author shuyu
	 * @return String comprising "repair " and the short description of the target of this <code>Repair</code>
	 */
	@Override
	public String getDescription() {
		return "repairs " + target.getShortDescription();
	}

	
}
