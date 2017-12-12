package frame;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    public Window() {
        glfwInit();

        long win = glfwCreateWindow(640, 480, "Window", 0, 0);

        glfwShowWindow(win);

        while (!glfwWindowShouldClose(win)){
            glfwPollEvents();
        }
    }
}
