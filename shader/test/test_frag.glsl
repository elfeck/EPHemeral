/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

#version 140

in vec4 color;

void main() {
	gl_FragColor = vec4(color[0], color[1], color[2], color[3]);
}