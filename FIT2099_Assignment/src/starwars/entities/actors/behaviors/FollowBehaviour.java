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
    
    /**
     * This behaviour is held by <code>SWActor</code>s that is prone to be able to be owned
     * by another actor (<code>Player</code>) and will move in the same direction of his/her
     * owner.
     * 
     * @param actor the <code>SWActor</code> that is being called
	 * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
     * @param owner the owner or another actor that this <code>SWActor</code> will follow
     */
	
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
    
    /**
     * If the actor is R2-D2, when it is owned, it will no longer patrol
     * 
     * Checks if location of actor and location of owner are the same
     */
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
