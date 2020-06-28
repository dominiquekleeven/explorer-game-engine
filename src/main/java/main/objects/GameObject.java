package main.objects;

import main.graphics.Mesh;
import main.math.Vector3f;

import java.util.Objects;

public class GameObject {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private Mesh mesh;
    private boolean isRendered;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return Objects.equals(mesh, that.mesh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mesh);
    }

    public boolean isRendered() {
        return isRendered;
    }

    public void setRendered(boolean rendered) {
        isRendered = rendered;
    }
}
