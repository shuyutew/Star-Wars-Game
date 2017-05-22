/**
 * Class that represents an Actor (i.e. something that can perform actions) in the starwars world.
 * 
 * @author ram
 * 
 * @modified 20130414 dsquire
 * 	- changed constructor so that affordances that all SWActors must have can be added
 * 	- changed team to be an enum rather than a string
 */
/*
 * Change log
 * 2017-01-20: Added missing Javadocs and improved comments (asel)
 * 2017-02-08: Removed the removeEventsMethod as it's no longer required.
 * 			   Removed the tick and act methods for SWActor as they are never called
 */
package starwars;

import java.util.ArrayList;
import java.util.HashSet;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Actor;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Attack;
import starwars.actions.MindControl;
import starwars.actions.Disowned;
import starwars.actions.Owned;
import starwars.actions.Train;
import starwars.entities.actors.behaviors.BehaviourInterface;
import starwars.actions.Move;

public abstract class SWActor extends Actor<SWActionInterface> implements SWEntityInterface {
	
	/**the <code>Team</code> to which this <code>SWActor</code> belongs to**/
	private Team team;
	
	/**The amount of <code>hitpoints</code> of this actor. If the hitpoints are zero or less this <code>Actor</code> is dead*/
	private int hitpoints;
	
	private int maxHitpoint;
	
	public int forceAbilityLevel;
	
	private int level = 0; //
	
	/**The world this <code>SWActor</code> belongs to.*/
	protected SWWorld world;
	
	/**Scheduler to schedule this <code>SWActor</code>'s events*/
	protected static Scheduler scheduler;
	
	/**The item carried by this <code>SWActor</code>. <code>itemCarried</code> is null if this <code>SWActor</code> is not carrying an item*/
	private SWEntityInterface itemCarried;
	
	/**The droid owned by this <code>SWActor</code>. <code>droidOwned</code> is null if this <code>SWActor</code> is not owning a droid*/
	private SWEntityInterface droidOwned;
	
	protected ArrayList<BehaviourInterface> behaviours = new ArrayList<BehaviourInterface>();
	
	/**If or not this <code>SWActor</code> is human controlled. <code>SWActor</code>s are not human controlled by default*/
	protected boolean humanControlled = false;
	
	protected boolean mindControlled = false;
	
	protected boolean Controlling = false;
	
	private SWActor poorOne;
	
	/**A string symbol that represents this <code>SWActor</code>, suitable for display*/
	private String symbol;
	
	/**A set of <code>Capabilities</code> of this <code>SWActor</code>*/
	private HashSet<Capability> capabilities;
	
	/**
	 * Constructor for the <code>SWActor</code>.
	 * <p>
	 * The constructor initializes the <code>actions</code> list of this <code>SWActor</code>.
	 * <p>
	 * By default,
	 * <ul>
	 * 	<li>All <code>SWActor</code>s can be attacked.</li>
	 * 	<li>Have their symbol set to '@'</li>
	 * </ul>
	 * 
	 * @param 	team to which this <code>SWActor</code> belongs to
	 * @param 	hitpoints initial hitpoints of this <code>SWActor</code> to start with
	 * @param 	m	message renderer for this <code>SWActor</code> to display messages
	 * @param 	world the <code>World</code> to which <code>SWActor</code> belongs to
	 * 
	 * @see 	#team
	 * @see 	#hitpoints
	 * @see 	#world
	 * @see 	starwars.actions.Attack
	 */
	public SWActor(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		super(m);
		actions = new HashSet<SWActionInterface>();
		this.team = team;
		this.hitpoints = hitpoints;
		this.maxHitpoint = hitpoints;
		this.world = world;
		this.symbol = "@";
		setForceAbility();
		
		//SWActors are given the Attack affordance hence they can be attacked
		SWAffordance attack = new Attack(this, m);
		this.addAffordance(attack);
		
		//SWActors are given the Train affordance hence they can be trained. But for SWRobots we need to remove this affordance!
		this.addAffordance(new Train(this, m));
		
//It does make sense that every actor could be mind controlled if their force ability level is less than a certain level
//Thus, in the MindControl class, under the canDo() method, we would set some conditions so that actors with a certain
//force ability would not be able to be mind controlled.
		this.addAffordance(new MindControl(this, m));
		
	}
	
