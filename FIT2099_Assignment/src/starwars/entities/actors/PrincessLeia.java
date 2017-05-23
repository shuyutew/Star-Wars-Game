package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.Team;
import starwars.entities.actors.behaviors.FollowBehaviour;

public class PrincessLeia extends SWActor{
	
	private boolean meeted = false;

	public PrincessLeia(int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 200, m, world);
		
		this.setShortDescription("Leia");
		this.setLongDescription("Leia, Princess Leia Organa");
		this.setSymbol("<");

	}
	
    @Override
    protected void executeBehaviours() {
	//say(describeLocation()); // Too verbose, good for debugging though.
    	super.executeBehaviours();
    }
	
	@Override
	public void act() {
		
		SWLocation location = this.world.getEntityManager().whereIs(this);
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		
		if (isDead()) {
			return;
		}
		
		if (meeted){
			executeBehaviours();
		}
		
		else if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
			for (SWEntityInterface entity : contents) {
				if (entity != this && entity instanceof SWActor){
					if (entity.getShortDescription() == "Luke"){ // check is Ben's previous belongings is still around
						this.behaviours.add(new FollowBehaviour(this, this.world, (SWActor)entity));
						executeBehaviours();
						meeted = true;
						return;
					}
				}
			}
		}
	
	}
}
