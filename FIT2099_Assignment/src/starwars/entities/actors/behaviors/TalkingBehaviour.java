package starwars.entities.actors.behaviors;

import java.util.*;

import starwars.TRandom;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Speak;

public class TalkingBehaviour extends BehaviourInterface {

    private ArrayList<String> quotes;
    private double probability;
    
    public TalkingBehaviour(SWActor actor, SWWorld world, ArrayList<String> quotes, double probability) {
    	super(actor, world);
    	this.quotes = quotes;
    	this.probability = probability;
    }

    @Override
    public boolean ExecuteBehaviour() {
    	if (Math.random() > probability){
    		System.out.println("TALKINGG");
    		actor.schedule(new Speak(messageRenderer, TRandom.itemFrom(quotes)));
    	}
    	return true;
    }
    
}