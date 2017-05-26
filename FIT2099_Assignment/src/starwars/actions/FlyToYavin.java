package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class FlyToYavin extends SWAffordance implements SWActionInterface {
	
	/**
	 * An action where the Millenium Falcon is able to fly the target and his companions
	 * to this desired location, specifically the Yavin IV map
	 * 
	 * @param theTarget <code>SWActor</code> that will be targeted for this action
	 * @param m <code>MessageRenderer</code> to display messages.
	 */

	public FlyToYavin(SWEntityInterface theTarget, MessageRenderer m) {
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
		return this.target.getShortDescription() + " fly to Yavin IV";
	}
	
	@Override
	public boolean canDo(SWActor a) {
		return true;
	}

	/**
	 * Set the world of where to transport the target and his companions to Yavin IV
	 * then lands them on the new desired map at coordinates (0,0)
	 */
	@Override
	public void act(SWActor a) {
		SWLocation loc;
		SWWorld world = a.getWorld();
		
		EntityManager<SWEntityInterface, SWLocation> entityManager = world.getEntitymanager();
		world.SetMyGrid("Yavin IV");
		
		SWGrid newW = world.getGrid();
		loc = newW.getLocationByCoordinates(0, 0);
		
		entityManager.setLocation(a, loc);
	}

}
