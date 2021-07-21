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

/**
 * Base tests for the fighting between player and monsters, and in between monsters.
 * Intended to work towards a maximum test coverage.
 */
public class FightCoverageTests {
    protected Monster monster1, monster2;
    protected Player player;
    protected Dungeon dungeon;
    protected static final Command TAKE_SWORD = new Command( CommandType.t, "sword" );
    protected static final Command USE_SWORD = new Command( CommandType.u, "sword" );

    @BeforeEach
    public void setUp() throws Exception {
        dungeon =  new Dungeon( 5, 5);
        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        // todo this needs to be urgently refactored
        dungeon.defineFieldNeighbours();

        player = new Player( Strength.fromMax( 50.0f ) );
        player.setName( "TestPlayer" );

        monster1 = new Monster( Strength.fromMax( 50.0f ), Impact.from( -5f ) );
        monster1.setName( "1-Crazy-Chicken");
        monster2 = new Monster( Strength.fromMax( 75.0f ), Impact.from( -10f ) );
        monster2.setName( "2-Raging-Bull");
    }

    @Test
    public void testPlayerMonsterAttack() throws Exception {
        // given: player and monster3 in one corner
        Coordinate coord00 = Coordinate.fromInteger( 0, 0 );
        player.place( dungeon.getField( coord00 ) );
        monster1.place( dungeon.getField( Coordinate.fromInteger( 0, 1 ) ) );

        // when: player has sword and uses it, and monster attacks back
        dungeon.getField( coord00 ).setItem( new Item( "sword", Impact.from( 0f ), Impact.from( -30f ) ) );
        player.execute( TAKE_SWORD );
        player.execute( USE_SWORD );
        monster1.attack();

        // then
        assertEquals( Strength.fromCurrentAndMax( 45f, 50f ), player.getStrength(),
               "Player has strength mismatch after fight" );
        assertFalse( player.isDead(), "Player shouldn't be dead" );
        assertEquals( Strength.fromCurrentAndMax( 20f, 50f ), monster1.getStrength(),
                "Monster1 has strength mismatch after fight" );
        assertFalse( monster1.isDead(), "Monster1 shouldn't be dead" );
    }


    @Test
    public void testTwoMonsterAttack() throws Exception {
        // given: two monsters in a corner
        monster1.place( dungeon.getField( Coordinate.fromInteger(3, 0 ) ) );
        monster2.place( dungeon.getField( Coordinate.fromInteger(4, 0 ) ) );

        // when
        monster1.attack();
        monster2.attack();

        // then
        assertEquals( Strength.fromCurrentAndMax( 40f, 50f ), monster1.getStrength() );
        assertFalse( monster1.isDead() );
        assertEquals( Strength.fromCurrentAndMax( 70f, 75f ), monster2.getStrength() );
        assertFalse( monster2.isDead() );
    }

}
