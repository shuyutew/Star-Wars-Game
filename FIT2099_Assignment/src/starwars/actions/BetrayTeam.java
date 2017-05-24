package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Team;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class BetrayTeam extends SWAffordance implements SWActionInterface{
	
	private Team team;

	public BetrayTeam(SWEntityInterface theTarget, MessageRenderer m, Team team) {
		super(theTarget, m);
		this.team = team;
	}
	
	/**
	 * A String describing what this <code>BetrayTeam</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "betrayed " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "turned " + this.target.getShortDescription() + " to the dark side.";
	}
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can force the target to betrayTeam.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true any <code>SWActor</code> can always try an BetrayTeam.
	 */
	@Override
	public boolean canDo(SWActor a) {
		if(a.getForce()>9){
			return true;
		}
		return false;
	}
	
	@Override
	public void act(SWActor a) {
		boolean turned = false;
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		
		if (targetIsActor && targetActor.getForce()>8){
			if (Math.random()>0.25){
				targetActor.setTeam(this.team);
				turned = true;
			}
		}
		else{
			targetActor.setTeam(this.team);
			turned = true;
		}
		
		if (!turned){
			a.say("Ah shit! Failed to turn " + targetActor.getShortDescription() + " to the dark side.");
		}
		
	}
	
	

}
