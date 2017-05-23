package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class ForceChoke extends SWAffordance implements SWActionInterface {

	public ForceChoke(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
	}
	
	/**
	 * A String describing what this <code>Attack</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "attack " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "ForceChoke " + this.target.getShortDescription();
	}
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can forceChoke the target.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true any <code>SWActor</code> can always try an forceChoke.
	 */
	@Override
	public boolean canDo(SWActor a) {
		if(a.getForce()>8){
			return true;
		}
		return false;
	}
	
	@Override
	public void act(SWActor a) {
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
					
				
		a.say(a.getShortDescription() + " is ForceChoking " + target.getShortDescription() + "!");
		target.takeDamage(50); 

		//After the attack

		if (this.getTarget().getHitpoints() <= 0) {  // can't use isDead(), as we don't know that the target is an actor
			target.setLongDescription(target.getLongDescription() + ", that was killed in a fight");
							
			//remove the attack affordance of the dead actor so it can no longer be attacked
			targetActor.removeAffordance(this);

				
		}
	} // not game player and different teams

}
