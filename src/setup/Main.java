package setup;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import input.Input;
import graphics.Texture;


public class Main implements Runnable {

	private int width = 800;
	private int height = 600;
	private boolean running = false;
	
	private Thread thread;
	private Texture floor;
	private Texture floor2;
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
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GLFW.glfwShowWindow(window);
		GL11.glClearColor(0.3f, 1.0f, 0.5f, 1.0f);
		floor = new Texture("Images/blue.jpg");
		floor2 = new Texture("Images/floor.png");

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
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		floor.draw(-1f, -0.5f);
		floor.draw(-1f + 0.065f + 0.01f, -0.5f);
		GLFW.glfwSwapBuffers(window);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

}


















