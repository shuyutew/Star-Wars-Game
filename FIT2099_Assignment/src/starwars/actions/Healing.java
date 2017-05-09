package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;


public class Healing extends SWAffordance implements SWActionInterface {

	public Healing(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isMoveCommand() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/**
	 * This should always returns true -- anything that's got a visible Leave affordance
	 * should be being held by the actor that can see the affordance.
	 * 
	 * @author dsquire
	 * @param a the SWActor we are querying
	 * @return true
	 */
	public boolean canDo(SWActor a) {
		return a.getItemCarried().equals(target);
	}

	@Override
	public void act(SWActor a) {
		
		if (a.getItemCarried() != null) {//if the actor is carrying an item 
			assert(this.getTarget().hasCapability(Capability.DRINKABLE));
			
			if (a.getItemCarried().hasCapability(Capability.DRINKABLE)) {
				a.takeDamage(a.getItemCarried().getHitpoints() * -1); // blunt weapon won't do much, but it will still do some damage
				a.getItemCarried().takeDamage(1); // weapon gets blunt
				a.say(a.getShortDescription() + " is healing");
			}
			else{
				a.say(a.getItemCarried().getShortDescription() + " is empty. Need to fill it with water.");
			}
		}

	}
	
	@Override
	public String getDescription() {
		return this.target.getShortDescription() + " healed.";
	}


}
