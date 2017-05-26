package starwars.entities.actors.behaviors;

import java.util.*;

import starwars.TRandom;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Speak;

public class TalkingBehaviour extends BehaviourInterface {

    private ArrayList<String> quotes;
    private double probability;
    
    /**
     * This behaviour is protrayed by actors that will likely be speaking
     * according to their probability
     * The actors will talk based on a list of quotes
     * 
     * @param actor the <code>SWActor</code> that is being called
	 * @param world the <code>SWWorld</code> world to which this <code>SWActor</code> belongs to
     * @param quotes a list of quotes to be displayed
     * @param probability how likely the <code>SWActor</code> will speak
     */
    
    public TalkingBehaviour(SWActor actor, SWWorld world, ArrayList<String> quotes, double probability) {
    	super(actor, world);
    	this.quotes = quotes;
    	this.probability = probability;
    }

    /**
     * When there is a probability that the actor will talk, it will speak and 
     * display quote randomly taken from a list of quotes
     */
    @Override
    public boolean ExecuteBehaviour() {
    	if (Math.random() > probability){
    		actor.schedule(new Speak(messageRenderer, TRandom.itemFrom(quotes)));
    	}
    	return true;
    }
    
}