package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.entities.LightSaber;
import starwars.entities.actors.SWOrganicActor;
import starwars.entities.actors.behaviors.WanderAround;
import starwars.entities.actors.behaviors.TortureLukeBehaviour;
import starwars.entities.actors.behaviors.ChokeBehaviour;

public class DarthVader extends SWOrganicActor{
	
	/**
	 * Darth Vader
	 * 
	 * A major character from Team EVIL who is extremely strong in force and 
	 * will have a 50% chance of using his force to Force Choke other actors with it, 
	 * regardless of the target's team.
	 * He wanders around Death Star at random and carries a Lightsaber.
	 * If he encounters Luke, he has a 50% of turning Luke to the Dark side,
	 * if he has successfully turned Luke, the game is lost.
	 * If Darth Vader is dead, the game is won, since he is very difficult of kill.
	 * 
	 * @param hitpoints the hit points of this <code>SWOrganicActor</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>SWOrganicActor</code> belongs to
	 */

	public DarthVader(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.EVIL, hitpoints, m, world);
		this.setShortDescription("Vader");
		this.setLongDescription("Darth Vader, Anakin Skywalker");
		this.setSymbol("#");
		
		SWEntity Vaderweapon = new LightSaber(m);
		Vaderweapon.removeAffordance(Take.class);
		Vaderweapon.addAffordance(new Leave(Vaderweapon, m));
		setItemCarried(Vaderweapon);
		
		behaviours.add(new TortureLukeBehaviour(this, this.world, m));
		behaviours.add(new ChokeBehaviour(this, world, m, true, "%s has Force Choked %2s"));
		behaviours.add(new WanderAround(this, this.world));
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

		executeBehaviours();
	}

}
