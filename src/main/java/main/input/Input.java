package main.input;

import org.lwjgl.glfw.*;

public class Input {


    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private static double mouseX;
    private static double mouseY;

    private static double scrollX;
    private static double scrollY;


    private static GLFWKeyCallback keyboard;
    private static GLFWCursorPosCallback cursorMove;
    private static GLFWMouseButtonCallback mouseButtons;
    private GLFWScrollCallback mouseScroll;


    public Input() {



        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);

            }
        };


        cursorMove = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double x, double y) {
                mouseX = x;
                mouseY = y;

            }
        };


        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };


        mouseScroll = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double offsetX, double offsetY) {
                scrollX += offsetX;
                scrollY += offsetY;
            }
        };




    }


    public static boolean isKeyDown(int key)
    {
        return keys[key];
    }

    public static boolean isButtonDown(int button)
    {
        return buttons[button];
    }

    public static boolean[] getKeys() {
        return keys;
    }

    public static boolean[] getButtons() {
        return buttons;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public GLFWKeyCallback getKeyboard() {
        return keyboard;
    }

    public GLFWCursorPosCallback getCursorMove() {
        return cursorMove;
    }

    public GLFWMouseButtonCallback getMouseButtons() {
        return mouseButtons;
    }



    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }

    public GLFWScrollCallback getMouseScroll() {
        return mouseScroll;
    }
}
