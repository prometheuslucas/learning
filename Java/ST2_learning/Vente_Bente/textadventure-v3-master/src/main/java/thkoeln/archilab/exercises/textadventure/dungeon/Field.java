package thkoeln.archilab.exercises.textadventure.dungeon;

import lombok.Getter;
import lombok.Setter;
import thkoeln.archilab.exercises.textadventure.creatures.Item;
import thkoeln.archilab.exercises.textadventure.domainprimitives.CommandType;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Coordinate;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Printable;

import javax.persistence.*;
import java.util.HashMap;
import java.util.UUID;

@Entity
public class Field implements Printable, Blockable {
    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @Embedded
    @Getter
    private Coordinate coordinate;

    @Transient
    @Getter
    private Blocking blocker;

    @Setter
    @Getter
    @Embedded
    private Item item;

    @OneToOne
    @Getter
    private Field northNeighbour;

    @Getter
    @OneToOne
    private Field westNeighbour;

    @OneToOne
    @Getter
    private Field eastNeighbour;

    @OneToOne
    @Getter
    private Field southNeighbour;

    @Transient
    private HashMap<CommandType, Field> neighbours = null;
    public HashMap<CommandType, Field> getNeighbours() {
        if ( neighbours == null ) {
            neighbours = new HashMap<CommandType, Field>();
            neighbours.put( CommandType.n, northNeighbour );
            neighbours.put( CommandType.w, westNeighbour );
            neighbours.put( CommandType.s, southNeighbour );
            neighbours.put( CommandType.e, eastNeighbour );
        }
        return neighbours;
    }

    /**
     * Constructor called for a fresh (not yet persisted) object
     * @param coordinate
     */
    public Field( Coordinate coordinate ) {
        this.coordinate = coordinate;
    }

    protected Field() {}


    public void setNorthNeighbour(Field northNeighbour ) {
        this.northNeighbour = northNeighbour;
        getNeighbours().put(CommandType.n, northNeighbour);
        if ( northNeighbour != null && northNeighbour.getSouthNeighbour() == null )
            northNeighbour.setSouthNeighbour( this );
    }

    public void setWestNeighbour( Field westNeighbour ) {
        this.westNeighbour = westNeighbour;
        getNeighbours().put( CommandType.w, westNeighbour );
        if( westNeighbour != null && westNeighbour.getEastNeighbour() == null )
            westNeighbour.setEastNeighbour( this );
    }

    public void setEastNeighbour(Field eastNeighbour ) {
        this.eastNeighbour = eastNeighbour;
        getNeighbours().put(CommandType.e, eastNeighbour);
        if ( eastNeighbour != null && eastNeighbour.getWestNeighbour() == null )
            eastNeighbour.setWestNeighbour( this );
    }

    public void setSouthNeighbour( Field southNeighbour ) {
        this.southNeighbour = southNeighbour;
        getNeighbours().put( CommandType.s, southNeighbour );
        if( southNeighbour != null && southNeighbour.getNorthNeighbour() == null )
            southNeighbour.setNorthNeighbour( this );
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
