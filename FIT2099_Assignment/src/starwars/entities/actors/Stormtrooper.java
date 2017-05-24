package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.Blaster;
import starwars.entities.actors.behaviors.AttackNeighboursBehaviour;
import starwars.entities.actors.behaviors.WanderAround;

public class Stormtrooper extends SWActor {

	private String name;

	/**
	 * Create a Stormtrooper. Stormtroopers will randomly wander
	 * around the playfield (on any given turn, there is a 50% probability
	 * that they will move) and attack anything they can (if they can attack
	 * something, they will).  They are all members of team EVIL, 
	 * so their attempts to attack other Stormtroopers 
	 * or Darth Vader won't be effectual.
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Stormtrooper. If this
	 *            decreases to below zero, the Stormtrooper will die.
	 * @param name
	 *            this Stormtrooper's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Stormtrooper</code> belongs to
	 * 
	 */
	public Stormtrooper(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.EVIL, hitpoints, m, world);
		// TODO Auto-generated constructor stub
		this.name = name;
		Blaster troopweapon = new Blaster(m);
		setItemCarried(troopweapon);
		//behaviours.add(new StormtrooperAttackBehaviour(this, world, m, true, true, "%s has attacked %2s"));
		behaviours.add(new AttackNeighboursBehaviour(this, world, m, true, true, "%s has attacked %2s"));
		behaviours.add(new WanderAround(this, world));
	}

    @Override
    protected void executeBehaviours() {
	//say(describeLocation()); // Too verbose, good for debugging though.
    	super.executeBehaviours();
    }
	
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());
		executeBehaviours();
	}
	
	@Override
	public String getShortDescription() {
		return name + " the Stormtrooper";
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

