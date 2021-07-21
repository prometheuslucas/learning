package thkoeln.archilab.exercises.textadventure.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Random;

@Getter
@Setter( AccessLevel.PROTECTED )
@Embeddable
@EqualsAndHashCode
public class Coordinate {

    private Integer x;
    private Integer y;

    public static Coordinate fromInteger( Integer x, Integer y ) throws CoordinateException {
        return new Coordinate( x, y );
    }

    public static Coordinate random( Integer maxX, Integer maxY ) {
        Random random = new Random();
        Coordinate coordinate = null;
        try {
            coordinate = new Coordinate( random.nextInt(maxX - 1), random.nextInt(maxY - 1) );
        }
        catch ( CoordinateException e ) { /* can't happen */ }
        return coordinate;
    }

    protected Coordinate ( Integer x, Integer y ) throws CoordinateException {
        if ( x < 0 || y < 0 ) throw new CoordinateException( "x and y must be >= 0" );
        this.x = x;
        this.y = y;
    }

    protected Coordinate() {}


    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
