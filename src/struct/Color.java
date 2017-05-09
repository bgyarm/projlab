package struct;

/**
 * Egy egyszerû szín osztály 4 színnel: piros, kék, zöld, szívárvány
 */
public enum Color {
    red(java.awt.Color.RED),
    blue(java.awt.Color.BLUE),
    green(java.awt.Color.GREEN),
    rainbow(java.awt.Color.WHITE);

    java.awt.Color color;

    /**
     * @param c szín
     */
    Color(java.awt.Color c){
        color = c;
    }
    /**
     * @return szín
     */
    public java.awt.Color getColor(){return color;}

    /**
     * @param c másik szín
     * @return igaz, ha azonos a kettõ szín
     */
    boolean equals(Color c){ return this == c || this == rainbow || c == rainbow; }//a szivárvány szín mindennel ekvivalens
}
