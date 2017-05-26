package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.MindControl;
import starwars.actions.ForceChoke;

public class SWOrganicActor extends SWActor {
	
	/**
	 * It does make sense that every actor could be mind controlled if 
	 * their force ability level is less than a certain level, thus in the MindControl class, 
	 * under the canDo() method, we would set some conditions so that actors with a certain
	 * force ability would not be able to be mind controlled.
	 * 
	 * All actor should be able to be Force Choked (currently only performed by Darth Vader)
	 * 
	 * @param team the <code>Team</code> to which the this <code>Player</code> belongs to
	 * @param hitpoints the number of hit points of this <code>SWOrganicActor</code>.
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>SWOrganicActor</code> belongs to
	 */

	public SWOrganicActor(Team team, int hitpoints, MessageRenderer m, SWWorld world)  {
		super(team, hitpoints, m, world);
		
		this.addAffordance(new MindControl(this, m));
		
		this.addAffordance(new ForceChoke(this, m));
	}

}
