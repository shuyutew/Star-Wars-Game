package starwars;

import edu.monash.fit2099.simulator.matter.EntityInterface;

/**
 * All <code>Entities</code> and <code>Actors</code> in the starwars client package should implement this interface.
 * 
 * It allows them to be managed by the <code>EntityManager</code>.
 * 
 * @author ram
 * @see	edu.monash.fit2099.simulator.matter.EntityInterface
 * @see edu.monash.fit2099.simulator.matter.EntityManager
 */
public interface SWEntityInterface extends EntityInterface {

	/**
	 * Returns a string symbol that represents this <code>SWEntity</code> or <code>SWActor</code>.
	 * <p>
	 * The Views use this method to obtain the symbols that are used to query for resources(images) and for display.
	 *  
	 * @return a String representing the <code>SWEntity</code> or <code>SWActor</code>.
	 * 
	 * @see 	starwars.swinterfaces.SWGridTextInterface
	 */
	public abstract String getSymbol();
	
	/**
	 * Sets the symbol of this <code>SWEntity</code> or <code>SWActor</code> with a new string <code>string</code>.
	 * <p>
	 * Although not a must the new symbol is preferably, 
	 * <ul>
	 * 	<li>single character</li>
	 * 	<li>unique for each <code>SWEntity</code> or <code>SWActor</code></li>
	 * </ul>
	 * <p>
	 * The Views use this method to obtain the symbols that are used to query for resources(images) and for display.
	 * 
	 * @param string a string to represent this <code>SWEntity</code> or <code>SWActor</code>
	 */
	public abstract void setSymbol(String string);
	
	/**
	 * Returns true if this <code>SWEntity</code> or <code>SWActor</code> has the given 
	 * capability <code>c</code>, false otherwise.
	 * 
	 * @param 	c the <code>Capability</code> to search for
	 * @return	true if this <code>Capability c</code> is manifested, false otherwise
	 * @see 	starwars.Capability
	 */
	public boolean hasCapability(Capability c);
	
    public void addCapability(Capability c);
    
    public void removeCapability(Capability c);
	
	/**
	 * Returns the hitpoints of this <code>SWEntity</code> or <code>SWActor</code>.
	 * 
	 * @return the amount of hitpoints
	 */
	public int getHitpoints();
	
	/**
	 * Returns the maximum hitpoints of this <code>SWEntity</code> or <code>SWActor</code>.
	 * 
	 * @return the amount of maxHitpoints
	 */	
	public int getmaxHitpoints();
	
    /**
     * Returns true if this <code>SWActor</code> is dead, false otherwise.
     * <p>
     * A <code>SWActor</code> is dead when it's <code>hitpoints</code> are less than or equal to zero (0)
     *
     * @author ram
     * @return true if and only if this <code>SWActor</code> is dead, false otherwise
     * @see #hitpoints
     */
	
	public boolean isDead();
	
	/**
	 * Returns the level of this <code>SWEntity</code> or <code>SWActor</code>.(currently only canteen uses this)
	 * 
	 * @return the level
	 */	
	
	public int getLevel();
	
	/**
	 * Returns the force ability level of this <code>SWActor</code>.
	 * 
	 * @return 	the force ability level of this <code>SWActor</code> 
	 * @see 	#forceAbilityLevel
	 */
	
	public int getForce();
	
	/**
	 * Method that reduces the <code>hitpoints</code> to insist damage on of this 
	 * <code>SWEntity</code> or <code>SWActor</code>.
	 * 
	 * @param damage the amount of <code>hitpoints</code> to be reduced
	 * @pre <code>damage</code> should be greater than or equal to zero to avoid any increase in the number of <code>hitpoints</code>
	 */
	public void takeDamage(int damage);
	
    public void heal(int hitPoints);
	
	public void movedToLocation(SWLocation loc);
	
    public SWAffordance getAffordance(Class<?> type);
    
    public void removeAffordance(Class<?> type);
	

}