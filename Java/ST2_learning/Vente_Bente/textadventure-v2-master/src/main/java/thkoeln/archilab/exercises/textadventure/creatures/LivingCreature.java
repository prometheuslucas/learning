package thkoeln.archilab.exercises.textadventure.creatures;

import lombok.Getter;
import lombok.Setter;
import thkoeln.archilab.exercises.textadventure.dungeon.AlreadyBlockedException;
import thkoeln.archilab.exercises.textadventure.fight.Impact;
import thkoeln.archilab.exercises.textadventure.fight.Strength;
import thkoeln.archilab.exercises.textadventure.command.CommandException;
import thkoeln.archilab.exercises.textadventure.command.CommandType;
import thkoeln.archilab.exercises.textadventure.dungeon.Blocking;
import thkoeln.archilab.exercises.textadventure.dungeon.Field;
import thkoeln.archilab.exercises.textadventure.Printable;

@Getter
public abstract class LivingCreature implements Printable, Blocking, Impactable {
    private Strength strength;

    @Setter
    private String name;

    private Field currentField = null;

    public abstract String printSymbol();

    public LivingCreature( Strength strength ) {
        this.strength = strength;
    }

    public Field getCurrentField() {
        return currentField;
    }

    /**
     * Only protected, not public, as it should not be set by the outside world.
     * Instead, outside calls should use some dedicated move command. Only instances of
     * LivingCreature may directly call this method.
     */
    protected void move( CommandType direction ) throws CommandException {
        Field newField = currentField.getNeighbours().get( direction );
        if ( newField == null ) throw new CommandException( "You can't go that way!" );
        // The order of the following lines matters! First, blocking the new field is required. If there is
        // already an occupant on the new field, an exception is thrown and we remain safely on our current field.
        // If we would leave the current field first, and then block the new field, we would sit on no field
        // at all!
        newField.block( this );
        currentField.unblock();
        currentField = newField;
    }

    @Override
    public boolean isDead() {
        return getStrength().meansDeath();
    }

    @Override
    public void receiveImpact( Impact impact ) {
        strength = strength.afterImpact( impact );
        if ( isDead() ) {
            System.out.println( this + " is dead!" );
        }
        else {
            System.out.println( this + " has now a strength of " + strength +
                " (changed by " + impact + ")" );
        }
    }

    @Override
    public void print() {
        ColorGradientPrinter.colorPrint( printSymbol(), strength.relative() );
    }

    @Override
    public void place( Field field ) throws AlreadyBlockedException {
        field.block( this );
        if ( currentField != null ) currentField.unblock();
        currentField = field;
    }

    @Override
    public String toString() {
        return getName();
    }
}
