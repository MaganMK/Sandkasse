package graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import static org.lwjgl.stb.STBImage.*;


import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL13;

public class Texture {

	private int textureID, width, height;
	
	public Texture(String path) 
	{
		textureID = load(path);
	}
	
	// Setter opp et bilde med GL11
	private int load(String path)
	{	
		// Her fylles pixlene på bildet inn i en array
		int[] pixels = null;
		try 
		{
			BufferedImage image = ImageIO.read(new FileInputStream(path)); // Her leses bildet inn 
			width = image.getWidth();
			height = image.getHeight();
			
			pixels = new int[width * height * 4];
			pixels = image.getRGB(0, 0, width, height, null, 0, width); // Her skjer den faktiske fyllingen
		}
		catch(IOException e){e.printStackTrace();}
		// ----------
		
		// GL11 ønsker å få pixlene på formen RGBA, mens vi har de på formen ARGB, 
		// så her flipper vi dette med litt binærmatte
		int[] data = new int[width * height * 4];
		for (int i = 0; i < width * height; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		// ----------

		// GL11 ønsker få pixlene som en IntBuffer, ikke som en array
		IntBuffer dataAsIntBuffer= ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		dataAsIntBuffer.put(data).flip(); 
		// ----------
		
		// Feeder pixlene inn til GL11.glGenTextures()
		int result = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, result); // Binder nåvernde texture
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, dataAsIntBuffer);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // Ferdig med å sette opp nåværende texture -> unbinder
		// ----------
		return result;
		
	}
	// ----------

	public void draw(float x, float y)
	{
		
		float dx = (float) (width/800.0);
		float dy = (float) (height/600.0);
		
		bind();
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(0, 1);
			glVertex2f(x, y + dy);
			glTexCoord2f(1, 1);
			glVertex2f(x + dx, y + dy);
			glTexCoord2f(1, 0);
			glVertex2f(x + dx, y);	
		}	
		glEnd();
	}
	
	// Binde/unbinde nåverende texture 
	public void bind() {GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);}
	public void unbind() {GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);}
	// ----------
}



















