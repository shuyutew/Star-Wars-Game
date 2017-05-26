package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.PatrolBehaviour;
import starwars.entities.actors.behaviors.RepairBehaviour;

public class R2D2 extends SWRobots{
	
	/**
	 * R2-D2 is a very important Droid, it holds the Death Star plans
	 * which is crucial to wining the game
	 * When it is not owned, it will wander around in a fixed list of moves.
	 * R2-D2 is able to repair other disabled droids
	 * 
	 * @param hitpoints the hit points of this <code>R2D2</code> to get started with
	 * @param patrol a list of moves this <code>R2D2</code> will wander around the map
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>R2D2</code> belongs to
	 * 
	 */

	public R2D2(int hitpoints, Direction[] patrol, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, m, world);
		this.setShortDescription("R2-D2");
		this.setLongDescription("R2-D2, an Industrial Automaton astromech droid");
		this.setSymbol("R");
		
		this.addCapability(Capability.DROID_MECHANIC);
		this.addCapability(Capability.DROID_REPAIR);
		
		behaviours.add(new RepairBehaviour(this, world));
		behaviours.add(followBehaviour);
		behaviours.add(new PatrolBehaviour(this, world, patrol));
	}

}
