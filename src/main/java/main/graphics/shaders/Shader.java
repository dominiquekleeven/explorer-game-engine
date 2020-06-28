package main.graphics.shaders;

import main.math.Matrix4f;
import main.math.Vector2f;
import main.math.Vector3f;
import main.utils.FileUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Shader {

    private String vertexFile;
    private String fragmentFile;

    private int vertexId;
    private int fragmentId;
    private int programId;

    public Shader(String vertexPath, String fragmentPath) {
        this.vertexFile = FileUtil.loadAsString(vertexPath);
        this.fragmentFile = FileUtil.loadAsString(fragmentPath);
    }

    public void create() {

        programId = GL20.glCreateProgram();
        vertexId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        GL20.glShaderSource(vertexId, vertexFile);
        GL20.glCompileShader(vertexId);

        if (GL20.glGetShaderi(vertexId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("Failed to compile Vertex Shader" + GL20.glGetShaderInfoLog(vertexId));
            return;
        }


        fragmentId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentId, fragmentFile);
        GL20.glCompileShader(fragmentId);

        if (GL20.glGetShaderi(fragmentId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("Failed to compile Fragment Shader" + GL20.glGetShaderInfoLog(fragmentId));
            return;
        }

        GL20.glAttachShader(programId, vertexId);
        GL20.glAttachShader(programId, fragmentId);

        GL20.glLinkProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
        {
            System.err.println("Program Linking: " + GL20.glGetProgramInfoLog(programId));
            return;
        }
        GL20.glValidateProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE)
        {
            System.err.println("Program Validation: " + GL20.glGetProgramInfoLog(programId));
            return;
        }

        GL20.glDeleteShader(vertexId);
        GL20.glDeleteShader(fragmentId);
    }



    public int getUniformLocation(String name)
    {
        return GL20.glGetUniformLocation(programId, name);
    }

    public void setUniform(String name, float value)
    {
        GL20.glUniform1f(getUniformLocation(name), value);
    }
    public void setUniform(String name, int value)
    {
        GL20.glUniform1i(getUniformLocation(name), value);
    }
    public void setUniform(String name, boolean value)
    {
        GL20.glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }
    public void setUniform(String name, Vector2f value)
    {
        GL20.glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }
    public void setUniform(String name, Vector3f value)
    {
        GL20.glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }
    public void setUniform(String name, Matrix4f value)
    {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        matrix.put(value.getElements()).flip();
        GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }


    public void bind()
    {
        GL20.glUseProgram(programId);
    }

    public void unbind()
    {
        GL20.glUseProgram(0);
    }

    public void destroy()
    {
        GL20.glDetachShader(programId, vertexId);
        GL20.glDetachShader(programId, fragmentId);
        GL20.glDeleteShader(vertexId);
        GL20.glDeleteShader(fragmentId);
        GL20.glDeleteProgram(0);
    }
}
