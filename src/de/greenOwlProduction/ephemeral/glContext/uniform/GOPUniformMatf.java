package de.greenOwlProduction.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;
import de.greenOwlProduction.ephemeral.math.GOPMatf;

import java.util.HashMap;
import java.util.Map;


public class GOPUniformMatf implements GOPUniformObject {

	private int index;
	private String name;
	private Map<Integer, GOPMatf> entries;

	public GOPUniformMatf(String name) {
		index = -1;
		this.name = name;
		entries = new HashMap<Integer, GOPMatf>();
	}

	@Override
	public void useUniform(int programHandle, int key) {
		if (index < 0) index = glGetUniformLocation(programHandle, name);
		GOPMatf matrix = entries.get(key);
		if (matrix != null) {
			switch (matrix.getDimension()) {
				case 2:
					glUniformMatrix2(index, false, matrix.toBuffer());
					break;
				case 3:
					glUniformMatrix3(index, false, matrix.toBuffer());
					break;
				case 4:
					glUniformMatrix4(index, false, matrix.toBuffer());
					break;
				default:
					System.err.println("Error in GOPUniformxfm. Size out of valid range");
			}
		}
	}

	@Override
	public void reset() {
		index = -1;
	}

	@Override
	public void removeEntry(int key) {
		entries.remove(key);
	}

	public void addEntry(int key, GOPMatf matrix) {
		entries.put(key, matrix);
	}

}
