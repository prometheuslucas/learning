package thkoeln.archilab.exercises.textadventure.domainprimitives;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Command {
    private CommandType commandType;
    private String itemName;

    public static Command valueOf( String inputFromCommandLine ) throws CommandException {
        if( inputFromCommandLine == null ) throw new CommandException( "Invalid command line!" );
        String[] parts = inputFromCommandLine.split("[ \t]+");
        if ( parts.length < 1 ) throw new CommandException( "Empty input!" );
        if ( parts.length > 2 ) throw new CommandException( "Max. 2 words allowed!" );
        if ( parts[0].length() != 1 ) throw new CommandException( "First word must be 1-letter command!" );

        CommandType commandType;
        try {
            commandType = CommandType.valueOf( parts[0] );
        }
        catch ( IllegalArgumentException exception ) {
            throw new CommandException( "Unknown command!" );
        }
        if ( commandType.isMoveCommand() || commandType.isMetaCommand() ) {
            return new Command( commandType, null );
        }
        else {
            if (parts.length < 2) throw new CommandException("Please say what you want to take, drop, or use.");
            return new Command(commandType, parts[1]);
        }
    }
}
