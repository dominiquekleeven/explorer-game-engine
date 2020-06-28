package main.graphics;

import main.math.Vector2f;
import main.math.Vector3f;

public class Vertex {
    private Vector3f position;

    private Vector3f normal;

    private Vector2f textureCoord;

    public Vertex(Vector3f position, Vector3f normal, Vector2f textureCoord) {
        this.position = position;
        this.normal = normal;
        this.textureCoord = textureCoord;
    }

    public Vertex(Vector3f position, Vector2f textureCoord) {
        this.position = position;

        this.textureCoord = textureCoord;
    }

    public Vector3f getPosition() {
        return position;
    }



    public void setPosition(Vector3f position) {
        this.position = position;
    }



    public Vector2f getTextureCoord() {
        return textureCoord;
    }

    public void setTextureCoord(Vector2f textureCoord) {
        this.textureCoord = textureCoord;
    }
}
