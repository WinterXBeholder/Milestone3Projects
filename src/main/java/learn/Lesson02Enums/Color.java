package learn.Lesson02Enums;

public enum Color {
    // Instantiation must be the first thing in the definition.
    RED("ff0000", "Rojo"),
    BLUE("0000ff", "Azul"),
    YELLOW("ffff00", "Amarillo"),
    GREEN("00ff00", "Verde"),
    ORANGE("ffa500", "Naranja"),
    PURPLE("a020f0", "Morado");

    // Fields are allowed
    private final String hexValue;
    private final String spanishName;

    // can have constructors
    Color(String hexValue, String spanishName) {
        this.hexValue = hexValue;
        this.spanishName = spanishName;
    }

    // Getters
    public String getHexValue() {return hexValue;}
    public String getSpanishName() {return spanishName;}

    // Returns the traditional compliment of this Color.
    public Color getTraditionalCompliment() {
        switch (this) {
            case RED:
                return GREEN;
            case BLUE:
                return ORANGE;
            case YELLOW:
                return PURPLE;
            case GREEN:
                return RED;
            case ORANGE:
                return BLUE;
            case PURPLE:
                return YELLOW;
        }
        return RED; // Required -- the compiler is paranoid.
    }

    public static Color findByHex(String hexValue) {
        for (Color color : Color.values()) { // all Enums have .value() methods. Returns an array of this
                    // enumerator type's values. The type (or classname) in here is Color.
            if (color.getHexValue().equalsIgnoreCase(hexValue)) {
                return color;
            }
        }
        String message = String.format("No Color with hex value %s", hexValue);
        throw new RuntimeException(message); // we can write this at the end because if we had found a
                    // a color, then it would have returned mid-loop instead of making it this far.
    }



}
