package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;


public class Healing extends SWAffordance implements SWActionInterface {
	
	/**
	 * Constructor for <code>Move</code> class. Will initialize the direction and the world for the <code>Move</code>.
	 * 
	 * @param target of what needs to be filled
	 * @param m <code>MessageRenderer</code> to display messages
	 */
	public Healing(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}

	/**
	 *Returns the time taken to perform this <code>Move</code> action.
	 *
	 *@return the duration of the <code>Move</code> action. Currently hard coded to return 1
	 */
	@Override
	public int getDuration() {
		return 0;
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public boolean isMoveCommand() {
		return false;
	}

	@Override
	/**
	 * This should always returns true -- anything that's got a visible Healing affordance
	 * should be being held by the actor that can see the affordance.
	 * 
	 * @param a the SWActor we are querying
	 * @return true
	 */
	public boolean canDo(SWActor a) {
		return a.getItemCarried().equals(target);
	}

	/**
	 * If SWActor is carrying an item, the item carry has capability Drinkable, 
	 * SWActor will use it to heal themselves
	 */
	@Override
	public void act(SWActor a) {
		
		if (a.getItemCarried() != null) {//if the actor is carrying an item 
			
			if (a.getItemCarried().hasCapability(Capability.DRINKABLE)) {
				int current = a.getHitpoints();
				int difference = a.getmaxHitpoints() - current;
				if (difference >= a.getItemCarried().getHitpoints()){
					a.heal(a.getItemCarried().getHitpoints());
				}
				else{
					a.heal(difference);
				}
				a.getItemCarried().takeDamage(1);
				a.say(a.getShortDescription() + " is healing");
			}
			else{
				a.say(a.getItemCarried().getShortDescription() + " is empty. Need to refill it if possible.");
			}
		}
	}
	
	@Override
	public String getDescription() {
		return this.target.getShortDescription() + " healed.";
	}


}
