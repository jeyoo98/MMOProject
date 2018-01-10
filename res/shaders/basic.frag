#version 330 core

out vec4 colour;

in vec2 texCoords;

uniform sampler2D tex;

void main() {
    colour = texture(tex, texCoords);
    if (colour.w < 0.1) {
        discard;
    }
}