package frame;

import input.KeyboardHandler;

import org.lwjgl.opengl.*;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {

    private final String windowTitle = "Fuck you";
    private long win;
    private int width;
    private int height;

    private MainCharacter watanabeYou;
    private Character zuramaru;

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
        glClearColor(0.0f, 0.5f, 0.0f, 0.0f);
        glfwShowWindow(win);
    }

    private void test() {
        watanabeYou = new MainCharacter("./res/You_Chibi.png", "res/shaders/basic.vert", "res/shaders/basic.frag");
        zuramaru = new Character("./res/Hanamaru_Chibi.png", "res/shaders/basic.vert", "res/shaders/basic.frag");
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

        watanabeYou.update();

        if (KeyboardHandler.isKeyDown(GLFW_KEY_SPACE)) {
            System.out.println("Space");
        }

        if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(win, true);
        }
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT);

        watanabeYou.draw();
        zuramaru.draw();

        glfwSwapBuffers(win);
    }

    private void terminate() {
        glfwDestroyWindow(win);
        Callbacks.glfwFreeCallbacks(win);
        glfwTerminate();
    }
}
