package main;

import imgui.ImGui;
import imgui.ImGuiIO;
import main.display.Window;
import main.graphics.*;
import main.input.Input;
import main.math.Vector2f;
import main.math.Vector3f;
import main.objects.Camera;
import main.objects.GameObject;
import main.scene.EditorScene;
import main.scene.Scene;
import main.utils.Cube;
import org.lwjgl.glfw.GLFW;

import java.util.Vector;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Application implements Runnable {


    private Scene currentScene;
    private Thread mainGameThread;
    private Window window;
    public static double deltaTime;
    public Renderer renderer;
    public Shader shader;






    //Launch mainGameThread
    public void launch()
    {
        mainGameThread = new Thread(this, "main_game");
        mainGameThread.start();
    }


    public void stop()
    {
        window.close();
        Cube.mesh.destroy();
        shader.destroy();
        mainGameThread.interrupt();

    }


    public void run() {

        init();
        double time = 0; // to track our frame delta value


        while (!window.shouldClose() && !mainGameThread.isInterrupted())
        {
            final double currentTime = glfwGetTime();
            deltaTime = (time > 0) ? (currentTime - time) : 1f / 60f;
            time = currentTime;
            update();
            render();
        }
        this.stop();
    }


    public void createEditorScene()
    {
        //TEMP
        GameObject object = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), Cube.mesh);
        Camera camera = new Camera(new Vector3f(0, 0,1), new Vector3f(0, 0, 0));

        System.out.println("Initializing Explorer Game Engine -- Loading EditorScene");
        Scene scene = new EditorScene();
        scene.setSceneCamera(camera);
        scene.addSceneObject(object);

        currentScene = scene;
    }

    public void init()
    {
        createEditorScene();




        window = new Window(1280, 720, "Explorer Engine", currentScene);
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        renderer = new Renderer(window, shader);
        window.setBackgroundColor(1f, 1f, 1f);
        window.create();
        Cube.mesh.create();
        shader.create();


    }



    public void update()
    {
        window.update();
        currentScene.update();
        final ImGuiIO io = ImGui.getIO();

        if (io.getKeysDown(GLFW_KEY_ESCAPE))
        {
            stop();
        }



        //Mouse States
        if(io.getMouseDown(GLFW.GLFW_MOUSE_BUTTON_1))
        {

            window.mouseState(false);
        }

        if (io.getMouseDown(GLFW.GLFW_MOUSE_BUTTON_2))
        {

            window.mouseState(true);
        }





    }


    public void render()
    {
        renderer.render(currentScene);
        window.swapBuffer();

    }



    public static void main(String[] args)
    {
        new Application().launch();
    }
}
