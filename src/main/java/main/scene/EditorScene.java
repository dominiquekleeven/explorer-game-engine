package main.scene;

import imgui.ImGui;
import main.math.Vector3f;
import main.objects.GameObject;
import main.utils.Cube;


public class EditorScene extends  Scene{


    private int frameCount;
    public int fps;


    @Override
    public void update() {

        this.sceneCamera.update();
        fpsCounter();
    }

    @Override
    public void sceneGui() {

       //
        ImGui.begin("Scene Editor");
        ImGui.text("Currently Editing: Main");
        ImGui.newLine();
        ImGui.text("Profiler");
        ImGui.separator();
        ImGui.text("FPS: " + fps);


        if (ImGui.button("Reset Scene"))
        {
            System.out.println("Resetting Scene");
        }



        //    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
        if (ImGui.button("Instantiate Cube"))
        {
            GameObject cube = new GameObject(new Vector3f((float) (Math.random()), (float) (Math.random()), (float) (Math.random()) * 10),
            new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), Cube.mesh);
            addSceneObject(cube);
        }

    }


    private void fpsCounter()
    {
        frameCount++;
        if (System.currentTimeMillis() > time + 1000)
        {
            time = System.currentTimeMillis();
            fps = frameCount;
            frameCount = 0;
        }
    }
}
