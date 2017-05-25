package starwars;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.FlyToDeath;
import starwars.actions.FlyToMain;
import starwars.actions.FlyToYavin;
import starwars.actions.Take;
import starwars.entities.*;
import starwars.entities.actors.*;

/**
 * Class representing a world in the Star Wars universe. 
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 */
public class SWWorld extends World {
	
	/**
	 * <code>SWGrid</code> of this <code>SWWorld</code>
	 */
	private SWGrid myGrid;
	private SWGrid main;
	private SWGrid deathstar;
	private SWGrid yavinIV;
	
	private ArrayList<SWGrid> maps;

	
	private MessageRenderer messageRenderer;
	
	/**The entity manager of the world which keeps track of <code>SWEntities</code> and their <code>SWLocation</code>s*/
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();
	
	/**
	 * Constructor of <code>SWWorld</code>. This will initialize the <code>SWLocationMaker</code>
	 * and the grid.
	 */
	public SWWorld() {
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		this.main = new SWGrid(10, 10, factory, "Main");
		this.deathstar = new SWGrid(10, 10, factory, "DeathStar");
		this.yavinIV = new SWGrid(2, 2, factory, "Yavin IV");
		
		ArrayList<SWGrid> mapss = new ArrayList<SWGrid>();
		mapss.add(this.main);
		mapss.add(this.deathstar);
		mapss.add(this.yavinIV);
		this.maps = mapss;
		
		//myGrid = deathstar;
		//myGrid = yavinIV;
		myGrid = main;
		space = myGrid;
		
	}

