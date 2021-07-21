package thkoeln.archilab.exercises.textadventure.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.archilab.exercises.textadventure.creatures.Monster;
import thkoeln.archilab.exercises.textadventure.domainprimitives.Command;
import thkoeln.archilab.exercises.textadventure.domainprimitives.CommandException;
import thkoeln.archilab.exercises.textadventure.domainprimitives.CommandType;

import javax.transaction.Transactional;
import java.util.Scanner;

@Component
public class Game {
    private Scanner scanner = new Scanner(System.in);

    private TextAdventureApplicationService service;

    @Autowired
    public Game( TextAdventureApplicationService service ) {
        this.service = service;
    }

    public void play() {
        if ( service.isDatabasePopulated() ) service.populateFromDataBase();
        else service.initialize();
        introduce();

        while( !isFinished() ) {
            service.getDungeon().print();
            executeCommand();
            service.persistAll();
        }
        System.out.println( "\nThis is how it looks in the end ...\n" );
        service.getDungeon().print();
    }


    private void introduce() {
        System.out.println( "\n\n" );
        System.out.println( "Hello! What is your name?" );
        readPlayerName();
        System.out.println( "Hello, " + service.getPlayer().getName() +
                ". Your task is to navigate the dungeon, and kill the monsters. Good luck!" );
        System.out.println( "You can use the following commands: " );
        System.out.println( " - n|e|s|w will move you on the dungeon." );
        System.out.println( " - t|d|u allows you to take, drop, or use an item." );
        System.out.println( " - r will restart the game, and q allows you to quit." );
        System.out.println( "Good luck!\n" );
    }

    private void readPlayerName() {
        while ( service.getPlayer().getName() == null ) {
            String name = scanner.nextLine();
            if ( name != null && name.length() > 0 ) {
                service.getPlayer().setName( name );
            }
            else {
                System.out.println( "Please tell me your name ..." );
            }
        }
    }

    public boolean isFinished() {
        if ( service.getPlayer().isDead() ) {
            System.out.println( "Sorry, you didn't make it ... better luck next time!" );
            System.out.println( "*** GAME OVER ***" );
            return true;
        }
        boolean allMonstersDead = true;
        for ( Monster monster : service.getMonsters() ) {
            if ( !monster.isDead() ) allMonstersDead = false;
        }
        if ( allMonstersDead ) {
            System.out.println( "Congratulations, you killed all monsters!" );
            System.out.println( "*** GAME OVER ***" );
            return true;
        }
        return false;
    }

    private void executeCommand() {
        try {
            Command command = nextCommand();
            if ( command.getCommandType().isPlayerCommand() ) {
                service.getPlayer().execute(command);
                if ( service.getPlayer().getCurrentField().containsItem() ) {
                    System.out.println("On the ground, there is a " + service.getPlayer().getCurrentField().getItem() + ".");
                }
                operateMonsters();
            }
            else {
                quitOrRestart( command );
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
        for ( Monster monster : service.getMonsters() ) {
            monster.randomWalk();
            monster.attack();
        }
    }

    private void quitOrRestart( Command command ) {
        if ( command.getCommandType() == CommandType.r ) {
            System.out.println( "Restarting the quest ...");
            service.initialize();
        }
        else if ( command.getCommandType() == CommandType.q ) {
            System.out.println( "Good bye!\n\n");
            System.exit( 0 );
        }
    }
}
