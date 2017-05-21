package starwars.entities.actors.behaviors;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;

public abstract class BehaviourInterface {
	
    protected SWActor actor;
    protected SWWorld world;
    protected MessageRenderer messageRenderer;
    protected EntityManager<SWEntityInterface, SWLocation> entityManager;

    public BehaviourInterface(SWActor actor, SWWorld world) {
    	this.actor = actor;
    	this.world = world;
    	if (world != null) {
    		entityManager = world.getEntityManager();
    		messageRenderer = world.getMessageRenderer();
    	}
    }
	
    public abstract boolean ExecuteBehaviour();

}
