package thkoeln.archilab.exercises.textadventure.dungeon;

import lombok.Getter;
import thkoeln.archilab.exercises.textadventure.Printable;


public class Dungeon implements Printable {

    @Getter // no later changes to the size!
    private int width, height;
    private Field[][] fields;

    /**
     * To avoid a call like dungeon.getFields()[0][0], and to prevent that Fields can be changed
     * from the outside
     */
    public Field getField(int x, int y ) {
        return fields[x][y];
    }

    public Dungeon( int width, int height ) {
        if( width <= 0 ) throw new DungeonException( "width must be > 0!" );
        if( height <= 0 ) throw new DungeonException( "height must be > 0!" );
        this.width = width;
        this.height = height;
        fields = new Field[width][height];
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                fields[x][y] = new Field();
                // as we move from left to right and from top to bottom, this method of setting
                // neighbours is sufficient - provided all neighbours are properly initialized to null.
                if( x > 0 ) fields[x][y].setWestNeighbour( fields[x-1][y] );
                if( y > 0 ) fields[x][y].setNorthNeighbour( fields[x][y-1] );
            }
        }
    }

    @Override
    public void print() {
        for ( int y = 0; y < height; y++ ) {
            for ( int x = 0; x < width; x++ ) {
                fields[x][y].print();
                System.out.print( " " );
            }
            System.out.println( "" );
        }
        System.out.println();
    }
}
