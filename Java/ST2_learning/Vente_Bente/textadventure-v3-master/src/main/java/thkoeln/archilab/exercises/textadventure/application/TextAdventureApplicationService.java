package thkoeln.archilab.exercises.textadventure.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import thkoeln.archilab.exercises.textadventure.creatures.*;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Impact;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Strength;
import thkoeln.archilab.exercises.textadventure.domainprimitives.TextAdventureBaseException;
import thkoeln.archilab.exercises.textadventure.dungeon.Dungeon;
import thkoeln.archilab.exercises.textadventure.dungeon.DungeonException;
import thkoeln.archilab.exercises.textadventure.dungeon.DungeonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TextAdventureApplicationService {
    @Getter
    private Dungeon dungeon;
    @Getter
    private Player player;
    @Getter
    private List<Monster> monsters = new ArrayList();

    private Random random = new Random();

    private DungeonRepository dungeonRepository;
    private MonsterRepository monsterRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public TextAdventureApplicationService(
                DungeonRepository dungeonRepository,
                MonsterRepository monsterRepository,
                PlayerRepository playerRepository ) {
        this.dungeonRepository = dungeonRepository;
        this.monsterRepository = monsterRepository;
        this.playerRepository = playerRepository;
    }

    public void initialize() {
        purgeAll();
        try {
            Integer width = 4 + random.nextInt( 6 );
            Integer height = 4 + random.nextInt( 6 );
            dungeon = new Dungeon( width, height );
            player = new Player( Strength.fromMax( 50.0f ) );
            player.place( dungeon.nextFreeCornerField() );
            placeMonsters();
            placeItems();
        }
        catch ( TextAdventureBaseException e ) {
            // can't happen
        }
        persistAll();
    }


    private void placeMonsters() throws TextAdventureBaseException {
        Monster monster = new Monster( Strength.fromMax( 50.0f ), Impact.from( -5f ) );
        monster.setName( "1-Crazy-Chicken");
        monster.place( dungeon.nextFreeCornerField() );
        monsters.add( monster );

        monster = new Monster( Strength.fromMax( 75.0f ), Impact.from( -10f ) );
        monster.setName( "2-Raging-Bull");
        monster.place( dungeon.nextFreeCornerField() );
        monsters.add( monster );

        monster = new Monster( Strength.fromMax( 100.0f ), Impact.from( -15f ) );
        monster.setName( "3-Fire-Demon");
        monster.place( dungeon.nextFreeCornerField() );
        monsters.add( monster );
    }


    private void placeItems() throws DungeonException {
        dungeon.nextFieldForItemPlacement().setItem(
                new Item( "potion", Impact.from( +40.0f ), Impact.from( 0f ), 1 )
        );
        dungeon.nextFieldForItemPlacement().setItem(
                new Item( "knife", Impact.from( 0.0f ), Impact.from( -15.0f ) )
        );
        dungeon.nextFieldForItemPlacement().setItem(
                new Item( "sword", Impact.from( 0.0f ), Impact.from( -30.0f ) )
        );
        dungeon.nextFieldForItemPlacement().setItem(
                new Item( "grenade", Impact.from( 0.0f ), Impact.from( -100.0f ), 1 )
        );
    }

    public void persistAll() {
        // first, persist the dungeon with a cascade to the fields ...
        dungeonRepository.save( dungeon );
        // ... the define the cross-references between the fields and persist again. Not elegant,
        // but the only way to avoid a wrong persistence order (leading to Hibernate exceptions)
        dungeon.defineFieldNeighbours();
        dungeonRepository.save( dungeon );

        for( Monster monster : monsters ) monsterRepository.save( monster );
        playerRepository.save( player );

    }

    public boolean isDatabasePopulated() {
        List<Dungeon> foundDungeons = (List) dungeonRepository.findAll();
        return (foundDungeons.size() > 0);
    }


    public void populateFromDataBase() {
        List<Dungeon> foundDungeons = (List) dungeonRepository.findAll();
        dungeon = foundDungeons.get( 0 );
        List<Player> foundPlayers = (List) playerRepository.findAll();
        player = foundPlayers.get( 0 );
        monsters = (List) monsterRepository.findAll();

        // The field blocking mechanism has to be called from the outside, unfortunenately.
        // The relationship Player => Field and Monster => Field is unidirectional. Therefore,
        // the "blocking" pointer in Field (pointing back to any blocker) is just transient.
        // I couldn't yet think of a better way to make sure it is set properly, then to do this
        // now after loading from DB.
        player.blockField();
        for ( Monster monster: monsters ) monster.blockField();
    }


    private void purgeAll() {
        monsterRepository.deleteAll();
        playerRepository.deleteAll();
        dungeonRepository.deleteAll();
        dungeon = null;
        player = null;
        monsters = new ArrayList<>();
    }


}
