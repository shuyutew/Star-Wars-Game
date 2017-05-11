package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;

public class MindControl extends SWAffordance implements SWActionInterface{
	

	public MindControl(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}

	/**
	 * Returns the time is takes to perform this <code>Train</code> action.
	 * 
	 * @return The duration of the Attack action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 0;
	}
	
	/**
	 * A String describing what this <code>Attack</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "attack " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "Mind Control " + this.target.getShortDescription();
	}
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can attack the target.

	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true any <code>SWActor</code> can always try an attack, it just won't do much 
	 * 			good unless this <code>SWActor a</code> has a suitable weapon.
	 */
	@Override
	public boolean canDo(SWActor a) {
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		
		if (a.getForce() >= 8 && targetActor.getForce() <= 4){
			return true;
		}
		return false;
	}
	
	@Override
	public void act(SWActor a){
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		
		targetActor.beingMindControlled(true);
		a.controlling(true);
		a.setPoorOnes(targetActor);
		
	}
	
}
