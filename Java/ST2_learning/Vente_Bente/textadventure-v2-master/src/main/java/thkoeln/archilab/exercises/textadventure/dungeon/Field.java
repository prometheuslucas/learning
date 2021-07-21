package thkoeln.archilab.exercises.textadventure.dungeon;

import lombok.Getter;
import lombok.Setter;
import thkoeln.archilab.exercises.textadventure.command.CommandType;
import thkoeln.archilab.exercises.textadventure.creatures.Item;
import thkoeln.archilab.exercises.textadventure.Printable;

import java.util.HashMap;

public class Field implements Printable, Blockable {

    @Getter
    private Blocking blocker;

    @Setter
    @Getter
    private Item item;

    @Getter
    private HashMap<CommandType, Field> neighbours = new HashMap<>();

    public Field() {
        neighbours.put( CommandType.n, null );
        neighbours.put( CommandType.w, null );
        neighbours.put( CommandType.s, null );
        neighbours.put( CommandType.e, null );
    }

    public void setNorthNeighbour(Field northNeighbour ) {
        neighbours.put(CommandType.n, northNeighbour);
        if (northNeighbour != null) northNeighbour.getNeighbours().put(CommandType.s, this);
    }

    public void setWestNeighbour( Field westNeighbour ) {
        neighbours.put( CommandType.w, westNeighbour );
        if( westNeighbour != null ) westNeighbour.getNeighbours().put( CommandType.e, this );
    }


    @Override
    public void unblock() {
        this.blocker = null;
    }

    @Override
    public void block(Blocking blocker) throws AlreadyBlockedException {
        if( this.blocker != null ) {
            throw new AlreadyBlockedException( "This field is already blocked!" );
        }
        this.blocker = blocker;
    }

    public boolean containsItem() {
        return ( item != null );
    }

    @Override
    public void print() {
        if( blocker != null ) {
            blocker.print();
        }
        else if( item != null ) {
            item.print();
        }
        else {
            System.out.print( "_" );
        }
    }
}
