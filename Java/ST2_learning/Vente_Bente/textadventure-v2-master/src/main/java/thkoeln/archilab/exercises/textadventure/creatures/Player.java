package thkoeln.archilab.exercises.textadventure.creatures;

import thkoeln.archilab.exercises.textadventure.fight.Strength;
import thkoeln.archilab.exercises.textadventure.command.Command;
import thkoeln.archilab.exercises.textadventure.command.CommandException;
import thkoeln.archilab.exercises.textadventure.command.CommandType;
import thkoeln.archilab.exercises.textadventure.dungeon.Field;
import thkoeln.archilab.exercises.textadventure.Printable;

import java.util.HashMap;

public class Player extends LivingCreature implements Printable {

    private HashMap<String, Item> inventory = new HashMap<>();

    public Player( Strength strength ) {
        super(strength);
    }

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
        inventory.put( itemName, getCurrentField().getItem() );
        getCurrentField().setItem( null );
        System.out.println( getName() + " has picked up a " + itemName + "." );
    }

    private void drop( String itemName ) throws CommandException {
        if ( !inventory.containsKey( itemName ) )
            throw new CommandException( this + " doesn't have a " + itemName + "." );
        if ( getCurrentField().containsItem() )
            throw new CommandException( "There is no room to drop it." );
        getCurrentField().setItem( inventory.get( itemName ) );
        inventory.remove( itemName );
    }

    private void use( String itemName ) throws CommandException {
        if ( !inventory.containsKey( itemName ) )
            throw new CommandException( this + " doesn't have a " + itemName + "." );
        Item item = inventory.get( itemName );

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
}
