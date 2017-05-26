package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Team;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class BetrayTeam extends SWAffordance implements SWActionInterface{
	
	private Team team;
	
	/**
	 * This action is done by an actor with a high force ability level in which
	 * the actor will have a 25% chance of turning his target to betray the
	 * target's own team and join the actor's team instead
	 * However if the target has a high force ability level as well,
	 * the target will have a 75% of resisting this actor's attempt to
	 * make the target betray his own team.
	 * 
	 * @param theTarget the target about to be turned to the attacker's team
	 * @param m message renderer to display messages
	 * @param team the team in which the actor will turn its target to
	 */

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
		if(a.getForce()>=9){
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

/**If the actor is trained in the force resulting in higher force level,
 * the actor will have a 75% chance to resist the attempt to change team.
 * Which means there is 25% chance that the actor will betray it's own team
 * 
 * If the actor is not trained by Force, then the actor will 100% cannot resist this action.*/
		if (targetIsActor && targetActor.getForce()>8){
			if (Math.random()>0.75){
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
		else{
			a.say(a.getShortDescription()+"successfully turned " + this.target.getShortDescription() + " to the dark side!!");
		}
		
	}
	
	

}
