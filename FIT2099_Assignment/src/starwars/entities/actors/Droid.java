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

public class Droid extends SWRobots{
	
	private String name;
	private MessageRenderer m;

	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, 200, m, world);
		this.name = name;
		this.m = m;
	}
	
	@Override
	public void act() {
		say(describeLocation());
		
/**
 * While not owned, droids which ahve the property of patrolling will patrol.
 */
		if (this.getPatrol() && !(this.getStatus())) {
			Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
	                CompassBearing.EAST,
	                CompassBearing.EAST, CompassBearing.EAST,
	                CompassBearing.WEST,
	                CompassBearing.WEST, CompassBearing.WEST,
	                CompassBearing.WEST, CompassBearing.WEST};
			
			Patrol path = new Patrol(patrolmoves);
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
				possibleQuotes.add("Don’t call me a mindless philosopher, you overweight glob of grease!");
				possibleQuotes.add("This sandStorm is killing me.");
				possibleQuotes.add("I've forgotten how much I hate space travel.");
				possibleQuotes.add("This is suicide.");
				possibleQuotes.add("Something's not right because now I can't see.");
				
				String saying = possibleQuotes.get((int) (Math.floor(Math.random() * possibleQuotes.size())));
				say(this.getShortDescription() + " says: ' " + saying + " ' ");
			}
		}
		
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
						scheduler.schedule(heal, targetActor, 1);
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
		return this.getShortDescription() + " [" + this.getHitpoints() +", " + this.getForce()  + "] is at " + location.getShortDescription();

	}

}
