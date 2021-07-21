package thkoeln.archilab.exercises.dungeon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import thkoeln.archilab.exercises.textadventure.domainprimitives.CommandType;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Coordinate;
import thkoeln.archilab.exercises.textadventure.dungeon.Dungeon;
import thkoeln.archilab.exercises.textadventure.dungeon.DungeonException;
import thkoeln.archilab.exercises.textadventure.dungeon.Field;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonTest {
    private Dungeon dungeon;
    private Coordinate corner00;

    @BeforeEach
    public void setUp() throws Exception {
        corner00 = Coordinate.fromInteger( 0, 0 );
    }


    @Test
    public void test1by1Dungeon() throws Exception {
        // given
        dungeon =  new Dungeon( 1, 1);
        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        // when
        Field field = dungeon.getField( corner00 );

        // then
        assertNull( field.getNeighbours().get( CommandType.n ) );
        assertNull( field.getNeighbours().get( CommandType.e ) );
        assertNull( field.getNeighbours().get( CommandType.s ) );
        assertNull( field.getNeighbours().get( CommandType.w ) );
    }


    @Test
    public void test2by2DungeonAt00() throws Exception {
        // given
        dungeon =  new Dungeon( 2, 2);
        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        // when
        Field field = dungeon.getField( corner00 );

        // then
        assertNull( field.getNeighbours().get( CommandType.n ) );
        assertNotNull( field.getNeighbours().get( CommandType.e ) );
        assertNotNull( field.getNeighbours().get( CommandType.s ) );
        assertNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void test2by2DungeonAt01() throws Exception {
        // given
        dungeon =  new Dungeon( 2, 2);
        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        // when
        Field field = dungeon.getField( Coordinate.fromInteger( 0, 1 ) );

        // then
        assertNotNull( field.getNeighbours().get( CommandType.n ) );
        assertNotNull( field.getNeighbours().get( CommandType.e ) );
        assertNull( field.getNeighbours().get( CommandType.s ) );
        assertNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void test2by2DungeonAt10() throws Exception {
        // given
        dungeon =  new Dungeon( 2, 2);
        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        // when
        Field field = dungeon.getField( Coordinate.fromInteger( 1, 0 ) );

        // then
        assertNull( field.getNeighbours().get( CommandType.n ) );
        assertNull( field.getNeighbours().get( CommandType.e ) );
        assertNotNull( field.getNeighbours().get( CommandType.s ) );
        assertNotNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void test2by2DungeonAt11() throws Exception {
        // given
        dungeon =  new Dungeon( 2, 2);
        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        // when
        Field field = dungeon.getField( Coordinate.fromInteger( 1, 1 ) );

        // then
        assertNotNull( field.getNeighbours().get( CommandType.n ) );
        assertNull( field.getNeighbours().get( CommandType.e ) );
        assertNull( field.getNeighbours().get( CommandType.s ) );
        assertNotNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void test3by3DungeonAtCenter() throws Exception {
        // given
        dungeon =  new Dungeon( 3, 3);
        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        // when
        Field field = dungeon.getField( Coordinate.fromInteger( 1, 1 ) );

        // then
        assertNotNull( field.getNeighbours().get( CommandType.n ) );
        assertNotNull( field.getNeighbours().get( CommandType.e ) );
        assertNotNull( field.getNeighbours().get( CommandType.s ) );
        assertNotNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void testInvalidDungeon() {
        // given
        // when
        // then
        assertThrows( DungeonException.class, () ->
            { dungeon =  new Dungeon( -1, 0 ); } );
    }


}
