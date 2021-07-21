package thkoeln.archilab.exercises.dungeon;

import org.junit.jupiter.api.Test;
import thkoeln.archilab.exercises.textadventure.command.CommandType;
import thkoeln.archilab.exercises.textadventure.dungeon.Dungeon;
import thkoeln.archilab.exercises.textadventure.dungeon.DungeonException;
import thkoeln.archilab.exercises.textadventure.dungeon.Field;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonTest {
    private Dungeon dungeon;


    @Test
    public void test1by1Dungeon() {
        // given
        dungeon =  new Dungeon( 1, 1);

        // when
        Field field = dungeon.getField( 0, 0 );

        // then
        assertNull( field.getNeighbours().get( CommandType.n ) );
        assertNull( field.getNeighbours().get( CommandType.e ) );
        assertNull( field.getNeighbours().get( CommandType.s ) );
        assertNull( field.getNeighbours().get( CommandType.w ) );
    }


    @Test
    public void test2by2DungeonAt00() {
        // given
        dungeon =  new Dungeon( 2, 2);

        // when
        Field field = dungeon.getField( 0, 0 );

        // then
        assertNull( field.getNeighbours().get( CommandType.n ) );
        assertNotNull( field.getNeighbours().get( CommandType.e ) );
        assertNotNull( field.getNeighbours().get( CommandType.s ) );
        assertNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void test2by2DungeonAt01() {
        // given
        dungeon =  new Dungeon( 2, 2);

        // when
        Field field = dungeon.getField( 0, 1 );

        // then
        assertNotNull( field.getNeighbours().get( CommandType.n ) );
        assertNotNull( field.getNeighbours().get( CommandType.e ) );
        assertNull( field.getNeighbours().get( CommandType.s ) );
        assertNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void test2by2DungeonAt10() {
        // given
        dungeon =  new Dungeon( 2, 2);

        // when
        Field field = dungeon.getField( 1, 0 );

        // then
        assertNull( field.getNeighbours().get( CommandType.n ) );
        assertNull( field.getNeighbours().get( CommandType.e ) );
        assertNotNull( field.getNeighbours().get( CommandType.s ) );
        assertNotNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void test2by2DungeonAt11() {
        // given
        dungeon =  new Dungeon( 2, 2);

        // when
        Field field = dungeon.getField( 1, 1 );

        // then
        assertNotNull( field.getNeighbours().get( CommandType.n ) );
        assertNull( field.getNeighbours().get( CommandType.e ) );
        assertNull( field.getNeighbours().get( CommandType.s ) );
        assertNotNull( field.getNeighbours().get( CommandType.w ) );
    }

    @Test
    public void test3by3DungeonAtCenter() {
        // given
        dungeon =  new Dungeon( 3, 3);

        // when
        Field field = dungeon.getField( 1, 1 );

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
