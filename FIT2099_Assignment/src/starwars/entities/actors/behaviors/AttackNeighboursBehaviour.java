package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.TRandom;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.Attack;

public class AttackNeighboursBehaviour extends BehaviourInterface {
	
	private boolean avoidFriendlies;
    private boolean avoidNonActors;
    private String message;
    private String messageMiss;
    private MessageRenderer messageRenderer;
    private double probability;
    
    /**
     * 
     * @param attacker the <code>SWActor</code> in which will 
     * 			perform the <code>Attack</code> action on another actor
     * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
     * @param m <code>MessageRenderer</code> to display messages.
     * @param avoidFriendlies boolean of whether it will attack actors of the same team or not
     * @param avoidNonActors boolean of whether it will ignore non-actors when encountered or not
     * @param message <code>MessageRenderer</code> to display messages.
     * @param messageMiss <code>MessageRenderer</code> to display message when <code>SWActor</code> misses hitting their target
     * @param proba a probabilty of how likely the attacker will miss their target.
     */
    
    public AttackNeighboursBehaviour(SWActor attacker, SWWorld world, MessageRenderer m, boolean avoidFriendlies, boolean avoidNonActors, String message, String messageMiss ,double proba) {

    	super(attacker, world);
    	this.avoidFriendlies = avoidFriendlies;
    	this.avoidNonActors = avoidNonActors;
    	this.message = message;
    	this.messageMiss = messageMiss;
    	this.probability = proba;
    	this.messageRenderer = m;
    }
    
    /**
     * If actor avoidsNonActors == true, it will proceed its moves without interacting with it
     * If actor avoidsFriendlies == true, it will not attack target of the same team
     * There will be a chance where certain actors may not have a perfect aim,
     * so a aiming probability parameter is added to the constructor above where 
     * when it executes this behaviour, whether the actor will hit their target
     * depends on this probability, the higher the probability,
     * the better the aim. When the attack misses their target,
     * a message will be displayed.
     */
    @Override
    public boolean ExecuteBehaviour() {

    	ArrayList<SWEntityInterface> targets = new ArrayList<SWEntityInterface>();

    	for (SWEntityInterface target : getLocalEntites()) {
    		if (target == actor){ // Why are you hitting yourself?
    			continue;
    		}
    			
    		if (target.isDead()){// Don't beat a dead raider.
    			continue;
    		}
    			
	    // I keep going back and forth on this. If you give it the attack affordance it makes sense
	    // but then the player gets asked to attack the canteen he's holding.
//	    if (target.getAffordance(Attack.class) == null) // Not an attackable thing.
//		continue;

    		if (avoidNonActors && !(target instanceof SWActor)){ // Not an actor.
    			continue;
    		}
		

    		if (avoidFriendlies && (target instanceof SWActor) && ((SWActor) target).getTeam() == actor.getTeam()){// No friendly fire.
    			continue;
    		}
		
    		targets.add(target);
    	}

    	if (targets.size() == 0)
    		return false;

    	if (Math.random()>probability){
    		SWEntityInterface target = TRandom.itemFrom(targets);
        	actor.say(String.format(message, actor.getShortDescription(), target.getShortDescription()));

    	// You only need an affordance on an item so the player has an option to attack it.
        	actor.schedule(new Attack(target, messageRenderer));
        	return true;
    	}
    	//When there is probability that the actor will miss and a string message is passed, this else statement will print the
    	//message indicating that the actor missed.
    	else{
    		if (messageMiss != null){
    			actor.say(String.format(messageMiss, actor.getShortDescription()));
    			return true;
    		}
    		return false;
    	}
    	
    }
}
