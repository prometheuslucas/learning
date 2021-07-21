package thkoeln.archilab.exercises.textadventure.creatures;

import lombok.Getter;
import lombok.Setter;
import thkoeln.archilab.exercises.textadventure.domainprimitives.*;
import thkoeln.archilab.exercises.textadventure.dungeon.AlreadyBlockedException;
import thkoeln.archilab.exercises.textadventure.dungeon.Blocking;
import thkoeln.archilab.exercises.textadventure.dungeon.Field;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class LivingCreature implements Printable, Blocking, Impactable {
    @Id
    private final UUID id = UUID.randomUUID();

    @Embedded
    @Setter
    protected Strength strength;

    @Setter
    private String name;

    @OneToOne
    private Field currentField;

    /**
     * Constructor called for a fresh (not yet persisted) object
     * @param strength
     */
    public LivingCreature( Strength strength ) {
        this.strength = strength;
    }


    protected LivingCreature() {}

    /**
     * The field blocking mechanism has to be refreshed from the outside, unfortunenately,
     * after a LivingCreature has been fetched from DB. The relationship Player => Field and
     * Monster => Field is unidirectional. Therefore, the "blocking" pointer in Field
     * (pointing back to any blocker) is just transient.
     * I couldn't yet think of a better way to make sure it is set properly, then to do this
     * now after loading from DB.
     */
    public void blockField() {
        try {
            currentField.block(this );
        }
        catch ( AlreadyBlockedException alreadyBlockedException ) {
            // ... should not happen ...
            alreadyBlockedException.printStackTrace();
        }
    }


    public abstract String printSymbol();


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
