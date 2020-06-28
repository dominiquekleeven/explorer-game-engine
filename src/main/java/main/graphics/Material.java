package main.graphics;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Material {


    private String path;
    private float width;
    private float height;
    private Texture texture;

    public Material(String path)
    {
        this.path = path;

    }

    public  void loadTexture() {


        PNGDecoder decoder = null;
        try {
            decoder = new PNGDecoder(Material.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }


        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());


        try {
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer.flip();

        int id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);


        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL_TEXTURE_2D);

        texture = new Texture(id);
    }


    public void destroy()
    {
        GL13.glDeleteTextures(texture.getId());
    }


    public int getTextureId() {
        return texture.getId();
    }
}
