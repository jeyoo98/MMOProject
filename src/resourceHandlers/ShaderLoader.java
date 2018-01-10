package resourceHandlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderLoader {
    private ShaderLoader() {}

    public static int createShaderProgram(String vertPath, String fragPath) {
        int program = glCreateProgram();

        int vert = createShader(vertPath, GL_VERTEX_SHADER);
        int frag = createShader(fragPath, GL_FRAGMENT_SHADER);

        glAttachShader(program, vert);
        glAttachShader(program, frag);

        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vert);
        glDeleteShader(frag);

        return program;
    }

    private static String readFromFile(String path) {
        String retVal;
        try {
            retVal = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            retVal = null;
            e.printStackTrace();
        }

        return retVal;
    }

    private static int createShader(String path, int type) {
        int shader;
        String source;

        source = readFromFile(path);
        shader = glCreateShader(type);
        glShaderSource(shader, source);

        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile " + path);
            System.err.println(glGetShaderInfoLog(shader));
        }

        return shader;
    }
}
