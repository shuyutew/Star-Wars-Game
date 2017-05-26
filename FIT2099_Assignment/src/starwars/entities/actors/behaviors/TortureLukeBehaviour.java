package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import starwars.SWWorld;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.BetrayTeam;

public class TortureLukeBehaviour extends BehaviourInterface {
	
	/**
	 * This behaviour is held by an <code>SWActor</code> that enables him to 
	 * have a 50% chance of turning the <code>Player</code> to the dark side
	 * 
	 * @param actor the <code>SWActor</code> that is being called
	 * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
	 * @param m <code>MessageRenderer</code> to display messages.
	 */

	public TortureLukeBehaviour(SWActor actor, SWWorld world, MessageRenderer m) {
		super(actor, world);
	}

	/**
	 * If target of this <code>SWActor</code> is Luke, the actor will have a 50% chance
	 * of turning Luke to the dark side by initiating the <code>BetrayTeam</code> action
	 * If failed to turn Luke to the dark side, he will attack Luke instead.
	 */
	  @Override
	    public boolean ExecuteBehaviour() {

	    	ArrayList<SWEntityInterface> targets = new ArrayList<SWEntityInterface>();

	    	for (SWEntityInterface target : getLocalEntites()) {
	    		if (target.getShortDescription() == "Luke"){
	    			if(Math.random()>0.5){
	    				actor.schedule(new BetrayTeam(target, messageRenderer, Team.EVIL));
	    				SWWorld w = actor.getWorld();
	    				w.setNotRun();
	    				w.setEndGame("Game Over! Vader successfully turned Luke to the dark side!");
	    				return true;
	    			}
	    			else{
	    				actor.schedule(new Attack(target, messageRenderer));
		    			return true;
	    			}
	    		}
	    	}
	    
	    	return false;
	  }
	    			
	  
}
