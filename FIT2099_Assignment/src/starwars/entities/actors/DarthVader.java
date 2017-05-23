package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.entities.actors.behaviors.WanderAround;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.ChokeBehaviour;

public class DarthVader extends SWActor{

	public DarthVader(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.EVIL, hitpoints, m, world);
		this.setShortDescription("Vader");
		this.setLongDescription("Darth Vader, Anakin Skywalker");
		this.setSymbol("#");
		
		SWEntity Vaderweapon = new LightSaber(m);
		Vaderweapon.removeAffordance(Take.class);
		Vaderweapon.addAffordance(new Leave(Vaderweapon, m));
		setItemCarried(Vaderweapon);
		
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
