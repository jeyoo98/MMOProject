package resourceHandlers;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture(String filepath) {

        ByteBuffer image;

        stackPush();

        IntBuffer widthBuffer = stackMallocInt(1);
        IntBuffer heightBuffer = stackMallocInt(1);
        IntBuffer componentsBuffer = stackMallocInt(1);

        stbi_set_flip_vertically_on_load(true);
        image = stbi_load(filepath, widthBuffer, heightBuffer, componentsBuffer, 4);

        width = widthBuffer.get();
        height = heightBuffer.get();

        stackPop();

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        glBindTexture(GL_TEXTURE_2D, 0);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
