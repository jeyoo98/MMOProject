package frame;

import graphics.Mesh;
import math.Vector3f;
import resourceHandlers.Texture;
import resourceHandlers.ShaderLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class Character {

    private int height;
    private int width;

    private Texture texture;
    private int shaderProgram;
    private Mesh mesh;
    private Vector3f position;

    public Character(String texturePath, String vertPath, String fragPath, float[] interleavedVertices, byte[] indices) {
        texture = new Texture(texturePath);
        shaderProgram = ShaderLoader.createShaderProgram(vertPath, fragPath);
        mesh = new Mesh(interleavedVertices, indices);
        position = new Vector3f();
    }

    public void draw() {
        bind();
        glDrawElements(GL_TRIANGLES, mesh.getVerticeCount(), GL_UNSIGNED_BYTE, 0);
        unbind();
    }

    public void bind() {
        glUseProgram(shaderProgram);
        texture.bind();
        mesh.bind();
    }

    public void unbind() {
        glUseProgram(0);
        texture.unbind();
        mesh.unbind();
    }
}
