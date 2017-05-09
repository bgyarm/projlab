package struct;

/**
 * Egy egyszer� sz�n oszt�ly 4 sz�nnel: piros, k�k, z�ld, sz�v�rv�ny
 */
public enum Color {
    red(java.awt.Color.RED),
    blue(java.awt.Color.BLUE),
    green(java.awt.Color.GREEN),
    rainbow(java.awt.Color.WHITE);

    java.awt.Color color;

    /**
     * @param c sz�n
     */
    Color(java.awt.Color c){
        color = c;
    }
    /**
     * @return sz�n
     */
    public java.awt.Color getColor(){return color;}

    /**
     * @param c m�sik sz�n
     * @return igaz, ha azonos a kett� sz�n
     */
    boolean equals(Color c){ return this == c || this == rainbow || c == rainbow; }//a sziv�rv�ny sz�n mindennel ekvivalens
}