	/** 
	 * Returns the height of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int height() {
		return space.getHeight();
	}
	
	/** 
	 * Returns the width of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int width() {
		return space.getWidth();
	}
	
    public MessageRenderer getMessageRenderer() {
    	return messageRenderer;
    }
	
	/**
	 * Set up the world, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 */
	public void initializeWorld(MessageRenderer iface) {
		messageRenderer = iface;
		SWLocation loc;
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = main.getLocationByCoordinates(col, row);
				loc.setLongDescription("Main (" + col + ", " + row + ")");
				loc.setShortDescription("Main (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		// BadLands
		for (int row = 5; row < 8; row++) {
			for (int col = 4; col < 7; col++) {
				loc = main.getLocationByCoordinates(col, row);
				loc.setLongDescription("Badlands (" + col + ", " + row + ")");
				loc.setShortDescription("Badlands (" + col + ", " + row + ")");
				loc.setSymbol('b');
			}
		}
		
		//Ben's Hut
		loc = main.getLocationByCoordinates(5, 6);
		loc.setLongDescription("Ben's Hut");
		loc.setShortDescription("Ben's Hut");
		loc.setSymbol('H');
		

		
		
		// Beggar's Canyon 
		for (int col = 3; col < 8; col++) {
			loc = main.getLocationByCoordinates(col, 8);
			loc.setShortDescription("Beggar's Canyon (" + col + ", " + 8 + ")");
			loc.setLongDescription("Beggar's Canyon  (" + col + ", " + 8 + ")");
			loc.setSymbol('C');
			loc.setEmptySymbol('='); // to represent sides of the canyon
		}
		
		// Moisture Farms
		for (int row = 0; row < 10; row++) {
			for (int col = 8; col < 10; col++) {
				loc = main.getLocationByCoordinates(col, row);
				loc.setLongDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setShortDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setSymbol('F');
				
				// moisture farms have reservoirs
				entityManager.setLocation(new Reservoir(iface), loc);				
			}
		}
		
		// Luke
		Player luke = new Player(Team.GOOD, 1000, iface, this);
		
		loc = main.getLocationByCoordinates(3, 3);
		luke.setShortDescription("Luke");
		entityManager.setLocation(luke, loc);
		luke.resetMoveCommands(loc);

		// we need to resetMOveCommands for incase they are mindControlled.
		//Uncle Owen
		TestActor owen = new TestActor("UncleOwen", iface, this);
		
		owen.setSymbol("UO");
		loc = main.getLocationByCoordinates(6,5);
		entityManager.setLocation(owen, loc);
		owen.resetMoveCommands(loc);
		
		//Aunt Beru
		TestActor beru = new TestActor("AuntBeru", iface, this);
		
		beru.setSymbol("AB");
		loc = main.getLocationByCoordinates(8,8);
		entityManager.setLocation(beru, loc);
		beru.resetMoveCommands(loc);
		
		// Ben Kenobi's hut
		/*
		 * Scatter some other entities and actors around
		 */
		// a canteen
		loc = main.getLocationByCoordinates(3,1);
		SWEntity canteen = new Canteen(iface, 10,0);
		canteen.setSymbol("o");
		canteen.setHitpoints(500);
		entityManager.setLocation(canteen, loc);
		canteen.addAffordance(new Take(canteen, iface));
		
		//adding a canteen somewhere at Ben's patrol route. Since Ben would be moving East the 2 times,
		// since the coordinate is (col, row), thus the coordinate is (4+1+1,5) = (6,5)
		loc = main.getLocationByCoordinates(6, 5);
		SWEntity canteenBen = new Canteen(iface, 10, 10);
		canteenBen.setSymbol("o");
		entityManager.setLocation(canteenBen, loc);
		canteenBen.addAffordance(new Take(canteenBen, iface));

		// an oil can treasure
		loc = main.getLocationByCoordinates(1,5);
		SWEntity oilcan = new SWEntity(iface);
		oilcan.setShortDescription("an oil can");
		oilcan.setLongDescription("an oil can, which would theoretically be useful for fixing robots");
		oilcan.setSymbol("x");
		oilcan.setHitpoints(100);
		// add a Take affordance to the oil can, so that an actor can take it
		entityManager.setLocation(oilcan, loc);
		oilcan.addAffordance(new Take(oilcan, iface));
		
		// a lightsaber
		LightSaber lightSaber = new LightSaber(iface);
		loc = main.getLocationByCoordinates(5,5);
		entityManager.setLocation(lightSaber, loc);
		
		// A blaster 
		Blaster blaster = new Blaster(iface);
		loc = main.getLocationByCoordinates(2, 5);
		entityManager.setLocation(blaster, loc);

/**We decided to setSymbol "T" for all TuskenRaider because we realised that if we set different symbol to 
* different TuskenRaider, it's going to be hard for users to identify which symbol indicates Tusken Raiders
* and which is some other things. So overall, in the map symbol "T indicates TuskenRaiders the players will
* only know which TuskenRaider has the name of what when they meet up at the same coordinate."*/				
		
		// A Tusken Raider
		TuskenRaider tim = new TuskenRaider(10, "Tim", iface, this);
		
		tim.setSymbol("T");
		loc = main.getLocationByCoordinates(4,5);
		entityManager.setLocation(tim, loc);
		
		// A Tusken Raider
		TuskenRaider david = new TuskenRaider(10, "David", iface, this);
		
		david.setSymbol("T");
		loc = main.getLocationByCoordinates(7,2);
		entityManager.setLocation(david, loc);
		
		// A Tusken Raider
		TuskenRaider zac = new TuskenRaider(10, "Zac", iface, this);
		
		zac.setSymbol("T");
		loc = main.getLocationByCoordinates(8,5);
		entityManager.setLocation(zac, loc);
		
		// A Tusken Raider
		TuskenRaider tom = new TuskenRaider(10, "Tom", iface, this);
		
		tom.setSymbol("T");
		loc = main.getLocationByCoordinates(1,1);
		entityManager.setLocation(tom, loc);
		
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.SOUTH,
                CompassBearing.WEST, CompassBearing.WEST,
                CompassBearing.SOUTH,
                CompassBearing.EAST, CompassBearing.EAST,
                CompassBearing.NORTHWEST, CompassBearing.NORTHWEST};
		
		BenKenobi ben = BenKenobi.getBenKenobi(iface, this, patrolmoves);
		ben.setSymbol("B");
		loc = main.getLocationByCoordinates(4,  5);
		entityManager.setLocation(ben, loc);

		//Droids
		Direction[] r2d2Patrol = { CompassBearing.EAST, CompassBearing.EAST, CompassBearing.EAST, CompassBearing.EAST, CompassBearing.EAST,
				CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST };
		
		// R2D2 new
		SWRobots RD = new R2D2(200, r2d2Patrol, iface, this);
		
		loc = main.getLocationByCoordinates(3,3);
		entityManager.setLocation(RD, loc);
		
		
		// C3PO new
		SWRobots C3Po = new C3PO(200, iface, this);
		loc = main.getLocationByCoordinates(5,5);
		entityManager.setLocation(C3Po, loc);
		
		//falcon
		Falcon falM = new Falcon(iface);
		loc = main.getLocationByCoordinates(0, 0);
		falM.removeAffordance(FlyToMain.class);
		entityManager.setLocation(falM, loc);
		
/**DEATH STAR*/		
			
		// Set default location string
		for (int row=0; row < height(); row++) {
			for (int col=0; col < width(); col++) {
				loc = deathstar.getLocationByCoordinates(col, row);
				loc.setLongDescription("DeathStar (" + col + ", " + row + ")");
				loc.setShortDescription("DeathStar (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		// Princess
		PrincessLeia Princess = new PrincessLeia(200, iface, this);
		loc = deathstar.getLocationByCoordinates(9,9);
		entityManager.setLocation(Princess, loc);
		
		// Darth Vader
		DarthVader Anakin = new DarthVader(100000, iface, this);
		loc = deathstar.getLocationByCoordinates(4,4);
		entityManager.setLocation(Anakin, loc);
		
		// Stormtrooper 1
		Stormtrooper storm1 = new Stormtrooper(100, "Storm1", iface, this);
		loc = main.getLocationByCoordinates(3,3);
		entityManager.setLocation(storm1, loc);
		
		// Stormtrooper 2
		Stormtrooper storm2 = new Stormtrooper(100, "Storm2", iface, this);
		loc = deathstar.getLocationByCoordinates(4,6);
		entityManager.setLocation(storm2, loc);
		
		// Stormtrooper 3
		Stormtrooper storm3 = new Stormtrooper(100, "Storm3", iface, this);
		loc = deathstar.getLocationByCoordinates(5,5);
		entityManager.setLocation(storm3, loc);
		
		// Stormtrooper 4
		Stormtrooper storm4 = new Stormtrooper(100, "Storm4", iface, this);
		loc = deathstar.getLocationByCoordinates(6,3);
		entityManager.setLocation(storm4, loc);
		
		// Stormtrooper 5
		Stormtrooper storm5 = new Stormtrooper(100, "Storm5", iface, this);
		loc = deathstar.getLocationByCoordinates(2,7);
		entityManager.setLocation(storm5, loc);
		
		//falcon
		Falcon falD = new Falcon(iface);
		loc = deathstar.getLocationByCoordinates(0, 0);
		falD.removeAffordance(FlyToDeath.class);
		entityManager.setLocation(falD, loc);


/**YAVIN IV*/				
		// Set default location string
		for (int row=0; row < 2; row++) {
			for (int col=0; col < 2; col++) {
				loc = yavinIV.getLocationByCoordinates(col, row);
				loc.setLongDescription("Yavin IV (" + col + ", " + row + ")");
				loc.setShortDescription("Yavin IV (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		// Admiral Ackbar
		AdmiralAckbar ackbar = new AdmiralAckbar(100, iface, this);
		loc = yavinIV.getLocationByCoordinates(1,1);
		entityManager.setLocation(ackbar, loc);
		
		// Mon Mothma
		MonMothma moth = new MonMothma(100, iface, this);
		loc = yavinIV.getLocationByCoordinates(0,1);
		entityManager.setLocation(moth, loc);
		
		//falcon
		Falcon falY = new Falcon(iface);
		loc = yavinIV.getLocationByCoordinates(0, 0);
		falY.removeAffordance(FlyToYavin.class);
		entityManager.setLocation(falY, loc);
		
		
	}

	/*
	 * Render method was removed from here
	 */
	
	/**
	 * Determine whether a given <code>SWActor a</code> can move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canMove(SWActor a, Direction whichDirection) {
		SWLocation where = (SWLocation)entityManager.whereIs(a); // requires a cast for no reason I can discern
		if (where!= null){
			return where.hasExit(whichDirection);
		}
		return false;
	}
	
	/**
	 * Accessor for the grid.
	 * 
	 * @author ram
	 * @return the grid
	 */
	public SWGrid getGrid() {
		return myGrid;
	}
	
    public void SetMyGrid(String mapName){
    	for(SWGrid world: maps){
    		if (world.getMapName() == mapName){
    			this.myGrid = world;
    		}
    	}
    }

	/**
	 * Move an actor in a direction.
	 * 
	 * @author ram
	 * @param a the actor to move
	 * @param whichDirection the direction in which to move the actor
	 */
	public void moveEntity(SWActor a, Direction whichDirection) {
		
		//get the neighboring location in whichDirection
		Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
		
		// Base class unavoidably stores superclass references, so do a checked downcast here
		if (loc instanceof SWLocation)
			//perform the move action by setting the new location to the the neighboring location
			entityManager.setLocation(a, (SWLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
	}

	/**
	 * Returns the <code>Location</code> of a <code>SWEntity</code> in this grid, null if not found.
	 * Wrapper for <code>entityManager.whereIs()</code>.
	 * 
	 * @author 	ram
	 * @param 	e the entity to find
	 * @return 	the <code>Location</code> of that entity, or null if it's not in this grid
	 */
	public Location find(SWEntityInterface e) {
		return entityManager.whereIs(e); //cast and return a SWLocation?
	}

	/**
	 * This is only here for compliance with the abstract base class's interface and is not supposed to be
	 * called.
	 */

	@SuppressWarnings("unchecked")
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return SWWorld.getEntitymanager();
	}

	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>SWEntities</code> and
	 * <code>SWLocations</code> in <code>SWWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>SWWorld</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}
}
