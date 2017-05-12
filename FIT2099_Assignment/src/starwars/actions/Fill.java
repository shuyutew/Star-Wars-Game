package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Fillable;

public class Fill extends SWAffordance {
	
	/**
	 * Constructor for <code>Fill</code> class.
	 * 
	 * @param target of what needs to be filled
	 * @param m <code>MessageRenderer</code> to display messages
	 */
	public Fill(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns if or not a <code>SWActor a</code> can perform a <code>Fill</code> command.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not dead.
	 * <p>
	 * @param 	a the <code>SWActor</code> doing the action
	 * @return 	true if and only if <code>a</code> is not dead, false otherwise.
	 * @see 	{@link starwars.SWActor#isDead()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return false;
	}

	/**
	 * Perform the <code>Fill</code> action.
	 * 
	 * This method will only be called if the <code>SWActor a</code> is alive
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> who is moving
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		assert(this.getTarget().hasCapability(Capability.FILLABLE));
		Fillable fillableTarget = (Fillable) (this.getTarget());
		fillableTarget.fill();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "";
	}

}
