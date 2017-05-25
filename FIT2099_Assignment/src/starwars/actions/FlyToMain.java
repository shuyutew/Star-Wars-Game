package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class FlyToMain extends SWAffordance implements SWActionInterface {

	public FlyToMain(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}
	
	/**
	 * Returns the time is takes to perform this <code>FlyTo DeathStar</code> action.
	 * 
	 * @return The duration of the FlyTo DeathStar action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}
	
	@Override
	public String getDescription() {
		return this.target.getShortDescription() + " fly to Main";
	}
	
	@Override
	public boolean canDo(SWActor a) {
		return true;
	}

	@Override
	public void act(SWActor a) {
		SWWorld world = a.getWorld();
		world.SetMyGrid("Main");
		
	}

}
