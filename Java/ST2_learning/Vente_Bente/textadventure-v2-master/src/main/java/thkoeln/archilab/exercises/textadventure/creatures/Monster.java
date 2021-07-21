package thkoeln.archilab.exercises.textadventure.creatures;

import lombok.Getter;
import thkoeln.archilab.exercises.textadventure.command.CommandException;
import thkoeln.archilab.exercises.textadventure.command.CommandType;
import thkoeln.archilab.exercises.textadventure.dungeon.Field;
import thkoeln.archilab.exercises.textadventure.fight.Impact;
import thkoeln.archilab.exercises.textadventure.fight.Strength;

import java.util.Random;

public class Monster extends LivingCreature implements Impactable {
    @Getter
    private Item attackCapability;

    public Monster(Strength strength, Impact inflictedDamage ) {
        super(strength);
        attackCapability = new Item( "attackCapability", Impact.from( 0f ), inflictedDamage );
    }

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
