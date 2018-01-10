package frame;

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

    private int vaoYou, ibo;

    private GLFWKeyCallback keyCallback;

    public Window() {
        init();
        loop();
        terminate();
    }

    private void init() {
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

        stackPush();

        int shaderProgram = ShaderLoader.createShaderProgram("res/shaders/basic.vert", "res/shaders/basic.frag");
        glUseProgram(shaderProgram);

        FloatBuffer verticesBuffer = stackMallocFloat(4 * 5);
        ByteBuffer indicesBuffer = stackMalloc(2 * 3);

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

        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        indicesBuffer.put(indices);
        indicesBuffer.flip();

        int vertArrObj = glGenVertexArrays();
        glBindVertexArray(vertArrObj);

        int vertBufObj = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertBufObj);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 3 * 4);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        int indexBufObj = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufObj);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        vaoYou = vertArrObj;
        ibo = indexBufObj;

        stackPop();
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

        glBindVertexArray(vaoYou);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glfwSwapBuffers(win);
    }

    private void terminate() {
        glfwDestroyWindow(win);
        Callbacks.glfwFreeCallbacks(win);
        glfwTerminate();
    }
}
