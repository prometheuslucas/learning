package thkoeln.archilab.exercises.textadventure.dungeon;

import lombok.Getter;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Coordinate;
import thkoeln.archilab.exercises.textadventure.domainprimitives.CoordinateException;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Printable;
import thkoeln.archilab.exercises.textadventure.domainprimitives.TextAdventureBaseException;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Dungeon implements Printable {
    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @Getter
    private Integer width, height;

    @Transient
    private Field[][] fieldArray = null;

    // Field doesn't have an own repo - is an inner entity in the Dungeon aggregate. Therefore, lifecycle
    // operations must be handled via cascade.
    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    private final List<Field> fields = new ArrayList<>();

    /**
     * To avoid a call like dungeon.getFields()[0][0], and to prevent that Fields can be changed
     * from the outside
     */
    public Field getField( Coordinate coordinate ) {
        ensureFieldArrayHasBeenInitialized();
        return fieldArray[ coordinate.getX() ][ coordinate.getY() ];
    }


    /**
     * Constructor called for a not yet persisted dungeon
     * @param width
     * @param height
     * @throws DungeonException
     */
    public Dungeon( Integer width, Integer height ) throws DungeonException, CoordinateException {
        if( width <= 0 ) throw new DungeonException( "width must be > 0!" );
        if( height <= 0 ) throw new DungeonException( "height must be > 0!" );
        this.width = width;
        this.height = height;
        fieldArray = new Field[width][height];
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                fieldArray[x][y] = new Field( Coordinate.fromInteger( x, y ) );
                fields.add( fieldArray[x][y] );
            }
        }
    }


    protected Dungeon() {}


    /**
     * This needs to be done _after_ persisting dungeon and fields, otherwise there are problems with
     * the order of persisting the fields due to the "arbitrary" cross-relationships of the fields.
     */
    public void defineFieldNeighbours() {
        ensureFieldArrayHasBeenInitialized();
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                // as we move from left to right and from top to bottom, this method of setting
                // neighbours is sufficient - provided all neighbours are properly initialized to null.
                if( x > 0 ) fieldArray[x][y].setWestNeighbour( fieldArray[x-1][y] );
                if( y > 0 ) fieldArray[x][y].setNorthNeighbour( fieldArray[x][y-1] );
            }
        }
    }

    /**
     * fieldArray is transient and needs to be initialized from the persistent members, in case
     * that the entity was loaded from DB.
     */
    private void ensureFieldArrayHasBeenInitialized() {
        if ( fieldArray == null ) {
            fieldArray = new Field[width][height];
            for ( Field field: fields ) {
                fieldArray[ field.getCoordinate().getX() ][  field.getCoordinate().getY() ] = field;
            }
        }
    }


    @Override
    public void print() {
        ensureFieldArrayHasBeenInitialized();
        for ( int y = 0; y < height; y++ ) {
            for ( int x = 0; x < width; x++ ) {
                fieldArray[x][y].print();
                System.out.print( " " );
            }
            System.out.println( "" );
        }
        System.out.println();
    }

    /**
     * @return an arbitrary corner field not occupied by a creature
     */
    public Field nextFreeCornerField() throws TextAdventureBaseException {
        Coordinate corners[] = {
           Coordinate.fromInteger( 0, 0 ),
           Coordinate.fromInteger( 0, getHeight()-1 ),
           Coordinate.fromInteger( getWidth()-1, getHeight()-1 ),
           Coordinate.fromInteger( getWidth()-1, 0 )
        };
        for ( Coordinate corner: corners ) {
            if ( getField( corner ).getBlocker() == null ) return getField( corner );
        }

        throw new DungeonException( "No more free corners!");
    }

    /**
     * @return an arbitrary field not yet containing an item
     */
    public Field nextFieldForItemPlacement() throws DungeonException {
        Coordinate coordinate;
        int counter = 0;
        while ( counter < 200 ) {
            coordinate = Coordinate.random( getWidth()-1, getHeight()-1 );
            if ( !getField( coordinate ).containsItem() ) return getField( coordinate );
            counter++;
        }

        // not really an elegant solution, to rely on a counter ...
        throw new DungeonException( "No more fields not yet containing an item!");
    }

}
