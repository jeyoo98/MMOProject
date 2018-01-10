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

    private Mesh meshYou;

    private GLFWKeyCallback keyCallback;

    public Window() {
        init();
        loop();
        terminate();
    }

    private void init() {
        // 16:9
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
        Texture chibiWatanabeYou = new Texture("./res/You_Chibi.png");
        // Texture chibiHanamaru = new Texture("./res/Hanamaru_Chibi.png");
        chibiWatanabeYou.bind();
        // chibiHanamaru.bind();

        int shaderProgram = ShaderLoader.createShaderProgram("res/shaders/basic.vert", "res/shaders/basic.frag");
        glUseProgram(shaderProgram);

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

        meshYou = new Mesh(vertices, indices);
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

        meshYou.bind();
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
        meshYou.unbind();

        glfwSwapBuffers(win);
    }

    private void terminate() {
        glfwDestroyWindow(win);
        Callbacks.glfwFreeCallbacks(win);
        glfwTerminate();
    }
}
