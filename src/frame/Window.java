package frame;

import graphics.Mesh;
import resourceHandlers.ShaderLoader;
import resourceHandlers.Texture;
import input.KeyboardHandler;

import org.lwjgl.opengl.*;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.*;

public class Window {

    private final String windowTitle = "Fuck you";
    private long win;
    private int width;
    private int height;

    private Character watanabeYou;

    private GLFWKeyCallback keyCallback;

    public Window() {
        init();
        loop();
        terminate();
    }

    private void init() {
        // 16:9 Aspect ratio
        width = 1280;
        height = 720;
        keyCallback = new KeyboardHandler();

        glfwInit();

        // Make the window non-resizable
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        win = glfwCreateWindow(width, height, windowTitle, 0, 0);

        // Center the window (on the primary monitor)
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(win, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        glfwSetKeyCallback(win, keyCallback);

        // Context for OpenGL
        glfwMakeContextCurrent(win);
        glfwSwapInterval(1); // Enable V-sync
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glfwShowWindow(win);
    }

    private void test() {
        float[] vertices = {
                +0.00f, +0.00f, 0.0f, 0.0f, 0.0f, // Bottom left
                +0.20f, +0.00f, 0.0f, 1.0f, 0.0f, // Bottom right
                +0.20f, +0.40f, 0.0f, 1.0f, 1.0f, // Top right
                +0.00f, +0.40f, 0.0f, 0.0f, 1.0f  // Top left
        };

        byte[] indices = {
                0, 1, 2,
                0, 2, 3
        };


        watanabeYou = new Character("./res/You_Chibi.png", "res/shaders/basic.vert", "res/shaders/basic.frag", vertices, indices);
    }

    private void loop() {
        test();
        while (!glfwWindowShouldClose(win)){
            update();
            render();
        }
    }

    private void update() {
        // Calls the invoke method of keyCallback (if any keys were pressed)
        glfwPollEvents();

        if (KeyboardHandler.isKeyDown(GLFW_KEY_SPACE)) {
            System.out.println("Space");
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(win, true);
        }
    }

    private void render() {
        // Background colour
        // Sets all pixels to specified colour
        glClearColor(0.0f, 0.5f, 0.0f, 0.0f); // Set state
        glClear(GL_COLOR_BUFFER_BIT); // Perform action based on state (above)

        watanabeYou.draw();

        glfwSwapBuffers(win);
    }

    private void terminate() {
        glfwDestroyWindow(win);
        Callbacks.glfwFreeCallbacks(win);
        glfwTerminate();
    }
}
