package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Fill;

/**
 * A canteen that can be used to contain water.
 * 
 * It can be filled at a Reservoir, or any other Entity
 * that has a Dip affordance.
 * 
 * Please note that drinking from the canteen is currently 
 * unimplemented
 * 
 * 
 * @author Robert Merkel
 * 
 */
public class Canteen extends SWEntity implements Fillable {

	private int capacity;
	private int level;
	//private int hitpoints;
	
	public Canteen(MessageRenderer m, int capacity, int initialLevel)  {
		super(m);
		this.shortDescription = "a canteen";
		this.longDescription = "a slightly battered aluminium canteen";
		
		this.capacity = capacity;
		this.level= initialLevel;
		//this.hitpoints = level;
		capabilities.add(Capability.FILLABLE);
		if (capacity == initialLevel){
			capabilities.add(Capability.DRINKABLE);
		}
		this.addAffordance(new Fill(this, m));
	}

	public void fill() {
		level = capacity;
		//hitpoints = level;
		capabilities.add(Capability.DRINKABLE);
	}
	
	//the canteen level reduce by 1 bar. For every bar, 5 hitpoints is restored.

	public void use(){
		/**
		super.takeDamage(1);
		this.level = hitpoints;
		
		if (this.hitpoints<=0) {
			this.shortDescription = "an empty canteen";
			this.longDescription  = "a slightly battered aluminium canteen";
			
			this.capabilities.remove(Capability.DRINKABLE);
		}
	*/
	}


	
	@Override 
	public String getShortDescription() {
		return shortDescription + " [" + level + "/" + capacity + "]";
	}
	
	@Override
	public String getLongDescription () {
		return longDescription + " [" + level + "/" + capacity + "]";
	}
}
