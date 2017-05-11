package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Repair;

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
	
	/**If or not this <code>SWRobot</code> will patrol while not Owned. <code>SWRobot</code>s will not patrol by default*/
	private boolean talk = false;
	
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
	
	@Override
	public void act() {
		if (hasOwner || (willPatrol)) {
			
		}
		return;
	}

}
