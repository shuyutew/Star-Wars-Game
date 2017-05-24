package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.TRandom;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Attack;
import starwars.entities.actors.Stormtrooper;

public class AttackNeighboursBehaviour extends BehaviourInterface {
	
	private boolean avoidFriendlies;
    private boolean avoidNonActors;
    private String message;
    private MessageRenderer messageRenderer;
    
    public AttackNeighboursBehaviour(SWActor attacker, SWWorld world, MessageRenderer m, boolean avoidFriendlies, boolean avoidNonActors, String message) {

    	super(attacker, world);
    	this.avoidFriendlies = avoidFriendlies;
    	this.avoidNonActors = avoidNonActors;
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
		

    		if (avoidFriendlies && (target instanceof SWActor) && ((SWActor) target).getTeam() == actor.getTeam()){// No friendly fire.
    			continue;
    		}
    		
    		if (actor instanceof Stormtrooper && Math.random() > 0.25){
    			actor.say("Stormtrooper shoots wildly!");
    			return true;
    		}
    		targets.add(target);
    	}

    	if (targets.size() == 0){
    		if (actor instanceof Stormtrooper && Math.random() > 0.95){
			// call for backup / create new ST at the same location as the Stormtrooper
			Stormtrooper clone = new Stormtrooper(100, "Clonetrooper", messageRenderer, actor.getWorld());
			clone.setSymbol("S");
			// SWLocation loc = (actor.getWorld().getEntityManager().whereIs(actor));
			System.out.println(actor.getLocation());
    		entityManager.setLocation(clone, actor.getLocation());
    		}
    		return false;
    	}

    	SWEntityInterface target = TRandom.itemFrom(targets);
    	actor.say(String.format(message, actor.getShortDescription(), target.getShortDescription()));

	// You only need an affordance on an item so the player has an option to attack it.
    	actor.schedule(new Attack(target, messageRenderer));
    	return true;
    }
}
