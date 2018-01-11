package frame;

import input.KeyboardHandler;

import static org.lwjgl.glfw.GLFW.*;

public class MainCharacter extends Character {
    public MainCharacter(String texturePath, String vertPath, String fragPath) {
        super(texturePath, vertPath, fragPath);
    }

    public void update() {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            move(0.0f, 0.1f, 0.0f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
            move(-0.1f, 0.0f, 0.0f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            move(0.0f, -0.1f, 0.0f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
            move(0.1f, 0.0f, 0.0f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_Q)) {
            rotate(-5.0f);
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_E)) {
            rotate(5.0f);
        }
    }
}
