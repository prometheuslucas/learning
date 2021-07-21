package thkoeln.st.st2praktikum.exercise.domainPrimitives;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Coordinates2D {

    private Integer x;
    private Integer y;


    public Coordinates2D(Integer x, Integer y) throws InvalidInputException {
        if (!validatePositiveCoordinateInput(x, y)) {
            throw new InvalidInputException("Coordinates are below 0. Negative Coordinates are not permitted.");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param Coordinates2DString the Coordinates2D in form of a string e.g. (1,2)
     */
    public static Coordinates2D fromString(String Coordinates2DString) throws InvalidInputException {
        if (!validateCoordinateStringFormatting(Coordinates2DString)) {
            throw new InvalidInputException("String formatting is invalid");
        }
        Pair<Integer, Integer> coordinatePair = parseStringToCoordinates(Coordinates2DString);
        return new Coordinates2D(coordinatePair.getLeft(), coordinatePair.getRight());
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    private Boolean validatePositiveCoordinateInput(Integer x, Integer y) {
        return (x >= 0 && y >= 0);
    }

    private static Boolean validateCoordinateStringFormatting(String Coordinates2DString) {
        return Coordinates2DString.matches("\\(\\d+,\\d\\)");
    }

    private static Pair<Integer, Integer> parseStringToCoordinates(String Coordinates2DString) {
        Coordinates2DString = Coordinates2DString.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] coordinateSplit = Coordinates2DString.split(",");
        return new Pair(Integer.parseInt(coordinateSplit[0]), Integer.parseInt(coordinateSplit[1]));
    }
}
