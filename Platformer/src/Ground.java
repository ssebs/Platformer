import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Ground extends PlatformerMain
{
	private int x, y;
	private Texture texture;

	public Ground(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
		loadTexture();
	}

	public void loadTexture()
	{
		// init texturs
		try
		{
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Ground.png"));
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

}
