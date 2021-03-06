package starwars.actions;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWEntityInterface;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWAction;
import starwars.entities.actors.SWOrganicActor;
import starwars.entities.actors.Stormtrooper;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWWorld;

public class MoreStormTrooper extends SWAction{
	
	SWWorld world;
	
	/**
	 * This action creates a new Stormtrooper at the same position as the actor 
	 * as a form of radioing for backup when the caller does not encounter anything 
	 * that the actor is able to attack.
	 * 
	 * @param m m message renderer to display messages
	 * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
	 */

	public MoreStormTrooper(MessageRenderer m, SWWorld world) {
		super(m);
		this.world = world;
	}
	
	public void act(SWActor a) {
		SWLocation location = this.world.getEntityManager().whereIs(a);
    	
    	SWOrganicActor clone = new Stormtrooper(100, "StormBackUp", messageRenderer, world);
		
		EntityManager<SWEntityInterface, SWLocation> entityManager = getEntitymanager();
		entityManager.setLocation(clone, location);
	}
	
	/**
	 * Returns if or not a <code>SWActor a</code> can perform a <code>MoreStrormTrooper</code> command.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not dead.
	 * <p>

	 * @param 	a the <code>SWActor</code> doing the moving
	 * @return 	true if and only if <code>a</code> is not dead, false otherwise.
	 * @see 	{@link starwars.SWActor#isDead()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return !a.isDead() && a.getSymbol() == "S";
	}
	
	@Override
	public String toString() {
		return getDescription();
	}
	
	@Override
	public String getDescription() {
		return "called for backup " ;
	}

}
