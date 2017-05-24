package starwars.entities.actors.behaviors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.BetrayTeam;

public class TortureLukeBehaviour extends BehaviourInterface {

	public TortureLukeBehaviour(SWActor actor, SWWorld world, MessageRenderer m) {
		super(actor, world);
	}

	  @Override
	    public boolean ExecuteBehaviour() {

	    	ArrayList<SWEntityInterface> targets = new ArrayList<SWEntityInterface>();

	    	for (SWEntityInterface target : getLocalEntites()) {
	    		if (target.getShortDescription() == "Luke"){
	    			System.out.println("HI THERE LUke TOrurtet");
	    			if(Math.random()>0.1){
	    				actor.schedule(new BetrayTeam(target, messageRenderer, Team.EVIL));
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
