#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;

uniform mat4 proj;
uniform mat4 view;
uniform mat4 model;

out vec2 texCoords;

void main() {
    gl_Position = proj * view * model * vec4(position, 1.0);
    texCoords = textureCoords;
}