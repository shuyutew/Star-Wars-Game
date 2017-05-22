package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWRobots;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.PatrolBehaviour;

public class R2D2 extends SWRobots{

	public R2D2(int hitpoints, Direction[] patrol, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);
		this.setShortDescription("R2-D2");
		this.setLongDescription("R2-D2, an Industrial Automaton astromech droid");
		this.setSymbol("R");
		
		behaviours.add(new PatrolBehaviour(this, world, patrol));
	}

}