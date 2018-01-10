package input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback{

    // Largest unsigned int
    // Can reduce size later?
    public static boolean[] keys = new boolean[65536];

    public void invoke(long window, int key, int scancode, int action, int mods) {
        // action: PRESS | RELEASE | REPEAT
        // Key down = true
        keys[key] = action != GLFW_RELEASE;
    }

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

}
