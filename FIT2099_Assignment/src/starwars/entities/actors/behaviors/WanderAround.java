package starwars.entities.actors.behaviors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.TRandom;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Move;

public class WanderAround extends BehaviourInterface {

	public WanderAround(SWActor actor, SWWorld world) {
		super(actor, world);
	}
    @Override
    public boolean ExecuteBehaviour() {

    	ArrayList<Direction> possibledirections = new ArrayList<Direction>();

	// build a list of available directions
    	for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
    		if (SWWorld.getEntitymanager().seesExit(actor, d)) {
    			possibledirections.add(d);
    		}
    	}

    	Direction heading = TRandom.itemFrom(possibledirections);
    	actor.say(actor.getShortDescription() + " is heading " + heading + " next.");
    	Move myMove = new Move(heading, world.getMessageRenderer(), world);
    	actor.schedule(myMove);

    	return true;
    }
}
