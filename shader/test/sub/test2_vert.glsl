/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

#version 140

in vec4 pos_model;
in vec4 col_model;

uniform mat4 mvp_matrix;
uniform vec2 offset;

out vec4 color;


void main() {
	vec4 position = pos_model * mvp_matrix;
	position[0] += offset[0];
	position[1] += offset[1];
	gl_Position = position;
	color = col_model;
}