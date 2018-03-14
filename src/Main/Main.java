package Main;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;


public class Main implements Runnable {

	private int width = 800;
	private int height = 600;
	private boolean running = false;
	
	private Thread thread;
	
	private long window;
	
	public void start()
	{
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	private void init()
	{
		running = true;
		GLFW.glfwInit();
		
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
		window = GLFW.glfwCreateWindow(width, height, "Oppe og Nikker", glfwGetPrimaryMonitor(), MemoryUtil.NULL);
	
		GLFW.glfwSetWindowPos(window, 200, 200);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		System.out.println(window);
	}
	
	public void run()
	{
		init();
		
		
		while(running)
		{
			update();
			render();
			
			/*
			if(GLFW.glfwWindowShouldClose(window) == true)
			{
				running = false;
			{*/
		}
		
		
	}
	
	private void update()
	{
		GLFW.glfwSwapBuffers(window);
	}
	private void render()
	{
		GLFW.glfwSwapBuffers(window);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}


















