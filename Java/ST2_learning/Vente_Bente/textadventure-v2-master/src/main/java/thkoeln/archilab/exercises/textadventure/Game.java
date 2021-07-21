package thkoeln.archilab.exercises.textadventure;

import lombok.Getter;
import thkoeln.archilab.exercises.textadventure.dungeon.Dungeon;
import thkoeln.archilab.exercises.textadventure.fight.Impact;
import thkoeln.archilab.exercises.textadventure.fight.Strength;
import thkoeln.archilab.exercises.textadventure.command.Command;
import thkoeln.archilab.exercises.textadventure.command.CommandException;
import thkoeln.archilab.exercises.textadventure.creatures.Item;
import thkoeln.archilab.exercises.textadventure.creatures.Monster;
import thkoeln.archilab.exercises.textadventure.creatures.Player;
import thkoeln.archilab.exercises.textadventure.dungeon.AlreadyBlockedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    @Getter
    private Dungeon dungeon;
    @Getter
    private Player player;
    @Getter
    private List<Monster> monsters = new ArrayList();
    private Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.play();
    }

    public void initialize() {
        dungeon = new Dungeon( 5, 5);
        player = new Player( Strength.fromMax( 50.0f ) );
        try {
            player.place(dungeon.getField(0, 0));
            placeMonsters();
            placeItems();
        }
        catch ( AlreadyBlockedException alreadyBlockedException ) {
            // can't happen
        }
    }


    private void placeMonsters() throws AlreadyBlockedException {
        // monsters in all the other corners:
        Monster monster = new Monster( Strength.fromMax( 50.0f ), Impact.from( -5f ) );
        monster.setName( "1-Crazy-Chicken");
        monster.place(  dungeon.getField(0, 4 ) );
        monsters.add( monster );

        monster = new Monster( Strength.fromMax( 75.0f ), Impact.from( -10f ) );
        monster.setName( "2-Raging-Bull");
        monster.place(  dungeon.getField(4, 4 ) );
        monsters.add( monster );

        monster = new Monster( Strength.fromMax( 100.0f ), Impact.from( -15f ) );
        monster.setName( "3-Fire-Demon");
        monster.place(  dungeon.getField(4, 0 ) );
        monsters.add( monster );
    }

    private void placeItems() {
        dungeon.getField(2, 1 ).setItem(
                new Item( "potion", Impact.from( +40.0f ), Impact.from( 0f ), 1 )
        );
        dungeon.getField(2, 2 ).setItem(
                new Item( "knife", Impact.from( 0.0f ), Impact.from( -15.0f ) )
        );
        dungeon.getField(3, 3 ).setItem(
                new Item( "sword", Impact.from( 0.0f ), Impact.from( -30.0f ) )
        );
        dungeon.getField(3, 0 ).setItem(
                new Item( "grenade", Impact.from( 0.0f ), Impact.from( -100.0f ), 1 )
        );
    }


    public void play() {
        readPlayerName();

        while( !isFinished() ) {
            dungeon.print();
            executePlayerCommand();
            operateMonsters();
        }
        System.out.println( "\nThis is how it looks in the end ...\n" );
        dungeon.print();
    }


    private void readPlayerName() {
        System.out.println( "Hello! What is your name?" );
        while ( player.getName() == null ) {
            String name = scanner.nextLine();
            if ( name != null && name.length() > 0 ) {
                player.setName( name );
                System.out.println( "Hello, " + name +
                        ". Your task is to navigate the dungeon, and kill the monsters. Good luck!" );
            }
            else {
                System.out.println( "Please tell me your name ..." );
            }
        }
    }

    public boolean isFinished() {
        if ( player.isDead() ) {
            System.out.println( "Sorry, you didn't make it ... better luck next time!" );
            System.out.println( "*** GAME OVER ***" );
            return true;
        }
        boolean allMonstersDead = true;
        for ( Monster monster : monsters ) {
            if ( !monster.isDead() ) allMonstersDead = false;
        }
        if ( allMonstersDead ) {
            System.out.println( "Congratulations, you killed all monsters!" );
            System.out.println( "*** GAME OVER ***" );
            return true;
        }
        return false;
    }

    private void executePlayerCommand() {
        try {
            Command command = nextCommand();
            player.execute(command);
            if ( player.getCurrentField().containsItem() ) {
                System.out.println( "On the ground, there is a " + player.getCurrentField().getItem() + "." );
            }
            System.out.println();
        }
        catch ( CommandException commandException ) {
            System.out.println( commandException.getMessage() );
            System.out.println( "Please try again.");
        }
    }

    private Command nextCommand() throws CommandException {
        Command command = null;
        while( command == null ) {
            String line = scanner.nextLine();
            command = Command.valueOf(line);
        }
        return command;
    }

    private void operateMonsters() {
        for ( Monster monster : monsters ) {
            monster.randomWalk();
            monster.attack();
        }
    }

}
