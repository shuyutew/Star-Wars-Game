package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.Blaster;
import starwars.entities.actors.behaviors.AttackNeighboursBehaviour;
import starwars.entities.actors.behaviors.WanderAround;

public class TuskenRaider extends SWOrganicActor {

	private String name;

	/**
	 * Create a Tusken Raider.  Tusken Raiders will randomly wander
	 * around the playfield (on any given turn, there is a 50% probability
	 * that they will move) and attack anything they can (if they can attack
	 * something, they will).  They 
	 * are all members of team TUSKEN, so their attempts to attack
	 * other Tusken Raiders won't be effectual.
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Tusken Raider. If this
	 *            decreases to below zero, the Raider will die.
	 * @param name
	 *            this raider's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>TuskenRaider</code> belongs to
	 * 
	 */
	public TuskenRaider(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.TUSKEN, hitpoints, m, world);
		// TODO Auto-generated constructor stub
		this.name = name;
		Blaster tuckenweapon = new Blaster(m);
		setItemCarried(tuckenweapon);
		behaviours.add(new AttackNeighboursBehaviour(this, world, m, false, false, "%s has attacked %2s", null, 0.0));
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
		return name + " the Tusken Raider";
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
