
package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.Take;

/**
 * @author minxi
 * A droid part can be used to repair droids
 * 
 * When a droid is owned, it can pick up droid parts to repair disabled droids,
 * thus is has a capability called Spareparts.
 * It has a Take affordance.
 */
public class DroidParts extends SWEntity {
	
	public DroidParts(MessageRenderer m) {
		super(m);
		this.setLongDescription("a droid part.");
		this.setShortDescription("a droid part which can be used for assembling other droids.");
		this.setSymbol("P");
		
		this.addAffordance(new Take(this, m));//add the Take affordance so that the droid parts can be picked up
		//the DroidPart has capabilities 
		this.capabilities.add(Capability.SPAREPARTs);   // and SPAREPARTs so that it can be used to repair
}
	
	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
}