package main.scene;

import main.display.Window;
import main.objects.Camera;
import main.objects.GameObject;

import java.util.ArrayList;

public abstract class Scene {

    public ArrayList<GameObject> sceneObjects = new ArrayList<>();

    public Camera sceneCamera;

    public long time;

    public Scene() {
        init();
    }

    public void init() {
        time = System.currentTimeMillis();
    }


    public abstract void update();

    public abstract void sceneGui();

    public ArrayList<GameObject> getSceneObjects() {
        return sceneObjects;
    }

    public void setSceneObjects(ArrayList<GameObject> sceneObjects) {
        this.sceneObjects = sceneObjects;
    }

    public void addSceneObject(GameObject object)
    {
        sceneObjects.add(object);
    }

    public Camera getSceneCamera() {
        return sceneCamera;
    }

    public void setSceneCamera(Camera sceneCamera) {
        this.sceneCamera = sceneCamera;
    }
}
