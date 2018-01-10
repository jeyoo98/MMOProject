package graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.*;

public class Mesh {

    private int vertArrObj;
    private int vertBufObj;
    private int indexBufObj;

    public Mesh(float[] interleavedVertices, byte[] indices) {
        stackPush();

        FloatBuffer verticesBuffer = stackMallocFloat(interleavedVertices.length);
        ByteBuffer indicesBuffer = stackMalloc(indices.length);

        verticesBuffer.put(interleavedVertices);
        verticesBuffer.flip();

        indicesBuffer.put(indices);
        indicesBuffer.flip();

        vertArrObj = glGenVertexArrays();
        glBindVertexArray(vertArrObj);

        vertBufObj = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertBufObj);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        // Stride: 5 floats per vertice, 4 bytes per float = 20 bytes stride
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 20, 0); // No offset
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 12); // Comes after 3 floats (xyz)
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        indexBufObj = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufObj);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        stackPop();
    }

    public void bind() {
        glBindVertexArray(vertArrObj);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufObj);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
}
