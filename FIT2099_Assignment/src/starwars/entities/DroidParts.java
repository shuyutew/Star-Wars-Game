package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.actions.Take;

public class DroidParts extends SWEntity {
	
	/**
	 * This entity can be used by mechanic droids to repair disabled droids
	 * When a Droid is disassembled, this will generate droid parts from it
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 */

	public DroidParts(MessageRenderer m) {
		super(m);
		this.shortDescription = "droid parts";
		this.longDescription = "the remains of a broken droid";
		this.setSymbol("p");
		this.addAffordance(new Take(this, m));
	}
	
    public boolean canBeUsedBy(SWActor user) {
    	return user.hasCapability(Capability.DROID_MECHANIC);
    }

}
