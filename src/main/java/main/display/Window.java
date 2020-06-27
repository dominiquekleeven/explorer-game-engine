package main.display;

import main.input.Input;
import main.math.Matrix4f;
import main.math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.glfwInit;


public class Window {

    private int width;
    private int height;
    private String title;

    private long window;

    private Input input;
    private Vector3f background = new Vector3f(0,0,0);

    //fps
    private int frameCount;
    private long time;

    private Matrix4f projection;


    private boolean isResized;
    private boolean isFullscreen;
    private GLFWWindowSizeCallback sizeCallback;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 1000.0f);
    }

    private void fpsCounter()
    {
        frameCount++;
        if (System.currentTimeMillis() > time + 1000)
        {
            GLFW.glfwSetWindowTitle(window, title + " - FPS: " + frameCount);
            time = System.currentTimeMillis();
            frameCount = 0;
        }
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

        window = GLFW.glfwCreateWindow(width, height, title, isFullscreen ? primary : 0, 0);

        if (window == 0)
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

        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        registerCallBacks();



        var posX = (videoMode.width() - width) / 2;
        var posY = (videoMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(window, posX, posY);
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);

        time = System.currentTimeMillis();
    }


    private void registerCallBacks()
    {

        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        GLFW.glfwSetKeyCallback(window, input.getKeyboard());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtons());
        GLFW.glfwSetCursorPosCallback(window, input.getCursorMove());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
        GLFW.glfwSetScrollCallback(window, input.getMouseScroll());
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
        fpsCounter();
    }

    public void swapBuffer()
    {
        GLFW.glfwSwapBuffers(window);
    }

    public void close()
    {
        this.shouldClose();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }


    public boolean shouldClose()
    {
        return GLFW.glfwWindowShouldClose(window);
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public boolean isResized() {
        return isResized;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public void setResized(boolean resized) {
        isResized = resized;

    }

    public boolean isFullscreen() {
        isResized = true;
        return isFullscreen;
    }


}
