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
	
	public Canteen(MessageRenderer m, int capacity, int initialLevel)  {
		super(m);
		this.shortDescription = "a canteen";
		this.longDescription = "a slightly battered aluminium canteen";
		this.hitpoints = initialLevel;
		
		this.capacity = capacity;
		this.level= initialLevel;
		capabilities.add(Capability.FILLABLE);
		if (this.level > 0){
			capabilities.add(Capability.DRINKABLE);
		}
		
		this.addAffordance(new Fill(this, m));
	}

	public void fill() {
	
		level = capacity;
		capabilities.add(Capability.DRINKABLE);
	}

	
	@Override 
	public String getShortDescription() {
		return shortDescription + " [" + level + "/" + capacity + "]";
	}
	
	@Override
	public String getLongDescription () {
		return longDescription + " [" + level + "/" + capacity + "]";
	}
	
	@Override
	public void takeDamage(int damage) {
		this.level -= 1;
		
		if (this.level<=0) {
			System.out.println("finished");
			this.shortDescription = "an empty canteen";
			this.longDescription  = "an empty slightly battered aluminium canteen";
			
			this.capabilities.remove(Capability.DRINKABLE);
		}
		
	}
	
	public int getLevel(){
		return level;
	}
}
