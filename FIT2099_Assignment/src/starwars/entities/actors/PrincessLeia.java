package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.Team;

public class PrincessLeia extends SWActor{

	public PrincessLeia(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 200, m, world);
		
		this.setShortDescription("Leia");
		this.setLongDescription("Leia, Princess Leia Organa");
		this.setSymbol("<3");
		behaviours.add(followBehaviour);
	}
	
	@Override
	public void act() {
		
		SWLocation location = this.world.getEntityManager().whereIs(this);
		
		if (isDead()) {
			return;
		}
		
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		
		if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
			for (SWEntityInterface entity : contents) {
				if (entity != this && entity instanceof SWActor){
					if (entity.getShortDescription() == "Luke"){ // check is Ben's previous belongings is still around 
						executeBehaviours();
					}
				}
			}
		}
	
	}
}
