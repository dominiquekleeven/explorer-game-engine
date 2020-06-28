package main.objects;

import imgui.ImGui;
import imgui.ImGuiIO;
import main.display.Window;
import main.input.Input;
import main.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;
    private float moveSpeed = 0.05f;
    private float mouseSensitivity = 0.1f;

    private double oldMouseX = 0;
    private double oldMouseY = 0;
    private double newMouseX;
    private double newMouseY;

    private float distance = 2.0f;
    private float horizontalAngle = 0;
    private float verticalAngle = 0;

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }


    private void movement()
    {



            final ImGuiIO io = ImGui.getIO();
            float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
            float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

            if (io.getKeysDown(GLFW.GLFW_KEY_A))
            {
                position = Vector3f.add(position, new Vector3f(-z, 0, x));
            }
            if (io.getKeysDown(GLFW.GLFW_KEY_D))
            {
                position = Vector3f.add(position, new Vector3f(z, 0, -x));
            }
            if (io.getKeysDown(GLFW.GLFW_KEY_W))
            {
                position = Vector3f.add(position, new Vector3f(-x, 0, -z));
            }
            if (io.getKeysDown(GLFW.GLFW_KEY_S))
            {
                position = Vector3f.add(position, new Vector3f(x, 0, z));
            }

            if (io.getKeysDown(GLFW.GLFW_KEY_SPACE))
            {
                position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));
            }

            if (io.getKeysDown(GLFW.GLFW_KEY_LEFT_SHIFT))
            {
                position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));
            }
        }





    public void update() {


        var input = GLFW.glfwGetInputMode(Window.windowPtr, GLFW.GLFW_CURSOR);

        if (input != GLFW.GLFW_CURSOR_NORMAL)
        {
            movement();
            mouseMovement();
        }

    }


//    public void update(GameObject focus)
//    {
//        newMouseX = Input.getMouseX();
//        newMouseY = Input.getMouseY();
//
//        float dx = (float) (newMouseX - oldMouseX);
//        float dy = (float) (newMouseY - oldMouseY);
//
//        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
//            verticalAngle -= dy * mouseSensitivity;
//            horizontalAngle += dx * mouseSensitivity;
//        }
//        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
//            if (distance > 0) {
//                distance += dy * mouseSensitivity / 4;
//            } else {
//                distance = 0.1f;
//            }
//        }
//
//        float horizontalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle)));
//        float verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle)));
//
//        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
//        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));
//
//        position.set(focus.getPosition().getX() + xOffset, focus.getPosition().getY() - verticalDistance, focus.getPosition().getZ() + zOffset);
//
//        rotation.set(verticalAngle, -horizontalAngle, 0);
//
//        oldMouseX = newMouseX;
//        oldMouseY = newMouseY;
//    }


    public void mouseMovement()
    {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();
        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);
        rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0)); //flipped xy for cursor movement
        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
