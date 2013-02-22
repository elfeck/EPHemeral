package de.greenOwlProduction.ephemeral.glContext.uniform;

public interface GOPUniformObject {

	void useUniform(int programHandle, int key);
	void reset();
	void removeEntry(int key);

}
