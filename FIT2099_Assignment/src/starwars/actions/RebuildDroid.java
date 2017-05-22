package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.DroidParts;

public class RebuildDroid extends SWAffordance {

	public RebuildDroid(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}
	
	@Override
    public boolean canDo(SWActor actor) {
		return actor.hasCapability(Capability.DROID_MECHANIC) && actor.getItemCarried() instanceof DroidParts;
    }
	
    @Override
    public void act(SWActor actor) {
    	actor.setItemCarried(null);
    	getTarget().takeDamage((getTarget().getmaxHitpoints() / 2) * -1);
    	actor.say(String.format("%s rebuilds %s.", actor.getShortDescription(), target.getShortDescription()));
    }
    
    @Override
    public String getDescription() {
    	return String.format("Rebuild %s with droid parts", target.getShortDescription());
    }

}
