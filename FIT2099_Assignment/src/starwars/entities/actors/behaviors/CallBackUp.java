package starwars.entities.actors.behaviors;

import starwars.entities.actors.SWOrganicActor;
import starwars.SWWorld;
import starwars.actions.MoreStormTrooper;

public class CallBackUp extends BehaviourInterface{

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
