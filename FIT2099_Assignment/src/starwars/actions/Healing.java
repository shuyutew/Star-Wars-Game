package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.Fillable;


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
		System.out.println("whatever");
		
		if (a.getItemCarried() != null) {//if the actor is carrying an item 
			System.out.println("helpppppp");
			assert(this.getTarget().hasCapability(Capability.FILLABLE));
			
			if (a.getItemCarried().hasCapability(Capability.FILLABLE)) {
				a.takeDamage(-20); // blunt weapon won't do much, but it will still do some damage
				a.getItemCarried().takeDamage(1); // weapon gets blunt
			}
			
			
		//assert(item instance of Fillable);
		}
		
		a.setLongDescription(a.getShortDescription() + " is healing");


		//a.say(item.getShortDescription() + "has been refilled to capacity");
	}
	
	@Override
	public String getDescription() {
		return this.target.getShortDescription() + " healed.";
	}


}
