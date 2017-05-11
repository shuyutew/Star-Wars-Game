package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.Capability;

public class Repair extends SWAffordance{

	public Repair(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}
	
	@Override
	public boolean canDo(SWActor a) {
		if (a.getItemCarried().hasCapability(Capability.SPAREPARTs) && this.getTarget().getHitpoints() == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author shuyu
	 * @return String comprising "repair " and the short description of the target of this <code>Take</code>
	 */
	@Override
	public String getDescription() {
		return "take " + target.getShortDescription();
	}

}
