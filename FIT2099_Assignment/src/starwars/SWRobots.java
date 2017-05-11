package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

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
 * @author Robert Merkel
 *
 */
public abstract class SWRobots extends SWActor {

	private boolean hasOwner;
	private boolean willPatrol;
	
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
		hasOwner = false;
		willPatrol = true;
	}

	
	protected boolean isOwned() {
		return hasOwner;
	}
	
	protected boolean getPatrol() {
		return willPatrol;
	}
	
	protected void owned() {
		hasOwner = true;
		willPatrol = false;
	}
	
	@Override
	public void act() {
		if (isOwned() || (willPatrol = true)) {
			this.robotAct();
		}
		return;
	}

	protected abstract void robotAct();
}
