package gui;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

    private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);



    public static void main(String[] args)
    {
        glfwSetErrorCallback(errorCallback);

        if(!glfwInit())
        {
            throw new IllegalStateException("Unable to initiate GLFW");
        }

        long window = glfwCreateWindow(640, 480, "Intro Tutorial", NULL, NULL);
        if(window == NULL)
        {
            glfwTerminate();
            throw new RuntimeException("Failed to Create Window");
        }

        glfwSetKeyCallback(window, (currWindow, key, scancode, action, mods) ->
                {
                    if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                    {
                        glfwSetWindowShouldClose(currWindow, true);
                    }
                }
        );
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // gui.Main Rendering Loop
        while(!glfwWindowShouldClose(window))
        {
            double time = glfwGetTime();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        glfwDestroyWindow(window);

        glfwTerminate();
        errorCallback.free();
    }
}