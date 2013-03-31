/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

#version 140

in vec4 vertex_position;
in vec4 vertex_color;

uniform mat4 mvp_matrix;
uniform vec2 offset;

out vec4 frag_color;


void main() {
	vec4 position = vertex_position * mvp_matrix;
	position.xy += offset;
	gl_Position = position;
	frag_color = vertex_color;
}