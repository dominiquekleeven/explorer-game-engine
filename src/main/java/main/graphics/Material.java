package main.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {


    private Texture texture;

    private String path;
    private float width;
    private float height;
    private int textureId;

    public Material(String path)
    {

        this.path = path;

    }

    public void create() {

        System.out.println("Reading file from path: " + path);
        try {
            texture = TextureLoader.getTexture(path.split("[.]")[1], Material.class.getResourceAsStream(path), GL11.GL_NEAREST);
            width = texture.getWidth();
            height = texture.getHeight();
            textureId = texture.getTextureID();
        }
        catch (NullPointerException | IOException e)
        {
            System.err.println("Failed to load texture PATH: " + path);
        }


    }

    public void destroy()
    {
        GL13.glDeleteTextures(textureId);
    }

    public Texture getTexture() {
        return texture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureId() {
        return textureId;
    }
}
