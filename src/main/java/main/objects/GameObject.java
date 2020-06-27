package main.objects;

import main.graphics.Mesh;
import main.math.Vector3f;

public class GameObject {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private Mesh mesh;

    private double temp;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }




    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
