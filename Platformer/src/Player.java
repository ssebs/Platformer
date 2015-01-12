import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Player extends PlatformerMain
{
	private int x, y, speed, health;
	private String fileName;
	private Texture texture;

	public Player(int x, int y, String fileName, int initSpeed, int health)
	{
		super();
		this.x = x;
		this.y = y;
		this.fileName = fileName;
		this.speed = initSpeed;
		this.health = health;
		loadTexture();
	}

	public void moveLeft()
	{
		x -= speed;
	}

	public void moveRight()
	{
		x += speed;
	}

	public void loadTexture()
	{
		// init texturs
		try
		{
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + fileName));
			// System.out.println("Texture laoded: " + texture);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void draw(int partOfImg)
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
		if (partOfImg == 1)
		{
			float width = (texture.getTextureWidth() / 4 + .25f);
			float height = texture.getTextureHeight();

			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);// top left coord
			glTexCoord2f(.25f, 0);
			glVertex2f(x + width, y);// top right coord
			glTexCoord2f(.25f, 1);
			glVertex2f(x + width, y + height);
			glTexCoord2f(0, 1);
			glVertex2f(x, y + height);// bottom left coord
			glEnd();
		} else if (partOfImg == 2)
		{
			float width = (texture.getTextureWidth() / 4 + .5f);
			float height = texture.getTextureHeight();

			glBegin(GL_QUADS);
			glTexCoord2f(.25f, 0);
			glVertex2f(x, y);// top left coord
			glTexCoord2f(.5f, 0);
			glVertex2f(x + width, y);// top right coord
			glTexCoord2f(.5f, 1);
			glVertex2f(x + width, y + height);
			glTexCoord2f(.25f, 1);
			glVertex2f(x, y + height);// bottom left coord
			glEnd();
		} else if (partOfImg == 3)
		{
			float width = (texture.getTextureWidth() / 4 + .75f);
			float height = texture.getTextureHeight();

			glBegin(GL_QUADS);
			glTexCoord2f(.5f, 0);
			glVertex2f(x, y);// top left coord
			glTexCoord2f(.75f, 0);
			glVertex2f(x + width, y);// top right coord
			glTexCoord2f(.75f, 1);
			glVertex2f(x + width, y + height);
			glTexCoord2f(.5f, 1);
			glVertex2f(x, y + height);// bottom left coord
			glEnd();
		} else
		{
			float width = (texture.getTextureWidth() / 4 + 1f);
			float height = texture.getTextureHeight();

			glBegin(GL_QUADS);
			glTexCoord2f(.75f, 0);
			glVertex2f(x, y);// top left coord
			glTexCoord2f(1f, 0);
			glVertex2f(x + width, y);// top right coord
			glTexCoord2f(1f, 1);
			glVertex2f(x + width, y + height);
			glTexCoord2f(.75f, 1);
			glVertex2f(x, y + height);// bottom left coord
			glEnd();
		}

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

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}
}
