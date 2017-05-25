package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;

public class FlyToDeath extends SWAffordance implements SWActionInterface {

	public FlyToDeath(SWEntityInterface theTarget, MessageRenderer m) {
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
		return this.target.getShortDescription() + " fly to DeathStar";
	}
	
	@Override
	public boolean canDo(SWActor a) {
		return true;
	}

	@Override
	public void act(SWActor a) {
		SWLocation loc;
		SWWorld world = a.getWorld();
		
		EntityManager<SWEntityInterface, SWLocation> entityManager = world.getEntitymanager();
		world.SetMyGrid("DeathStar");
		
		SWGrid newW = world.getGrid();
		loc = newW.getLocationByCoordinates(0, 0);
		
		entityManager.setLocation(a, loc);
		
	}

}
