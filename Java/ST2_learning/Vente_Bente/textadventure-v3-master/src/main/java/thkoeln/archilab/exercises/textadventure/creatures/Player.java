package thkoeln.archilab.exercises.textadventure.creatures;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.archilab.exercises.textadventure.domainprimitives.*;
import thkoeln.archilab.exercises.textadventure.dungeon.Field;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Entity
public class Player extends LivingCreature implements Printable {
    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @ElementCollection( targetClass = Item.class )
    private final List<Item> items = new ArrayList<>();

    /**
     * Constructor called for a fresh (not yet persisted) object
     * @param strength
     */
    public Player( Strength strength ) {
        super(strength);
    }

    protected Player() {}

    @Override
    public String printSymbol() {
        return "P";
    }

    public void execute( Command command ) throws CommandException {
        if ( command.getCommandType().isMoveCommand() ) {
            move( command.getCommandType() );
        }
        if ( command.getCommandType() == CommandType.t ) take( command.getItemName() );
        if ( command.getCommandType() == CommandType.d ) drop( command.getItemName() );
        if ( command.getCommandType() == CommandType.u ) use( command.getItemName() );
    }

    private void take( String itemName ) throws CommandException {
        if ( !getCurrentField().containsItem() ||
             !getCurrentField().getItem().getName().equals( itemName) )
                    throw new CommandException( "Here is no " + itemName + "." );
        items.add( getCurrentField().getItem() );
        getCurrentField().setItem( null );
        System.out.println( getName() + " has picked up a " + itemName + "." );
    }

    private void drop( String itemName ) throws CommandException {
        Item found = findItemInInventory ( itemName );
        if ( getCurrentField().containsItem() )
            throw new CommandException( "There is no room to drop it." );
        getCurrentField().setItem( found );
        items.remove( found );
    }

    private void use( String itemName ) throws CommandException {
        Item item = findItemInInventory ( itemName );

        // impact on self
        receiveImpact( item.getImpactOnSelf() );
        // impact on others
        for ( Field neighbourField : getCurrentField().getNeighbours().values() ) {
            if ( neighbourField != null && neighbourField.getBlocker() instanceof Impactable) {
                Impactable impactable = (Impactable) neighbourField.getBlocker();
                System.out.println( this + " attacks " + impactable + " with a " + itemName + "!" );
                ((Impactable) neighbourField.getBlocker()).receiveImpact( item.getImpactOnOthers() );
            }
        }
    }

    private Item findItemInInventory( String itemName ) throws CommandException {
        Item found = null;
        for ( Item item : items ) {
            if ( item.getName().equals( itemName ) ) {
                found = item;
                break;
            }
        }
        if ( found == null ) throw new CommandException( this + " doesn't have a " + itemName + "." );
        return found;
    }
}