	/**
	 * Sets the <code>scheduler</code> of this <code>SWActor</code> to a new <code>Scheduler s</code>
	 * 
	 * @param	s the new <code>Scheduler</code> of this <code>SWActor</code> 
	 * @see 	#scheduler
	 */
	public static void setScheduler(Scheduler s) {
		scheduler = s;
	}
	
	public void setForceAbility(){
		if (maxHitpoint>0 && maxHitpoint<= 50){
			this.forceAbilityLevel = 1;
		}
		
		else if(maxHitpoint>50 && maxHitpoint<= 70){
			this.forceAbilityLevel = 2;
		}
		
		else if(maxHitpoint>70 && maxHitpoint<= 100){
			this.forceAbilityLevel = 3;
		}
		
		else if(maxHitpoint>100 && maxHitpoint<= 150){
			this.forceAbilityLevel = 4;
		}
		
		else if(maxHitpoint>150 && maxHitpoint<= 190){
			this.forceAbilityLevel = 5;
		}
		
		else if(maxHitpoint>190 && maxHitpoint<= 210){
			this.forceAbilityLevel = 6;
		}
		
		else if(maxHitpoint>210 && maxHitpoint<= 300){
			this.forceAbilityLevel = 7;
		}
		
		else if(maxHitpoint>300 && maxHitpoint<= 350){
			this.forceAbilityLevel = 8;
		}
		
		else if(maxHitpoint>350 && maxHitpoint<= 400){
			this.forceAbilityLevel = 9;
		}
		
		else if(maxHitpoint>400){
			this.forceAbilityLevel = 10;
		}
	}
	
	/**
	 * Returns the team to which this <code>SWActor</code> belongs to.
	 * <p>
	 * Useful in comparing the teams different <code>SWActor</code> belong to.
	 * 
	 * @return 	the team of this <code>SWActor</code>
	 * @see 	#team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Returns the hit points of this <code>SWActor</code>.
	 * 
	 * @return 	the hit points of this <code>SWActor</code> 
	 * @see 	#hitpoints
	 * @see 	#isDead()
	 */
	@Override
	public int getHitpoints() {
		return hitpoints;
	}
	
	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public int getmaxHitpoints() {
		return maxHitpoint;
	}
	
	/**
	 * Returns the force ability level of this <code>SWActor</code>.
	 * 
	 * @return 	the force ability level of this <code>SWActor</code> 
	 * @see 	#forceAbilityLevel
	 */
	
	public int getForce() {
		return forceAbilityLevel;
	}
	
	public boolean isWeakMinded(){
		if (this.forceAbilityLevel <= 4 && this.forceAbilityLevel>0){
			return true;
		}
		return false;
	}

	/**
	 * Returns an ArrayList containing this Actor's available Actions, including the Affordances of items
	 * that the Actor is holding.
	 * 
	 * @author ram
	 */
	public ArrayList<SWActionInterface> getActions() {
		ArrayList<SWActionInterface> actionList = super.getActions();
		
		//If the HobbitActor is carrying anything, look for its affordances and add them to the list
		SWEntityInterface item = getItemCarried();
		if (item != null)
			for (Affordance aff : item.getAffordances())
				if (aff instanceof SWAffordance)
				actionList.add((SWAffordance)aff);
		return actionList;
	}
	
 	/**
 	 * Returns the item carried by this <code>SWActor</code>. 
 	 * <p>
 	 * This method only returns the reference of the item carried 
 	 * and does not remove the item held from this <code>SWActor</code>.
 	 * <p>
 	 * If this <code>SWActor</code> is not carrying an item this method will return null.
 	 * 
 	 * @return 	the item carried by this <code>SWActor</code> or null if no item is held by this <code>SWActor</code>
 	 * @see 	#itemCarried
 	 */
 	public SWEntityInterface getItemCarried() {
 		return itemCarried;
 	}
 	
 	/**
 	 * Returns the droid owned by this <code>SWActor</code>. 
 	 * <p>
 	 * This method only returns the reference of the droid owned
 	 * and does not remove the ownership held from this <code>SWActor</code>.
 	 * <p>
 	 * If this <code>SWActor</code> does not own a droid this method will return null.
 	 * 
 	 * @return 	the droid owned by this <code>SWActor</code> or null if no droid owned by this <code>SWActor</code>
 	 * @see 	#droidOwned
 	 */
 	public SWEntityInterface getDroidOwned() {
 		return droidOwned;
 	}
 
