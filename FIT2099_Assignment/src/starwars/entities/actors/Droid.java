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
import starwars.entities.actors.behaviors.Patrol;

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
	private Patrol path;
	private int maxparts;
	private int parts;

	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world, Direction[] patrolmoves) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.name = name;
		this.m = m;
		path = new Patrol(patrolmoves);
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
		
		/**
		 * If hitpoint is 0, droid is immobile/disabled, droid parts = 0 so it needs droid parts to be repaired
		 */
		if (this.isImmobile()){
			parts = 0;
		}

		if (this.getStatus()){
			System.out.println("MYGOOOODDDDD");
		}
		
		else{
			say(describeLocation());
			
			/**
			 * While not owned, droids which have the property of patrolling will patrol.
			 */
					if (this.getPatrol() && !(this.getStatus())) {		
						
						Direction newdirection = path.getNext();
						say(getShortDescription() + " is heading " + newdirection + " next.");
						Move myMove = new Move(newdirection, messageRenderer, world);

						scheduler.schedule(myMove, this, 1);
					}
					
			/**
			 * this allows droids to say something at a 10% chance rate given that the droids had willTalk property
			 */
					if(this.willTalk()){
						if(Math.random() > 0.1){
							ArrayList<String> possibleQuotes = new ArrayList<String>();
							possibleQuotes.add("Don�t call me a mindless philosopher, you overweight glob of grease!");
							possibleQuotes.add("This sandStorm is killing me.");
							possibleQuotes.add("I've forgotten how much I hate space travel.");
							possibleQuotes.add("This is suicide.");
							possibleQuotes.add("Something's not right because now I can't see.");
							
							String saying = possibleQuotes.get((int) (Math.floor(Math.random() * possibleQuotes.size())));
							say(this.getShortDescription() + " says: ' " + saying + " ' ");
						}
					}
					
		}
		
		/**
		 * If droid has an internal oil reservoir, when droid sees another droid 
		 * in his location that needs to fill up hitpoints, it will apply oil on the droid
		 */
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
						
						System.out.println("MOTHEHERREE");
						Healing heal = new Healing(targetActor, m);
						scheduler.schedule(heal, targetActor, 0);
					}
				}
			}
		}
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
		return this.getShortDescription() + " [" + this.getHitpoints() +", " + this.getParts()  + "] is at " + location.getShortDescription();

	}

}
