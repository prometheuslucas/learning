package thkoeln.archilab.exercises.textadventure.creatures;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.archilab.exercises.textadventure.domainprimitives.CommandException;
import thkoeln.archilab.exercises.textadventure.domainprimitives.CommandType;
import thkoeln.archilab.exercises.textadventure.dungeon.Field;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Impact;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Strength;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Random;
import java.util.UUID;

@Entity
@Getter
public class Monster extends LivingCreature implements Impactable {
    @Embedded
    private Item attackCapability;

    /**
     * Constructor called for a fresh (not yet persisted) object
     * @param strength
     * @param inflictedDamage
     */
    public Monster( Strength strength, Impact inflictedDamage ) {
        super(strength);
        attackCapability = new Item( "attackCapability", Impact.from( 0f ), inflictedDamage );
    }

    protected Monster() {}


    public void attack() {
        if ( !isDead() ) {
            for (Field neighbourField : getCurrentField().getNeighbours().values()) {
                if (neighbourField != null && neighbourField.getBlocker() instanceof Impactable) {
                    Impactable impactable = (Impactable) neighbourField.getBlocker();
                    if ( !impactable.isDead() ) {
                        System.out.println( this + " attacks " + impactable + "!" );
                        impactable.receiveImpact( attackCapability.getImpactOnOthers() );
                    }
                }
            }
        }
    }

    public void randomWalk() {
        if ( isDead() ) return;
        Random random = new Random();
        // move in 7 out of 10 cases
        if ( random.nextFloat() > 0.3f ) {
            // if this random move is illegal, just don't do it :-), no damage done.
            try { move(CommandType.randomDirection()); }
                catch ( CommandException commandException ) {}
        }
    }


    @Override
    public String printSymbol() {
        return getName().substring( 0, 1 );
    }

}
