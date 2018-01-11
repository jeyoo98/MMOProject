package frame;

import graphics.Mesh;
import math.Matrix4f;
import math.Vector3f;
import resourceHandlers.Texture;
import resourceHandlers.ShaderLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;


public class Character {

    private float height;
    private float width;

    private Texture texture;
    private int shaderProgram;
    private Mesh mesh;
    private Vector3f position;

    private Matrix4f model;
    private Matrix4f view;
    private Matrix4f proj;

    private int modelUniform;
    private int viewUniform;
    private int projUniform;

    public Character(String texturePath, String vertPath, String fragPath) {
        texture = new Texture(texturePath);
        height = (float) texture.getHeight();
        width = (float) texture.getWidth();

        float[] vertices = {
                +0.00f, +0.00f, 0.0f, 0.0f, 0.0f, // Bottom left
                 width, +0.00f, 0.0f, 1.0f, 0.0f, // Bottom right
                 width, height, 0.0f, 1.0f, 1.0f, // Top right
                +0.00f, height, 0.0f, 0.0f, 1.0f  // Top left
        };

        byte[] indices = {
                0, 1, 2,
                0, 2, 3
        };

        shaderProgram = ShaderLoader.createShaderProgram(vertPath, fragPath);
        mesh = new Mesh(vertices, indices);
        position = new Vector3f();

        model = Matrix4f.identity();
        view = Matrix4f.identity();
        proj = Matrix4f.orthographic(-16.0f, 16.0f, 9.0f, -9.0f, 1.0f, -1.0f);

        modelUniform = glGetUniformLocation(shaderProgram, "model");
        viewUniform = glGetUniformLocation(shaderProgram, "view");
        projUniform = glGetUniformLocation(shaderProgram, "proj");
    }

    public void draw() {
        bind();
        setUniformMatrices();
        glDrawElements(GL_TRIANGLES, mesh.getVerticeCount(), GL_UNSIGNED_BYTE, 0);
        unbind();
    }

    public void move(float deltaX, float deltaY, float deltaZ) {
        position.x += deltaX;
        position.y += deltaY;
        position.z += deltaZ;
    }

    private void bind() {
        glUseProgram(shaderProgram);
        texture.bind();
        mesh.bind();
    }

    private void unbind() {
        glUseProgram(0);
        texture.unbind();
        mesh.unbind();
    }

    private void setUniformMatrices() {
        glUniformMatrix4fv(modelUniform, true, Matrix4f.translate(position).multiply(Matrix4f.scale(0.03f, 0.03f, 0.03f)).matrix);
        glUniformMatrix4fv(viewUniform, true, view.matrix);
        glUniformMatrix4fv(projUniform, true, proj.matrix);
    }
}
