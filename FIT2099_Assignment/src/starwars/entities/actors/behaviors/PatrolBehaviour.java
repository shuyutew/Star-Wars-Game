package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import edu.monash.fit2099.simulator.space.Direction;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Move;

public class PatrolBehaviour extends BehaviourInterface {

	private ArrayList<Direction> moves;
	private int position = 0;
	
	/**
	 * This behaviour makes the actor patrol around the map based on a list of
	 * directions.
	 * 
	 * @param actor the <code>SWActor</code> that is being called
	 * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
	 * @param moves a list of directions that the actor will move according to
	 */
	
	public PatrolBehaviour(SWActor actor, SWWorld world, Direction [] moves) {
		super(actor, world);
		this.moves = new ArrayList<Direction>(Arrays.asList(moves));

	}
	
	/**
	 * Initiates the <code>Move</code> action and gets its next direction
	 * either North, West, East, South, Northwest, Northeast, Southeast or Southwest
	 * and displays it's new direction
	 */
    @Override
    public boolean ExecuteBehaviour() {

    	Direction newdirection = getNext();
    	actor.say(actor.getShortDescription() + " moves " + newdirection);
    	Move move = new Move(newdirection, messageRenderer, world);

    	actor.schedule(move);
    	return true;
    }
	
    /**
     * This method find the next position of where the actor 
     * will head toward
     * @return next position of actor
     */
	public Direction getNext() {
		Direction nextMove = moves.get(position);
		position = ++position % moves.size();
		return nextMove;
	}
}
