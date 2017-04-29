package struct;

public enum Color {
    red(java.awt.Color.RED),
    blue(java.awt.Color.BLUE),
    green(java.awt.Color.GREEN),
    rainbow(java.awt.Color.WHITE);

    java.awt.Color color;

    Color(java.awt.Color c){
        color = c;
    }
    public java.awt.Color getColor(){return color;}

    boolean equals(Color c){ return this == c || this == rainbow || c == rainbow; }//a szivárvány szín mindennel ekvivalens
}