 	/**
 	 * Sets the team of this <code>SWActor</code> to a new team <code>team</code>.
 	 * <p>
 	 * Useful when the <code>SWActor</code>'s team needs to change dynamically during the simulation.
 	 * For example, a bite from an evil actor makes a good actor bad.
 	 *
 	 * @param 	team the new team of this <code>SWActor</code>
 	 * @see 	#team
 	 */
 	public void setTeam(Team team) {
 		this.team = team;
 	}
	
 	public void setMaxHit(int maxxie){
 		this.maxHitpoint = maxxie;
 	}
 
 	/**
 	 * Method insists damage on this <code>SWActor</code> by reducing a 
 	 * certain amount of <code>damage</code> from this <code>SWActor</code>'s <code>hitpoints</code>
 	 * 
 	 * @param 	damage the amount of <code>hitpoints</code> to be reduced
 	 * @pre 	<code>damage</code> should not be negative
 	 */
 	@Override
 	public void takeDamage(int damage) {
 		//Precondition 1: Ensure the damage is not negative. Negative damage could increase the SWActor's hitpoints
 		//assert (damage >= 0)	:"damage on SWActor must not be negative";
 		//We don't need these preconditions because we could use them during healing to increase the SWActor's hitpoints.
 		this.hitpoints -= damage;
 	}
 
 	/**
 	 * Assigns this <code>SWActor</code>'s <code>itemCarried</code> to 
 	 * a new item <code>target</code>
 	 * <p>
 	 * This method will replace items already held by the <code>SWActor</code> with the <code>target</code>.
 	 * A null <code>target</code> would signify that this <code>SWActor</code> is not carrying an item anymore.
 	 * 
 	 * @param 	target the new item to be set as item carried
 	 * @see 	#itemCarried
 	 */
 	public void setItemCarried(SWEntityInterface target) {
 		this.itemCarried = target;
 	}
 	
 	/**
 	 * Assigns this <code>SWActor</code>'s <code>droidOwned</code> to 
 	 * a new droid <code>target</code>
 	 * <p>
 	 * This method will replace droid already owned by the <code>SWActor</code> with the <code>target</code>.
 	 * A null <code>target</code> would signify that this <code>SWActor</code> is not owning a droid anymore.
 	 * 
	
	/**
	 * Assigns this <code>SWActor</code>'s <code>droidOwned</code> to 
	 * a new droid <code>target</code>
	 * <p>
	 * This method will replace droid already owned by the <code>SWActor</code> with the <code>target</code>.
	 * A null <code>target</code> would signify that this <code>SWActor</code> is not owning a droid anymore.
	 * 
	 * @param 	target the new droid to be set as droid owned
	 * @see 	#droidOwned
	 */
	public void setDroidOwned(SWEntityInterface target, Affordance a) {
 		assert target instanceof SWRobots;
 		
 		boolean targetIsActor = target instanceof SWRobots;
 		SWRobots targetActor = null;
 		
 		if (targetIsActor) {
 			targetActor = (SWRobots) target;
 		}
 		
 		targetActor.removeAffordance(a);
 		targetActor.isOwned();
 		targetActor.addAffordance(new Disowned(targetActor, messageRenderer));
 		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
 		entityManager.remove(target);
 		entityManager.setLocation(targetActor, entityManager.whereIs(this));
 		
 		this.droidOwned = targetActor;
 
 		/**
 		SWAction.getEntitymanager().remove(target);
 		
 		Droid newD = new Droid(i, name, messageRenderer, world);
 		newD.setSymbol("RD");
 		newD.internalOil();
 		newD.isOwned();
 		newD.removeAffordance(a);
 		newD.addAffordance(new Disowned(newD, messageRenderer));
 
 		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
 		entityManager.setLocation(newD, entityManager.whereIs(this));
 		
 		this.droidOwned = newD;
 		*/
	}
	
