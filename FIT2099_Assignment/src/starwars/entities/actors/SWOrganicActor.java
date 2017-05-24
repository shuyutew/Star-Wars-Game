package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.MindControl;
import starwars.actions.ForceChoke;

public class SWOrganicActor extends SWActor {

	public SWOrganicActor(Team team, int hitpoints, MessageRenderer m, SWWorld world)  {
		super(team, hitpoints, m, world);
		
		//It does make sense that every actor could be mind controlled if their force ability level is less than a certain level
		//Thus, in the MindControl class, under the canDo() method, we would set some conditions so that actors with a certain
		//force ability would not be able to be mind controlled.		
		this.addAffordance(new MindControl(this, m));
		
		this.addAffordance(new ForceChoke(this, m));
	}

}
