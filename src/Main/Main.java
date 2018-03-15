package Main;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import Input.Input;


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
		if (GLFW.glfwInit() == false) {System.out.println("Failed to init GLFW");}
		
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
		window = GLFW.glfwCreateWindow(width, height, "Oppe og Nikker", MemoryUtil.NULL, MemoryUtil.NULL);
		GLFW.glfwSetWindowPos(window, 250, 75); // TODO: Sentrere vinduet
		
		GLFW.glfwSetKeyCallback(window, new Input());
		
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
	}
	
	public void run()
	{
		init();
		
		while(running)
		{
			update();
			render();
			
			if(GLFW.glfwWindowShouldClose(window) == true)
			{
				running = false;
			}
		}	
	}
	
	private void update()
	{
		GLFW.glfwPollEvents();
		if(Input.keys[GLFW.GLFW_KEY_SPACE] == true)
		{
			System.out.println("Space");
		}
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


















