package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Leave;
import starwars.actions.Repair;
import starwars.entities.Canteen;

/**
 * This class represents "droids" in the Star Wars universe.  
 * They use a variation of the Singleton
 * pattern to ensure that only ONE of each legend can exist.
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
		this.addAffordance(new Repair(this, m));
	}
	
	public void isOwned() {
		this.hasOwner = true;
	}
	
	public void disowned(){
		this.hasOwner = false;
	}
	
	public boolean getStatus(){
		return hasOwner;
	}
	
	public void YesPatrol() {
		this.willPatrol = true;
	}
	
	public boolean getPatrol(){
		return willPatrol;
	}
	
	public void RandomTalk(){
		this.talk = true;
	}
	
	public boolean willTalk(){
		return talk;
	}
	
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
	
	public boolean checkInternal(){
		return internal;
	}
	
	@Override
	public void act() {
		if (hasOwner || (willPatrol)) {
			
		}
		return;
	}

}
