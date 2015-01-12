import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Enemy extends PlatformerMain
{
	private int x, y, speed;
	private String fileName;
	private boolean up;
	private Texture texture;

	public Enemy(int x, int y, String fileName, int initSpeed)
	{
		super();
		this.x = x;
		this.y = y;
		this.fileName = fileName;
		this.speed = initSpeed;
		up = false;
		loadTexture();
	}

	public void move()
	{

		if (y == 720 - (64 * 2))
		{
			up = true;
		}
		else if (y == 720 - (64 * 4))
		{
			up = false;
		}

		if (up == true)
			y--;
		else if (up == false)
			y++;
	}
	
	public void move2()
	{

		if (y == 720 - (64 * 3))
		{
			up = true;
		}
		else if (y == 720 - (64 * 5))
		{
			up = false;
		}

		if (up == true)
			y--;
		else if (up == false)
			y++;
	}
	
	public void move3()
	{

		if (y == 720 - (64 * 1))
		{
			up = true;
		}
		else if (y == 720 - (64 * 3))
		{
			up = false;
		}

		if (up == true)
			y--;
		else if (up == false)
			y++;
	}

	public void loadTexture()
	{
		// init texturs
		try
		{
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + fileName));
			// System.out.println("Texture laoded: " + texture);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void draw()
	{

		glEnable(GL_TEXTURE_2D);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		Color.white.bind();
		texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);// top left coord
		glTexCoord2f(1, 0);
		glVertex2f(x + texture.getTextureWidth(), y);// top right coord
		glTexCoord2f(1, 1);
		glVertex2f(x + texture.getTextureWidth(), y + texture.getTextureHeight());// bottom
																					// right
																					// coord
		glTexCoord2f(0, 1);
		glVertex2f(x, y + texture.getTextureHeight());// bottom left coord
		glEnd();
		glDisable(GL_TEXTURE_2D);
		
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

}
