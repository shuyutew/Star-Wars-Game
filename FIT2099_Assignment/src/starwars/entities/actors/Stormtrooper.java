package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.entities.Blaster;
import starwars.entities.actors.behaviors.AttackNeighboursBehaviour;
import starwars.entities.actors.behaviors.WanderAround;
import starwars.entities.actors.behaviors.CallBackUp;

public class Stormtrooper extends SWOrganicActor {

	private String name;

	/**
	 * Create a Stormtrooper. Stormtroopers has a <code> Blaster</code> and will randomly wander
	 * around the playfield (on any given turn, they will move randomly) 
	 * and attack anything they can (if they can attack
	 * something, they will). They are all members of team EVIL, 
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
		this.setSymbol("S");
		
		Blaster troopweapon = new Blaster(m);
		troopweapon.removeAffordance(Take.class);
		troopweapon.addAffordance(new Leave(troopweapon, m));
		setItemCarried(troopweapon);
		
		behaviours.add(new AttackNeighboursBehaviour(this, world, m, true, true, "%s has attacked %2s", "%s shoots wildly!", 0.75));
		behaviours.add(new CallBackUp(this, world));
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

	/**
	 * 
	 */
	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() +", " + this.getForce()  + "] is at " + location.getShortDescription();
		
	}
}

