package struct;

public enum Color {
    red,
    blue,
    green,
    rainbow;

    boolean equals(Color c){ return this == c || this == rainbow || c == rainbow; }//a szivárvány szín mindennel ekvivalens
}
