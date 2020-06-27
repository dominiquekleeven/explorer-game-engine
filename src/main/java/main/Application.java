package main;

import main.display.Window;
import main.graphics.*;
import main.input.Input;
import main.math.Vector2f;
import main.math.Vector3f;
import main.objects.Camera;
import main.objects.GameObject;
import org.lwjgl.glfw.GLFW;

import java.util.Vector;

public class Application implements Runnable {

    private Thread mainGameThread;
    private Window window;


    public Renderer renderer;
    public Shader shader;
    public Mesh mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(1.0f, 0.0f)),
    },
            new int[]{
                    0, 1, 2,
                    0, 3, 2

    }, new Material("/textures/test-texture.jpg"));



    public GameObject object = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);

    public Camera camera = new Camera(new Vector3f(0, 0,1), new Vector3f(0, 0, 0));

    //Launch mainGameThread
    public void launch()
    {
        mainGameThread = new Thread(this, "main_game");
        mainGameThread.start();
    }


    public void stop()
    {
        window.close();
        mesh.destroy();
        shader.destroy();
        mainGameThread.interrupt();

    }


    public void run() {

        init();
        while (!window.shouldClose() && !mainGameThread.isInterrupted())
        {
            update();
            render();
        }
        this.stop();
    }

    public void init()
    {
        System.out.println("Initializing Explorer Game Engine");
        window = new Window(1280, 720, "Explorer Engine");
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        renderer = new Renderer(window, shader);
        window.setBackgroundColor(0.9f, 0.25f, 0f);
        window.create();
        mesh.create();
        shader.create();
    }



    public void update()
    {
        window.update();
        camera.update();


        //TEST
        if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
        {
            stop();
        }
        //TEST2
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_1))
        {
            System.out.println("X: " + Input.getCursorX()+ " Y: " + Input.getCursorY());
            System.out.println("Scroll X: " + Input.getScrollX()+ " Scroll Y: " + Input.getScrollY());
        }





    }


    public void render()
    {
        renderer.renderMesh(object, camera);
        window.swapBuffer();

    }



    public static void main(String[] args)
    {
        new Application().launch();
    }
}
