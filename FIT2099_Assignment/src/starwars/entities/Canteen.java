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
 */
public class Canteen extends SWEntity implements Fillable {
	
	/**
	 * Constructor for the <code>Canteen</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Canteen</code></li>
	 * 	<li>Set the short description of this <code>Canteen</code> to "a canteen"</li>
	 * 	<li>Set the long description of this <code>Canteen</code> to "a slightly battered aluminium canteen"</li>
	 * 	<li>Set the hit points of this <code>Canteen</code> to initial level</li>
	 * 	<li>Add a capacity for the <code>Canteen</code> to determine its max level it can be filled</li>
	 * 	<li>Add a <code>Take</code> affordance to this <code>Canteen</code> so it can be taken</li> 
	 *	<li>Add a <code>FILLABLE Capability</code> to this <code>Canteen</code> so it can be used to <code>Fill</code></li>
	 *	<li>Add a <code>DRINKABLE Capability</code> to this <code>Canteen</code> so it can be used to <code>Heal</code></li>
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Take}
	 * @see {@link starwars.Capability}
	 */

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

	/**
	 * This method fills the <code>Canteen<code> object when the level does not reach its capacity
	 */
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
	
	/**
	 * When <code>Canteen</code> object is used, the level of the object decrement by 1
	 * When level reaches 0, the objects's capability Drinkable is removed
	 */
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
	
	/**
	 * Shows the level of the <code>Canteen</code> object
	 */
	public int getLevel(){
		return level;
	}
}
