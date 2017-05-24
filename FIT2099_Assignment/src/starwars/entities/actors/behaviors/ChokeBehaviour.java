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
	
	public ChokeBehaviour(SWActor attacker, SWWorld world, MessageRenderer m, boolean avoidNonActors, String message) {
		super(attacker, world);
		this.message = message;
    	this.messageRenderer = m;
	}

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
