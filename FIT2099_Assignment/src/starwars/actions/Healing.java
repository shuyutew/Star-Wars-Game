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
	public boolean canDo(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		if (item!= null) {
			return item.hasCapability(Capability.FILLABLE);
		}
		return false;
	}

	@Override
	public void act(SWActor a) {
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
		
		SWEntityInterface item = a.getItemCarried();
		
		if (item != null) {//if the actor is carrying an item 
			assert(this.getTarget().hasCapability(Capability.FILLABLE));
			
			if (item.hasCapability(Capability.DRINKABLE)) {
				target.takeDamage(-20); // blunt weapon won't do much, but it will still do some damage
				item.takeDamage(1); // weapon gets blunt
			}
			
			
		assert(item instanceof Fillable);
		}


		a.say(item.getShortDescription() + "has been refilled to capacity");
	}
	
	@Override
	public String getDescription() {
		return this.target.getShortDescription() + " healed.";
	}


}
