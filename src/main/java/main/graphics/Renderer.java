package main.graphics;

import main.display.Window;
import main.graphics.shaders.Shader;
import main.math.Matrix4f;
import main.objects.GameObject;
import main.scene.Scene;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {


    double temp;
    private Shader shader;
    private Window window;





    public void render(Scene scene){


        for (GameObject object: scene.getSceneObjects()
             ) {


            temp += 0.02;
            GL30.glBindVertexArray(object.getMesh().getVAO());
            GL30.glEnableVertexAttribArray(0); //positional data
            GL30.glEnableVertexAttribArray(1); //color data
            GL30.glEnableVertexAttribArray(2);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureId());

            shader.bind();
            shader.setUniform("view", Matrix4f.view(scene.getSceneCamera().getPosition(), scene.getSceneCamera().getRotation()));
            shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
            shader.setUniform("projection", window.getProjection());

            GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            shader.unbind();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL30.glDisableVertexAttribArray(0);
            GL30.glDisableVertexAttribArray(1);
            GL30.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);
        }

    }

    public Renderer(Window window, Shader shader) {

        this.shader = shader;
        this.window = window;
    }
}
