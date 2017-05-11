package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;


public class Train extends SWAffordance implements SWActionInterface {

	public Train(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}
	
	/**
	 * Returns the time is takes to perform this <code>Attack</code> action.
	 * 
	 * @return The duration of the Attack action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	
	/**
	 * A String describing what this <code>Attack</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "attack " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "Accept training from " + this.target.getShortDescription();
	}
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can attack the target.
	 * 
	 * @author 	dsquire
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
		
	// Only acotrs on the same team can train someone with lower force ability.
		if (a.getForce()<targetActor.getForce() && a.getTeam() == targetActor.getTeam() && a.getForce()<10){
			return true;
		}
		a.say("It seems like " + a.getShortDescription() + " has nothing new to learn anymore from " + target.getShortDescription());
		return false;
	}
	
	@Override
	public void act(SWActor a) {
		a.setMaxHit(a.getmaxHitpoints() + 100);
		a.setForceAbility();
	}

}
