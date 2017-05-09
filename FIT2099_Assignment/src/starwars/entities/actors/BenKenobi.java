package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntityInterface;
import starwars.SWActor;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.Capability;
import starwars.actions.Move;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.actions.Healing;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.Patrol;

/**
 * Ben (aka Obe-Wan) Kenobi.  
 * 
 * At this stage, he's an extremely strong critter with a <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * 
 * Note that you can only create ONE Ben, like all SWLegends.
 * @author rober_000
 *
 */
public class BenKenobi extends SWLegend {

	private static BenKenobi ben = null; // yes, it is OK to return the static instance!
	private Patrol path;
	private MessageRenderer m;
	private boolean taken;
	private boolean buddy; // to check if ben's light saber is always along with him
	private int maxHitpoint;
	private BenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.GOOD, 800, m, world);
		this.m = m;
		maxHitpoint = 1020;
		path = new Patrol(moves);
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		LightSaber bensweapon = new LightSaber(m);
		setItemCarried(bensweapon);
		buddy = true;
	}

	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		ben = new BenKenobi(m, world, moves);
		ben.activate();
		return ben;
	}
	
	@Override
	protected void legendAct() {
		
		boolean isCanteen = false;
		SWEntityInterface fullCan = null;
		SWEntityInterface benSS = null;
		
		SWLocation location = this.world.getEntityManager().whereIs(this);

		if(isDead()) {
			return;
		}
		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(ben,  ben.world, true, true);
		
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		
		if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
			for (SWEntityInterface entity : contents) {
				if (entity != this && !(entity instanceof SWActor)){
					if (entity.hasCapability(Capability.DRINKABLE) == true){ // don't include self in scene description
						fullCan = entity;
						if(entity.getHitpoints() != 0){
							isCanteen = true;
						}
					}
					if (entity.getShortDescription() == "A Lightsaber"){
						benSS = entity;
					}
				}

			}
		}
		
		if (this.getItemCarried()!=null){
			if (this.getItemCarried().hasCapability(Capability.DRINKABLE)){
				if(this.getItemCarried().getLevel() != 0)
				isCanteen = true;
			}
			else if(this.getItemCarried().getShortDescription() == "A Lightsaber"){
				buddy = true;
			}
		}
		
		if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " +
		attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, ben, 1);
		}
		
		else if(isCanteen && this.getHitpoints()!= maxHitpoint && buddy){
			Leave byeItem = new Leave(this.getItemCarried(), m);
			buddy = false;
			scheduler.schedule(byeItem, this, 0);
			
			Take theCan = new Take(fullCan,m);
			taken = true;
			scheduler.schedule(theCan, this, 1);
		}
		
		//when his hitpoints are fully recovered and he is not holding his lightsaber. Drop canteen and
		//pick up his light saber.
		else if((this.getHitpoints()== maxHitpoint && !(buddy) && taken)||(this.getItemCarried().getLevel() <= 0 && !(buddy))){
			Leave byecanteen = new Leave(this.getItemCarried(), m);
			taken = false;
			scheduler.schedule(byecanteen, this, 0);
			
			Take benbuddy= new Take(benSS,m);
			scheduler.schedule(benbuddy, this, 1);
		}
		
		//when ben is holding a centeen.
		else if (taken && this.getHitpoints()!= maxHitpoint){
			Healing heal = new Healing(ben, m);
			scheduler.schedule(heal, this, 1);
		}
		
		else {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

}
