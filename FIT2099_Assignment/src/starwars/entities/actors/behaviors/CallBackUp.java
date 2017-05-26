package starwars.entities.actors.behaviors;

import starwars.entities.actors.SWOrganicActor;
import starwars.SWWorld;
import starwars.actions.MoreStormTrooper;

public class CallBackUp extends BehaviourInterface{
	
	/**
	 * This is a call-for-back-up behaviour where an <code>SWOrganicActor</code> (for instance
	 * a Stormtrooper) may have 5% chance of calling for back up when it does not
	 * find anything at its location to attack.
	 * This will perform the MoreStormTrooper action class which will result
	 * in creating a new Stormtrooper at its location.
	 * 
	 * @param actor the <code>SWOrganicActor</code> that is being called
	 * @param world the <code>SWWorld</code> world to which this <code>SWOrganicActor</code> belongs to
	 */

	public CallBackUp(SWOrganicActor actor, SWWorld world) {
		super(actor, world);
	}
	
    @Override
    public boolean ExecuteBehaviour() {
    	
    	if(Math.random()>0.95){
        	actor.schedule(new MoreStormTrooper(messageRenderer, world));
        	
        	return true;
    	}
    	return false;
    }

}
