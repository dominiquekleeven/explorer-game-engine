package main.display;

import main.Application;
import main.input.Input;
import main.math.Matrix4f;
import main.math.Vector3f;
import main.scene.Scene;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;


public class Window {

    private int width;
    private int height;
    private String title;
    public static long windowPtr;
    private Input input;
    private Vector3f background = new Vector3f(0,0,0);


    private Matrix4f projection;
    private GuiLayer guiLayer;
    private Scene currentScene;


    public static void mouseState(boolean lock) {
        GLFW.glfwSetInputMode(windowPtr, GLFW_CURSOR, lock ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
    }

    private boolean isResized;
    private GLFWWindowSizeCallback sizeCallback;

    public Window(int width, int height, String title, Scene scene) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.currentScene = scene;
        projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 1000.0f);
    }


    public void create()
    {
        if (!glfwInit())
        {
            System.err.println("ERROR: Failed to initialize GLFW");
            return;
        }
        input = new Input();
        long primary = GLFW.glfwGetPrimaryMonitor();
        windowPtr = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (windowPtr == 0)
        {
            System.err.println("ERROR: Failed to initialize Window");
            return;
        }
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(primary);
        if (videoMode == null)
        {
            System.err.println("ERROR: Failed to get GLFW Video Mode");
            return;
        }

        GLFW.glfwMakeContextCurrent(windowPtr);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);


        registerCallBacks();
        this.guiLayer = new GuiLayer(this);
        this.guiLayer.init();






        var posX = (videoMode.width() - width) / 2;
        var posY = (videoMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(windowPtr, posX, posY);
        GLFW.glfwShowWindow(windowPtr);
        GLFW.glfwSwapInterval(1);



    }


    private void registerCallBacks()
    {
        GLFW.glfwSetKeyCallback(windowPtr, input.getKeyboard());
        GLFW.glfwSetMouseButtonCallback(windowPtr, input.getMouseButtons());
        GLFW.glfwSetCursorPosCallback(windowPtr, input.getCursorMove());
        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        GLFW.glfwSetWindowSizeCallback(windowPtr, sizeCallback);
        GLFW.glfwSetScrollCallback(windowPtr, input.getMouseScroll());
    }


    public void setBackgroundColor(float r, float g, float b)
    {
       background.set(r, g, b);
    }


    private void updateViewPort()
    {
        if (isResized)
        {
            GL11.glViewport(0, 0, width, height);
            isResized = false;
        }

    }


    public void update() {

        updateViewPort();
        GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
    }

    public void swapBuffer()
    {
        this.guiLayer.update((float) Application.deltaTime, currentScene);
        GLFW.glfwSwapBuffers(windowPtr);
    }

    public void close()
    {
        this.shouldClose();
        GLFW.glfwDestroyWindow(windowPtr);
        GLFW.glfwTerminate();
    }


    public boolean shouldClose()
    {
        return GLFW.glfwWindowShouldClose(windowPtr);
    }



    public Matrix4f getProjection() {
        return projection;
    }


    public long getWindow() {
        return windowPtr;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
