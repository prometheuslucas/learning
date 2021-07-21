package thkoeln.archilab.exercises.textadventure.creatures;

public abstract class ColorGradientPrinter {
    private static final String RED_BOLD_BRIGHT = "\033[1;91m";
    private static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    private static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    private static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    private static final String MAGENTA_BOLD_BRIGHT = "\033[1;95m";
    private static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
    private static final String BLACK_BACKGROUND = "\033[40m\033[1;97m";
    private static final String RESET = "\033[0m";

    /**
     * Print a given text in a gradient fashion, indicating "strength"
     */
    public static void colorPrint( String text, float strengthBetween0and1 ) {
        String formatString;
        if( strengthBetween0and1 > (5.0f / 6.0f) ) formatString = BLUE_BOLD_BRIGHT;
        else if( strengthBetween0and1 > (4.0f / 6.0f) ) formatString = CYAN_BOLD_BRIGHT;
        else if( strengthBetween0and1 > (3.0f / 6.0f) ) formatString = GREEN_BOLD_BRIGHT;
        else if( strengthBetween0and1 > (2.0f / 6.0f) ) formatString = YELLOW_BOLD_BRIGHT;
        else if( strengthBetween0and1 > (1.0f / 6.0f) ) formatString = RED_BOLD_BRIGHT;
        else if( strengthBetween0and1 > 0 ) formatString = MAGENTA_BOLD_BRIGHT;
        else formatString = BLACK_BACKGROUND;

        System.out.print( formatString + text + RESET );
    }
}
