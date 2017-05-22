package starwars;

import java.util.HashSet;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.Entity;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * Class that represents inanimate objects in the Star Wars world. Objects that cannot move for example trees.
 * 
 * @author 	ram
 * @see 	edu.monash.fit2099.simulator.matter.Entity
 * @see 	SWEntityInterface
 */

public class SWEntity extends Entity implements SWEntityInterface {
	
	/**A string symbol that represents this <code>SWEntity</code>, suitable for display*/
	private String symbol;
	
	/**A set of <code>Capabilities</code> of this <code>SWEntity</code>*/
	protected HashSet<Capability> capabilities;
	
	/**The amount of <code>hitpoints</code> of this <code>SWEntity</code>.*/
	protected int hitpoints = 0; // Not all non-actor entities will make use of this
	
	protected int maxHitpoints = 0; // This is equals to whatever the hitpoint initially was.
	
	protected int level = 0; // Not all non actor entities will make use of this.(currently only canteen uses this)
	
	protected int forceAbilityLevel = 0; //Non actor entites will have force of ) which means no force
	
	protected boolean owned = false;

	/**
	 * Constructor for this <code>SWEntity</code>. Will initialize this <code>SWEntity</code>'s
	 * <code>messageRenderer</code> and set of capabilities.
	 * 
	 * @param m the <code>messageRenderer</code> to display messages
	 */
	protected SWEntity(MessageRenderer m) {
		super(m);
		capabilities = new HashSet<Capability>();
	}


	/**
	 * Returns a String symbol representing this <code>SWEntity</code>.
	 * 
	 * @return 	symbol a String that represents this <code>SWEntity</code>
	 * @see 	#symbol
	 */
	@Override
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Sets the symbol of this <code>SWEntity</code> with a new string <code>s</code>.
	 * 
	 * @param 	s the new string symbol for this <code>SWEntity</code>
	 * @see 	#symbol 
	 */
	@Override
	public void setSymbol(String s) {
		symbol = s;
	}

	@Override
	public boolean hasCapability(Capability c) {
		return capabilities.contains(c);
	}
	

    @Override
    public void addCapability(Capability c) {
    	capabilities.add(c);
    }

    @Override
    public void removeCapability(Capability c) {
    	capabilities.remove(c);
    }

	@Override
	public int getHitpoints() {
		return hitpoints;
	}
	
	@Override
	public int getmaxHitpoints() {
		return maxHitpoints;
	}
	
	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public int getForce() {
		return forceAbilityLevel;
	}
	
	/**
	 * Sets the <code>hitpoints</code> of this <code>SWEntity</code>
	 * to a new number of hit points <code>p</code>.
	 * 
	 * @param p the new number of <code>hitpoints</code>
	 */
	public void setHitpoints(int p) {
		hitpoints = p;
	}
	
	@Override
	public void takeDamage(int damage) {
		//Precondition 1: Ensure that the damage is not negative
		//assert (damage >= 0)	:"damage on SWEntity must not be negative";
		//These preconditions are not needed because we could use negative numbers to increase hitpoints when healing.
		this.hitpoints -= damage;
	}
	
	@Override
	public void movedToLocation(SWLocation loc) { }
	 
	@Override
	public SWAffordance getAffordance(Class<?> type) {
		for (Affordance a : getAffordances()) {
			if (a.getClass() == type){
	 			return (SWAffordance) a;
			}
		 }
		 	return null;
	 }
	    
	 @Override
	 public void removeAffordance(Class<?> type) {
		 removeAffordance(getAffordance(type));
	 }
	 
	@Override
	public boolean isDead() {
		return hitpoints <= 0;
}
	 
	 
	
	
}
