package game;

import java.awt.Color;

/**
 * colors parser class.
 */
public class ColorsParser {

    /**
     * parse color definition and return the specified color.
     *
     * @param s string
     * @return color
     */
    public java.awt.Color colorFromString(String s) {
        Color color;
        if (s.startsWith(("color(RGB(")) && (s.endsWith("))"))) {
            String string = s.substring("color(RGB(".length(), s.length() - "))".length());
            String[] numbers = string.split(",");
            return new Color(Integer.parseInt(numbers[0].trim()), Integer.parseInt(numbers[1].trim()),
                    Integer.parseInt(numbers[2].trim()));
        } else if ((s.startsWith("color(")) && (s.endsWith(")"))) {
            String string = s.substring("color(".length(), s.length() - ")".length());
            try {
                color = (Color) Color.class.getField(string).get(null);
            } catch (java.lang.ReflectiveOperationException ex) {
                throw new RuntimeException("Problem with the color's name");
            }
            return color;
        }
        return null;
    }
}
