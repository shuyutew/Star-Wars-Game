package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Leave;
import starwars.actions.Repair;
import starwars.actions.Owned;
import starwars.actions.MindControl;
import starwars.entities.Canteen;

/**
 * This class represents "droids" in the Star Wars universe.  
 * 
 * Subclasses are intended to contain a static instance which represents the one
 * and only instance of the subclass.  
 * 
 * Subclasses should implement their own "getRobotClass" method that returns 
 * the single instance. There is no abstract method for this to avoid an 
 * unnecessary downcast.
 * 
 * To prevent SWRobots acting until intended, this abstract class implements
 * an API for activating them when getInstance is called.
 * 
 * Rather than implement act() like regular SWActors, Robots should implement
 * robotAct().  
 * 
 * @author shuyu
 *
 */
public abstract class SWRobots extends SWActor {

	/**If or not this <code>SWRobot</code> has owner. <code>SWRobot</code>s does not have a owner by default*/
	private boolean hasOwner = false;
	
	/**If or not this <code>SWRobot</code> will patrol while not Owned. <code>SWRobot</code>s will not patrol by default*/
	private boolean willPatrol = false;
	
	/**If or not this <code>SWRobot</code> will talk randomly. <code>SWRobot</code>s will not by default*/
	private boolean talk = false;
	
	/**If or not this <code>SWRobot</code> has an internal oil reservoir. <code>SWRobot</code>s will not have it by default*/
	private boolean internal = false;
	
	/**If or not this <code>SWRobot</code> has an internal oil reservoir. <code>SWRobot</code>s will not have it by default*/
	private boolean needsRepair = true;
	
	/**If or not this <code>SWRobot</code> can disassemble. <code>SWRobot</code>s will not have it by default*/
	private boolean disassemble = true;
	
	/** this is for droids, because there might be droids that has an internal oil resorvior*/
	private SWEntityInterface itemCarriedalong;
	
	private MessageRenderer m;
	
	/** 
	 * Protected constructor to prevent random other code from creating 
	 * SWDroid or their descendants.
	 * @param team
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	
	protected SWRobots(Team team, int hitpoints,  MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
		this.m = m;
		//this.addAffordance(new Repair(this, m));
		this.addAffordance(new Owned(this, m));
		this.removeAffordance(new MindControl(this,m));
		setForceAbility();
	}
	
	@Override
	public void setForceAbility(){
		this.forceAbilityLevel = 0;
	}

	
	/**
	 * Returns true for droid having an owner and returns false for patroling when owned
	 */
	public void isOwned() {
		this.hasOwner = true;
		this.willPatrol = false;
	}
	
	/**
	 * Returns false for droid having an owner when disowned
	 */
	public void disowned(){
		this.hasOwner = false;
	}
	
	/**
	 * This method gets the status of whether the droid has owner or not
	 * @return boolean of hasOwner
	 */
	public boolean getStatus(){
		return hasOwner;
	}
	
	/**
	 * This method takes in a boolean true or false to manipulate the droid to patrol or not
	 * @param isit takes in a boolean false or true
	 */
	public void setPatrol(boolean isit) {
		this.willPatrol = isit;
	}
	
	/**
	 * This method is to get whether the droid will patrol or not
	 * @return
	 */
	public boolean getPatrol(){
		return willPatrol;
	}
	
	/**
	 * This method determines that the droid to talk randomly
	 */
	public void RandomTalk(){
		this.talk = true;
	}
	
	/**
	 * This method determines that the droid will talk
	 * @return
	 */
	public boolean willTalk(){
		return talk;
	}
	
	/**
	 * This method shows the droid is immobile and disabled, returns the droid's hitpoints = 0
	 * @return isDead status where hitpoint = 0
	 */
	public boolean isImmobile(){
		return this.isDead();
	}
	
	/**
	 * if the droid has an internal oil reservoir, it can heal itself or other robots around. Thus, in this assignment
	 * we reuse the Canteen class as oil reservoir. We are assuming that the internal oil reservoir will eventually runs out
	 * but not that easy to run out? Thus, we put 10000 for it's initial level.
	 */
	public void internalOil(){
		this.internal = true;
		SWEntity fullOil = new Canteen(m, 10, 10000); 
		fullOil.removeAffordance(new Leave(fullOil, m));
		this.setItemCarried(fullOil);
	}
	
	/**
	 * Checks whether droid has an internal oil reservoir
	 * @return true or false for whether droid has an internal oil reservoir
	 */
	public boolean checkInternal(){
		return internal;
	}
	
	/** Determine that the droid will be disassembled
	 * 
	 * @return true if droid can be disassemble
	 */
	public boolean disassemble(){
		return disassemble;
	}
	
	public boolean repairs(){
		return needsRepair;
	}
	
	/**
	 * This act() method for the SWRobot is called to check the status of whether is has an owner or will patrol or not
	 */
	@Override
	public void act() {
		if (hasOwner || (willPatrol)) {
			
		}
		return;
	}

}
