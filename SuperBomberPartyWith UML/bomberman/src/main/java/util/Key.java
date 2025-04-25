package util;

public class Key {

    public static Key up = new Key();       // Up movement
    public static Key down = new Key();     // Down movement
    public static Key left = new Key();     // Left movement
    public static Key right = new Key();    // Right movement
    public static Key action = new Key();   // Place bomb
    public static Key back = new Key(); //return to previous screen

    private boolean pressed = false;

    public void press() {
        this.pressed = true;
    }

    public void release() {
        this.pressed = false;
    }

    public boolean isPressed() {
        return this.pressed;
    }
}
