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
import starwars.actions.Train;
import starwars.actions.Healing;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.PatrolBehaviour;
import starwars.swinterfaces.SWGridController;

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
	private PatrolBehaviour path;
	private MessageRenderer m;
	private boolean taken;
	private SWEntityInterface benSS;	// ben's original holding stuff
	private BenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.GOOD, 800, m, world);
		this.m = m;
		path = new PatrolBehaviour(moves);
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		LightSaber bensweapon = new LightSaber(m);
		setItemCarried(bensweapon);
		this.taken = false;
		this.benSS = null;
	}

	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		ben = new BenKenobi(m, world, moves);
		ben.activate();
		return ben;
	}
	
	@Override
	protected void legendAct() {
		
		boolean isCanteen = false;
		boolean LukeAround = false;
		SWEntityInterface fullCan = null;
		SWEntityInterface tobeTrained = null;		//the Actor that ben is going to train
		
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
						if(entity.getLevel() != 0){
							isCanteen = true;
						}
					}
				}
/** for this assignment, we assume that Ben will only train Luke, but we added train affordance to all SWActor
 * because we would like to implement this game in a way that people with higher force ability will be able to train
 * people with lower force ability. This means that Luke can be trained by some other people with higher force ability.
 * */
				else if(entity != this && entity instanceof SWActor){
					if (entity.getSymbol() == "@"){
						tobeTrained = entity;
						if(tobeTrained.getForce() < this.getForce()){
							LukeAround = true;
						}
					}
				}
			}
		}
		
		if (this.getItemCarried()!=null){
			if (this.getItemCarried().hasCapability(Capability.DRINKABLE)){
				isCanteen = false;
			}
		}
		
/**
 * Ben will prioritise attack first. Let say he is healing halfway, Ben will still attack using that canteen.
 * Or when he reached the coordinate with canteen and a Tusken Raider is there, he would kill the TuskenRaider first
 * before healing himself.		
 */
		
		if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " +
		attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, ben, 1);
		}
		
//If luke is around and his force is less than the Ben's force, Ben will stop moving because he will ask to
// train luke. Only when Luke moves away, indicating that he declines the training, then only Ben will continue
// patrol.
		else if(LukeAround){
			System.out.println("NOT MOVING");
		}
		
//if a canteen exist and ben's hitpoint is not maximum and he is not holding a canteen
		else if(isCanteen && this.getHitpoints()!= this.getmaxHitpoints() && !taken){
			if (this.getItemCarried() == null && !(taken)){
				Take theCan = new Take(fullCan,m);
				this.taken = true;
				scheduler.schedule(theCan, this, 1);
			}
			else{
				this.benSS = this.getItemCarried();      // to store whatever Ben is holding previously
				Leave byeItem = new Leave(this.getItemCarried(), m);
				scheduler.schedule(byeItem, this, 0);
				
				Take theCan = new Take(fullCan,m);
				this.taken = true;
				scheduler.schedule(theCan, this, 1);
			}
		}
		
		//when ben is holding a centeen.
		else if (taken && this.getHitpoints()!= this.getmaxHitpoints() && this.getItemCarried().getLevel() > 0){
			Healing heal = new Healing(ben, m);
			scheduler.schedule(heal, this, 1);
		}
		
//when his hitpoints are fully recovered and he is holding a canteen. Drop canteen and pick up his light saber.
// when the canteen level <=0 drop canteen and pick up whatever he left.
		else if((this.getHitpoints() == this.getmaxHitpoints() && this.getItemCarried().hasCapability(Capability.DRINKABLE)) || (taken && this.getItemCarried().getLevel() <= 0)){
			Leave byecanteen = new Leave(this.getItemCarried(), m);
			taken = false;
			scheduler.schedule(byecanteen, this, 0);
			
			if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
				for (SWEntityInterface entity : contents) {
					if (entity != this && !(entity instanceof SWActor)){
						if (entity == this.benSS){ // check is Ben's previous belongings is still around 
							Take benbuddy= new Take(benSS,m);
							scheduler.schedule(benbuddy, this, 1);
						}
					}
				}
			}
		}
		
		else {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

}
