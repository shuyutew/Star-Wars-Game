package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntityInterface;
import starwars.SWActor;
import starwars.SWRobots;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.Capability;
import starwars.actions.Move;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.entities.actors.behaviors.Patrol;

/**
 * Droids
 * 
 *
 */
public class Droid extends SWRobots {

	private static Droid droid = null; // yes, it is OK to return the static instance!
	private Patrol path;
	private MessageRenderer m;
	private String name;
	private int i;
	public Droid(int i, String name, MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.NEUTRAL, 200, m, world);
		this.m = m;
		this.name = name;
		path = new Patrol(moves);
		this.setShortDescription("Droid " + name);
		this.setLongDescription("This is a droid named " + name);
	}

	public Droid(int i, String name, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, 200, m, world);
		this.m = m;
		this.name = name;
		this.setShortDescription("Droid " + name);
		this.setLongDescription("This is a droid named " + name);
		getPatrol();
	}
	

	@Override
	protected void robotAct() {

		if(isDead()) {
			return;
		}
		
		else if (getPatrol()) {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

}