 	public void disownDroid(){
 		droidOwned.addAffordance(new Owned(droidOwned, messageRenderer));
 		SWRobots tar = (SWRobots)droidOwned;
 		tar.disowned();
 		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
 		entityManager.setLocation(tar, entityManager.whereIs(this));
 		
 		this.droidOwned = null;
 	}
	
	
	/**
	 * Returns true if this <code>SWActor</code> is dead, false otherwise.
	 * <p>
	 * A <code>SWActor</code> is dead when it's <code>hitpoints</code> are less than or equal to zero (0)
	 *
	 * @author 	ram
	 * @return 	true if and only if this <code>SWActor</code> is dead, false otherwise
	 * @see 	#hitpoints
	 */
	public boolean isDead() {
		return hitpoints <= 0;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}
	

	@Override
	public void setSymbol(String s) {
		symbol = s;
	}
	
	/**
	 * Returns if or not this <code>SWActor</code> is human controlled.
	 * <p>
	 * Human controlled <code>SWActors</code>' <code>SWActions</code> are selected by the user as commands from the Views.
	 * 
	 * @return 	true if the <code>SWActor</code> is controlled by a human, false otherwise
	 * @see 	#humanControlled
	 */
	public boolean isHumanControlled() {
		return humanControlled;
	}
	
	public void beingMindControlled(boolean isit){
		mindControlled = isit;
	}
	
	public void controlling(boolean doit){
		Controlling = doit;
	}
	
	/** 
	 * remembers the Actor that is being mind controlled so that 
	 * @param a
	 */
	
	public void setPoorOnes(SWActor a){
		poorOne = a;
	}
	
	public SWActor getPoorOnes(){
		return poorOne;
	}
	

	@Override
	public boolean hasCapability(Capability c) {
		return capabilities.contains(c);
	}
	
	/**
	 * This method will poll this <code>SWActor</code>'s current <code>Location loc</code>
	 * to find potential exits, and replaces all the instances of <code>Move</code>
	 * in this <code>SWActor</code>'s command set with <code>Moves</code> to the new exits.
	 * <p>
	 * This method doesn't affect other non-movement actions in this <code>SWActor</code>'s command set.
	 *  
	 * @author 	ram
	 * @param 	loc this <code>SWActor</code>'s location
	 * @pre		<code>loc</code> is the actual location of this <code>SWActor</code>
	 */
	public void resetMoveCommands(Location loc) {
		HashSet<SWActionInterface> newActions = new HashSet<SWActionInterface>();
		
		// Copy all the existing non-movement options to newActions
		for (SWActionInterface a: actions) {
			if (!a.isMoveCommand())
				newActions.add(a);
		}
		
		// add new movement possibilities
		for (CompassBearing d: CompassBearing.values()) { 														  
			if (loc.getNeighbour(d) != null) //if there is an exit from the current location in direction d, add that as a Move command
				newActions.add(new Move(d,messageRenderer, world)); 
		}
		
		// replace command list of this SWActor
		this.actions = newActions;		
		
		// TODO: This assumes that the only actions are the Move actions. This will clobber any others. Needs to be fixed.
		/* Actually, that's not the case: all non-movement actions are transferred to newActions before the movements are transferred. --ram */
	}
	
 
 	/**
 	 * Only actors can be owners thus this method is not in the interface.
 	 * @return boolean returns true if actor has already own a droid, false otherwise.  
 	 */
 	public boolean getisOwner(){
 		if (droidOwned != null){
 			return true;
 		}
 		return false;
 	}
	
	
	public void setNotOwner(){
		droidOwned = null;
	}
	
    public void schedule(ActionInterface action) {
    	scheduler.schedule(action, this, action.getDuration());
    }

    @Override
    public void act() {
    	if (this.getSymbol()=="??"){
    		System.out.println("YES?????");
    	}
    	
    	if (isDead())
    		return;
    	
    	executeBehaviours();
    }

    protected void executeBehaviours() {
    	if (this.getSymbol()=="??"){
    		System.out.println("YES?????2");
    	}
    	for (BehaviourInterface behaviour : behaviours) {
    		if (behaviour.ExecuteBehaviour())
    			return;
	}
    }
    
    public BehaviourInterface getBehaviour(Class<? extends BehaviourInterface> type){
    	for (BehaviourInterface b : behaviours) {
    		if (b.getClass() == type){
    			return (BehaviourInterface)b;
    		}
    	}
    	return null;
    }
    
    public void removeBehaviour(Class<? extends BehaviourInterface> type) {
    	behaviours.remove(getBehaviour(type));
    }
    
    @Override
    public void movedToLocation(SWLocation loc) { }

}
