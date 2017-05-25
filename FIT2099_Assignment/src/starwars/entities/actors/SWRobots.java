package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.DissambleDroid;
import starwars.actions.Owned;
import starwars.actions.RebuildDroid;
import starwars.actions.Repair;
import starwars.entities.Canteen;
import starwars.entities.actors.behaviors.FollowBehaviour;

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
public class SWRobots extends SWActor implements SWRobotsInterface {
	
	/**If or not this <code>SWRobot</code> has an internal oil reservoir. <code>SWRobot</code>s will not have it by default*/
	private boolean internal = false;
	
	/** this is for droids, because there might be droids that has an internal oil resorvior*/
	
	protected FollowBehaviour followBehaviour;
	
	/** 
	 * Protected constructor to prevent random other code from creating 
	 * SWDroid or their descendants.
	 * @param team
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	
	public SWRobots(Team team, int hitpoints,  MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
		//this.addAffordance(new Repair(this, m));
		this.addAffordance(new Owned(this, messageRenderer));
		this.addAffordance(new Repair(this, messageRenderer));
		
		this.followBehaviour = new FollowBehaviour(this, world, null);
	}
	
//Droids has no force ability	
	@Override
	public void setForceAbility(){
		this.forceAbilityLevel = 0;
	}
	
    @Override
    public boolean hasOwner() {
    	return followBehaviour.hasOwner();
    }

    @Override
    public void setOwner(SWActor newOwner) {
    	followBehaviour.setOwner(newOwner);
    }
    
    @Override
    public void takeDamage(int hitPoints) {
    	super.takeDamage(hitPoints);
    	if (isDead()) {
    		addAffordance(new DissambleDroid(this, messageRenderer));
    		addAffordance(new RebuildDroid(this, messageRenderer));
    	}
    }
    
    @Override
    public void heal(int hitPoints){
    	boolean wasDead = isDead();
    	super.heal(hitPoints);
    	if(wasDead && !isDead()){
    		removeAffordance(DissambleDroid.class);
    		removeAffordance(RebuildDroid.class);
    	}
    }
	
	/**
	 * if the droid has an internal oil reservoir, it can heal itself or other robots around. Thus, in this assignment
	 * we reuse the Canteen class as oil reservoir. We are assuming that the internal oil reservoir will eventually runs out
	 * but not that easy to run out? Thus, we put 10000 for it's initial level.
	 */
	public void internalOil(){
		this.internal = true;
		SWEntity fullOil = new Canteen(messageRenderer, 10, 10000); 
		this.setItemCarried(fullOil);
	}
	
	public boolean checkInternal(){
		return internal;
	}
	
    @Override
    public void movedToLocation(SWLocation loc) {
    	if(loc.getSymbol() == 'b'){ //This is the only defining characterist of the badlands.
    		takeDamage(5);
    	}
    }

}
