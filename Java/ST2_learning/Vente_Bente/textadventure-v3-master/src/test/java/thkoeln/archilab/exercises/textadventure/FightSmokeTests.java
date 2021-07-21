package thkoeln.archilab.exercises.textadventure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.archilab.exercises.textadventure.domainprimitives.*;
import thkoeln.archilab.exercises.textadventure.creatures.Item;
import thkoeln.archilab.exercises.textadventure.creatures.Monster;
import thkoeln.archilab.exercises.textadventure.creatures.Player;
import thkoeln.archilab.exercises.textadventure.dungeon.AlreadyBlockedException;
import thkoeln.archilab.exercises.textadventure.dungeon.Dungeon;

import static org.junit.jupiter.api.Assertions.*;

public class FightSmokeTests {
    protected Monster monster1, monster2, monster3;
    protected Player player;
    protected Dungeon dungeon;
    protected static final Command TAKE_SWORD = new Command( CommandType.t, "sword" );
    protected static final Command USE_SWORD = new Command( CommandType.u, "sword" );

    @BeforeEach
    public void setUp() throws Exception {
        dungeon =  new Dungeon( 5, 5);
        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        player = new Player( Strength.fromMax( 50.0f ) );
        player.setName( "TestPlayer" );

        monster1 = new Monster( Strength.fromMax( 50.0f ), Impact.from( -5f ) );
        monster1.setName( "1-Crazy-Chicken");
        monster2 = new Monster( Strength.fromMax( 75.0f ), Impact.from( -10f ) );
        monster2.setName( "2-Raging-Bull");
        monster3 = new Monster( Strength.fromMax( 100.0f ), Impact.from( -15f ) );
        monster3.setName( "3-Fire-Demon");
    }

    @Test
    public void testPlayerMonsterFightToDeath() throws Exception {
        // given: player and monster3 in one corner
        player.place( dungeon.getField( Coordinate.fromInteger(0, 0 ) ) );
        monster3.place( dungeon.getField( Coordinate.fromInteger(0, 1 ) ) );

        // when: player has sword, and there is fight until death: player attacks monster3 / then monster3 attacks player
        dungeon.getField( Coordinate.fromInteger(0, 0 ) ).setItem( new Item( "sword", Impact.from( 0f ), Impact.from( -30f ) ) );
        player.execute( TAKE_SWORD );
        while ( !player.isDead() && !monster3.isDead() ) {
            player.execute( USE_SWORD );
            monster3.attack();
        }

        // then: player nearly dead with strength 5, monster3 dead
        assertEquals( Strength.fromCurrentAndMax( 5f, 50f ), player.getStrength() );
        assertTrue( monster3.isDead(), "Monster 3 dead" );
    }


    @Test
    public void testThreeMonsterFightToDeath() throws Exception {
        // given: three monsters in a corner, like this:
        //  1 3
        //    2
        monster1.place( dungeon.getField( Coordinate.fromInteger(3, 0 ) ) );
        monster2.place( dungeon.getField( Coordinate.fromInteger(4, 1 ) ) );
        monster3.place( dungeon.getField( Coordinate.fromInteger(4, 0 ) ) );

        // when: all three monsters attack each other in the order 1-2-3, until the first one is dead
        while ( !monster1.isDead() && !monster2.isDead() && !monster3.isDead() ) {
            monster1.attack();
            monster2.attack();
            monster3.attack();
        }

        /* then: it should have the following strength table
               1   2    3
              ===========
              50  75  100
              50  75   95
              50  75   85
              35  60   80
              35  60   70
              20  45   70
              20  45   65
              20  45   55
               5  30   50
               5  30   40
               0  15   40
        */

        assertTrue( monster1.isDead(), "Monster 1 dead" );
        assertFalse( monster2.isDead(), "Monster 2 not dead" );
        assertFalse( monster3.isDead(), "Monster 2 not dead" );
        assertEquals( Strength.fromCurrentAndMax( 15f, 75f ), monster2.getStrength() );
        assertEquals( Strength.fromCurrentAndMax( 40f, 100f ), monster3.getStrength() );
    }

}
