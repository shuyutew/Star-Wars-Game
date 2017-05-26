package starwars.entities.actors.behaviors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.TRandom;
import starwars.actions.ForceChoke;

public class ChokeBehaviour extends BehaviourInterface{

	private String message;
	private boolean avoidNonActors;
    private MessageRenderer messageRenderer;
    
    /**
     * This behaviour is held by actors that have a high force ability level so that he
     * is able to perform Force Choke action on another actor
     * The behaviour shows that it has a 50% of being able to perform this Force Choke action
     * 
     * @param attacker the <code>SWActor</code> in which will 
     * 			perform the <code>Attack</code> action on another actor
     * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
     * @param m <code>MessageRenderer</code> to display messages.
     * @param avoidNonActors boolean of whether it will ignore non-actors when encountered or not
     * @param message <code>MessageRenderer</code> to display messages
     */
	
	public ChokeBehaviour(SWActor attacker, SWWorld world, MessageRenderer m, boolean avoidNonActors, String message) {
		super(attacker, world);
		this.message = message;
    	this.messageRenderer = m;
	}

	/**
	 * Ensures attacker won't target himself and will only execute behaviour when 
	 * target is not a nonactor, or when target is dead
	 * If he found a target in his location, he will have a 50% chance of
	 * performing the Force Choke action this target
	 * 
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
		
    		targets.add(target);
    	}

    	if (targets.size() == 0)
    		return false;

    	SWEntityInterface target = TRandom.itemFrom(targets);
    	actor.say(String.format(message, actor.getShortDescription(), target.getShortDescription()));

//if the target is still alive and there is 50% chance that Darth Vader will force choke the target
    	if (target.getHitpoints()>0 && Math.random()>0.5){
    		actor.schedule(new ForceChoke(target, messageRenderer));
    		return true;
    	}
    	
    	return false;
    }
}
