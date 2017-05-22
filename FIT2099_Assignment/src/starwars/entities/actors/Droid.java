package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWRobots;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Healing;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.PatrolBehaviour;

/**
 * 
 * @author minxi
 *
 */
public class Droid extends SWRobots{
	/**
	 * Constructor for the <code>Droid</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Droid</code></li>
	 * 	<li>Initialize the world for this <code>Droid</code></li>
	 *  <li>Initialize the <code>Team</code> for this <code>Droid</code></li>
	 * 	<li>Initialize the hit points for this <code>Droid</code></li>
	 * 	<li>Initialize the current droid parts and max droid parts for this <code>Droid</code></li>
	 * </ul>
	 * 
	 * @param team the <code>Team</code> to which the this <code>Droid</code> belongs to
	 * @param hitpoints the hit points of this <code>Droid</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>Droid</code> belongs to
	 */
	
	private String name;
	private MessageRenderer m;
	private int maxparts;
	private int parts;

	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world, Direction[] patrolmoves) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.name = name;
		this.m = m;
		maxparts = 2;
		parts = 2;
	}
	
	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.name = name;
		this.m = m;
		maxparts = 2;
		parts = 2;
	}
	
	public int getParts(){
		return parts;
	}
	
	public int getmaxParts(){
		return maxparts;
	}
	
	/**
	 * This method will describe this <code>Droid</code>'s scene and prompt for user input through the controller 
	 * to schedule the command.
	 * <p>
	 * This method will only be called if this <code>Droid</code> is alive and is not waiting.
	 * 
	 * @see {@link #describeScene()}
	 * @see {@link starwars.swinterfaces.SWGridController}
	 */
	@Override
	public void act() {
		
		//this is where when the droid is being owned. The droid does not do anything. It follows the owner.
		//all those happens in the SWActor class in the setDroidOwner() method

		
		if (this.checkInternal()){
			say(describeLocation());
					
			if(this.checkInternal()){

				SWLocation location = this.world.getEntityManager().whereIs(this);
						
				List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
				for (SWEntityInterface entity : contents) {
					if (entity instanceof SWRobots){
						if (entity.getHitpoints() < entity.getmaxHitpoints()){
									
							boolean targetIsActor = entity instanceof SWActor;
							SWActor targetActor = null;
									
							if (targetIsActor) {
								targetActor = (SWActor) entity;
							}
									
							Healing heal = new Healing(targetActor, m);
							scheduler.schedule(heal, targetActor, 0);
						}
					}
				}
			}
					
		}
		
		/**
		 * If droid has an internal oil reservoir, when droid sees another droid 
		 * in his location that needs to fill up hitpoints, it will apply oil on the droid
		 */

	}
	
	@Override
	public String getShortDescription() {
		return name + " the Droid";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() +", " + this.getForce()  + "]"+ "[ "+ this.getParts() + " ]" + " is at " + location.getShortDescription();

	}

}
