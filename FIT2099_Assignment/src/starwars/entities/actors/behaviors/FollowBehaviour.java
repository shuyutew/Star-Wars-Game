package starwars.entities.actors.behaviors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;

public class FollowBehaviour extends BehaviourInterface {

    private SWActor owner;
	
	public FollowBehaviour(SWActor actor, SWWorld world, SWActor owner) {
		super(actor, world);
		this.owner = owner;
	}
	
    public boolean hasOwner() {
    	return owner != null;
    }
    
    public void setOwner(SWActor newOwner) {
    	owner = newOwner;
    	owner.setDroidOwned(actor);
    }
    
    @Override
    public boolean ExecuteBehaviour() {
    	if (owner == null){
    		return false;
    	}
    	
    	if (actor.getShortDescription() == "R2-D2"){
    		actor.removeBehaviour(PatrolBehaviour.class);
    	}

	//TODO go the full A* 
    	SWLocation a = entityManager.whereIs(actor);
    	SWLocation b = entityManager.whereIs(owner);
    	
    	if (a == b){
    		return true;
    	}
    	
    	if(actor.getShortDescription() == "Leia"){
    		owner.setPrincess(actor);
    	}
    	else{
    		owner.setDroidOwned(actor);
    	}
    	//entityManager.setLocation(actor, entityManager.whereIs(owner));
    	return false;
    }

}
