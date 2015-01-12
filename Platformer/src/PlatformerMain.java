import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
//import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class PlatformerMain implements Runnable
{
	public final int WIDTH = 1280, HEIGHT = 720, MENU = 0, PLAY = 1, END = 2, OPTIONS = 3;
	private boolean stopper, fallBool = true, foodDraw = true, once = false;
	private int state, level, y = 0;;
	private String stringToDraw;
	private Player joff;
	private Enemy penguin, penguin2, penguin3, penguin4, penguin5, penguin6, penguin7, penguin8, penguin9;
	private Door door1;
	private Food food;
	private Font font;
	private TrueTypeFont ttf;
	private ArrayList<Ground> dirt, grass, dirt2, grass2, water2, dirt3, grass3, water3;
	private Texture menuTex, optTex, skyTex, endTex;

	public void menu()
	{
		renderMenu();
		pollInputMenu();
	}

	public void renderMenu()
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
		menuTex.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);// top left coord
		glTexCoord2f(1, 0);
		glVertex2f(0 + menuTex.getTextureWidth(), 0);// top right coord
		glTexCoord2f(1, 1);
		glVertex2f(0 + menuTex.getTextureWidth(), 0 + menuTex.getTextureHeight());
		glTexCoord2f(0, 1);
		glVertex2f(0, 0 + menuTex.getTextureHeight());// bottom left coord
		glEnd();
	}

	/**
	 * checks the input of the controls
	 */
	public void pollInputMenu()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			state = PLAY;
		}
		// Mouse
		if (Mouse.isButtonDown(0) && (Mouse.getX() >= 320 && Mouse.getX() <= 960) && (Mouse.getY() <= 450 && Mouse.getY() >= 270))// start
																																	// button
		{
			state = PLAY;
		}

		if (Mouse.isButtonDown(0) && (Mouse.getX() >= 320 && Mouse.getX() <= 960) && (Mouse.getY() <= 180 && Mouse.getY() >= 0))// Options
		// button
		{
			state = OPTIONS;
		}

	}

	public void options()
	{
		renderOptions();
		pollInputOptions();
	}

	public void renderOptions()
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
		optTex.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);// top left coord
		glTexCoord2f(1, 0);
		glVertex2f(0 + optTex.getTextureWidth(), 0);// top right coord
		glTexCoord2f(1, 1);
		glVertex2f(0 + optTex.getTextureWidth(), 0 + optTex.getTextureHeight());
		glTexCoord2f(0, 1);
		glVertex2f(0, 0 + optTex.getTextureHeight());// bottom left coord
		glEnd();
	}

	public void pollInputOptions()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			state = MENU;
		}
	}

	public void play()
	{
		playerBounds();
		switch (level)
		{
		case 1:/* level one */
			levelOne();
			break;
		case 2: /* level two */
			levelTwo();
			break;
		case 3: /* level three */
			levelThree();
			break;
		}
	}

	public void playerBounds()
	{
		if (joff.getX() <= 0)
		{
			joff.setX(0);
		} else if (joff.getY() > 720 - 64)
		{
			joff.setY(720 - 64);
		} else if (joff.getY() <= 0)
		{
			joff.setY(0);
		}

	}

	public void renderSky()
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
		skyTex.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);// top left coord
		glTexCoord2f(1, 0);
		glVertex2f(0 + skyTex.getTextureWidth(), 0);// top right coord
		glTexCoord2f(1, 1);
		glVertex2f(0 + skyTex.getTextureWidth(), 0 + skyTex.getTextureHeight());
		glTexCoord2f(0, 1);
		glVertex2f(0, 0 + skyTex.getTextureHeight());// bottom left coord
		glEnd();
	}

	public void pollInputPlayL3()
	{
		if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)))
		{
			// up and right
			joff.draw(2);
			joff.moveRight();
			gravityL3(true);
			// fallBool = true;
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)))
		{
			// up and to the left
			joff.draw(2);
			joff.moveLeft();
			gravityL3(true);
			// fallBool = true;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			joff.draw(4);

			gravityL3(true);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			joff.draw(1);
			fallBool = true;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			joff.draw(2);
			joff.moveRight();
			fallBool = true;
			// System.out.println("Right");
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			joff.draw(3);
			joff.moveLeft();
			fallBool = true;
			// System.out.println("Left");
		} else
		{
			joff.draw(1);
			fallBool = true;
		}
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_P)
				{

				}
			} else
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_W || Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					// System.out.println("A Key Released");
					// joff.
					gravityL3(false);
				}
			}
		}
		gravityL3(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			state = MENU;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_P))
		{
			joff.setHealth(0);
		}
	}

	public void pollInputPlayL2()
	{
		if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)))
		{
			// up and right
			joff.draw(2);
			joff.moveRight();
			gravityL2(true);
			// fallBool = true;
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)))
		{
			// up and to the left
			joff.draw(2);
			joff.moveLeft();
			gravityL2(true);
			// fallBool = true;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			joff.draw(4);

			gravityL2(true);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			joff.draw(1);
			fallBool = true;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			joff.draw(2);
			joff.moveRight();
			fallBool = true;
			// System.out.println("Right");
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			joff.draw(3);
			joff.moveLeft();
			fallBool = true;
			// System.out.println("Left");
		} else
		{
			joff.draw(1);
			fallBool = true;
		}
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_P)
				{

				}
			} else
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_W || Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					// System.out.println("A Key Released");
					// joff.
					gravityL2(false);
				}
			}
		}
		gravityL2(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			state = MENU;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_P))
		{
			joff.setHealth(0);
		}
	}

	/**
	 * checks the input of the controls
	 */
	public void pollInputPlayL1()
	{
		if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)))
		{
			// up and right
			joff.draw(2);
			joff.moveRight();
			gravityL1(true);
			// fallBool = true;
		} else if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)))
		{
			// up and to the left
			joff.draw(2);
			joff.moveLeft();
			gravityL1(true);
			// fallBool = true;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			joff.draw(4);

			gravityL1(true);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			joff.draw(1);
			fallBool = true;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			joff.draw(2);
			joff.moveRight();
			fallBool = true;
			// System.out.println("Right");
		} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			joff.draw(3);
			joff.moveLeft();
			fallBool = true;
			// System.out.println("Left");
		} else
		{
			joff.draw(1);
			fallBool = true;
		}
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_P)
				{

				}
			} else
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_W || Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					// System.out.println("A Key Released");
					// joff.
					gravityL1(false);
				}
			}
		}
		gravityL1(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			state = MENU;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_P))
		{
			joff.setHealth(0);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_3))
		{
			level = 3;
		}
	}

	public void levelOne()
	{
		renderLevelOne();
		// logic
		penguin.move();
		enemyCollision(penguin);
		doorCollision();
		stringToDraw = "Health: " + joff.getHealth();
		pollInputPlayL1();// gets input from keyboard and mouse, also renders
		// character
		gravityL1(true);

		if (joff.getHealth() <= 0)
		{
			state = END;
		}

	}

	public void renderLevelOne()
	{
		renderSky();
		for (Ground g : dirt)
		{
			g.draw(1);
		}
		for (Ground g : grass)
		{
			g.draw(2);
		}

		penguin.draw();
		door1.draw();

		ttf.drawString(64, 32, stringToDraw, Color.green);// comic sans
		// ttf2.drawString(400, 300, "some other text", Color.red);
	}

	public void levelTwo()
	{
		renderLevelTwo();
		// logic
		penguin.move3();
		penguin2.move2();
		enemyCollision(penguin);
		enemyCollision(penguin2);
		doorCollision();
		powerUp();
		stringToDraw = "Health: " + joff.getHealth();
		pollInputPlayL2();// gets input from keyboard and mouse, also renders
		// character
		gravityL2(true);

		if (joff.getHealth() <= 0)
		{
			state = END;
		}
	}

	public void renderLevelTwo()
	{
		renderSky();
		for (Ground g : dirt2)
		{
			g.draw(1);
		}
		for (Ground g : grass2)
		{
			g.draw(2);
		}
		for (Ground g : water2)
		{
			g.draw(3);
		}

		penguin.draw();
		penguin2.draw();
		door1.draw();

		if (foodDraw == true)
		{
			food.draw();
		}
		ttf.drawString(64, 32, stringToDraw, Color.green);// comic sans

		// ttf2.drawString(400, 300, "some other text", Color.red);
	}

	public void levelThree()
	{
		renderLevelThree();
		// logic
		penguin3.move(3, 5);
		penguin4.move(5, 7);
		penguin5.move(6, 8);
		penguin6.move(3, 5);
		penguin7.move(5, 7);
		penguin8.move(6, 8);
		penguin9.move(5, 7);
		enemyCollision(penguin3);
		enemyCollision(penguin4);
		enemyCollision(penguin5);
		enemyCollision(penguin6);
		enemyCollision(penguin7);
		enemyCollision(penguin8);
		enemyCollision(penguin9);
		doorCollision();
		powerUp();
		stringToDraw = "Health: " + joff.getHealth();
		pollInputPlayL3();// gets input from keyboard and mouse, also renders
		// character
		gravityL3(true);

		if (joff.getHealth() <= 0)
		{
			state = END;
		}
	}

	public void renderLevelThree()
	{
		renderSky();
		for (Ground g : dirt3)
		{
			g.draw(1);
		}
		for (Ground g : grass3)
		{
			g.draw(2);
		}
		for (Ground g : water3)
		{
			g.draw(3);
		}

		penguin3.draw();
		penguin4.draw();
		penguin5.draw();
		penguin6.draw();
		penguin7.draw();
		penguin8.draw();
		penguin9.draw();

		door1.draw();

		if (foodDraw == true)
		{
			food.draw();
		}
		ttf.drawString(64, 32, stringToDraw, Color.green);// comic sans

		// ttf2.drawString(400, 300, "some other text", Color.red);
	}

	public void end()
	{
		renderCredits(y);
		y -= 2;
		if (y <= -2880)
			System.exit(0);
	}

	public void renderCredits(int y)
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
		endTex.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, y);// top left coord
		glTexCoord2f(1, 0);
		glVertex2f(0 + endTex.getTextureWidth(), y);// top right coord
		glTexCoord2f(1, 1);
		glVertex2f(0 + endTex.getTextureWidth(), y + endTex.getTextureHeight());
		glTexCoord2f(0, 1);
		glVertex2f(0, y + endTex.getTextureHeight());// bottom left coord
		glEnd();
	}

	public void doorCollision()
	{
		if ((joff.getX() >= 1216))
		{
			level++;
			joff.setX(32);
			joff.setY(200);
		}
		if (level > 3 && joff.getHealth() > 0)
		{
			win();
			System.out.println("YOU WON");
		}
		if (level > 3)
		{
			joff.setHealth(0);
		}
		if (level == 3)
		{
			food.setX(64 * 7);
			food.setY((720 - (64 * 5)));

			if (once == false)
			{
				foodDraw = true;
				once = true;
			}
		}

	}

	public void win()
	{
		// TODO redner win
	}

	@SuppressWarnings("unused")
	public void powerUp()
	{

		// these are all left and top, everything is 64 tall n 64 wide
		int joffX = joff.getX();
		int joffY = joff.getY();
		int foodX = food.getX();
		int foodY = food.getY();

		// if (true)
		// {
		// joff.setHealth(joff.getHealth() + 50);
		// foodDraw = false;
		// }

		if (((Math.abs(joff.getY() - food.getY()) <= 64) && ((joff.getX() + 64) >= food.getX()) && (joff.getX() + 64) <= (food.getX() + 64)) && foodDraw == true)
		{
			joff.setHealth(joff.getHealth() + 50);
			foodDraw = false;
		}
	}

	public void enemyCollision(Enemy penguin)
	{
		// bounce left
		if ((Math.abs(joff.getY() - penguin.getY()) <= 64) && ((joff.getX() + 64) >= penguin.getX()) && (joff.getX() + 64) <= (penguin.getX() + 64))
		{
			// System.out.println("Enemy collision");
			joff.setX(joff.getX() - 64);
			joff.setHealth(joff.getHealth() - 5);
		}
	}

	public void gravityL1(boolean gravity)
	{
		for (Ground g : dirt)
		{
			if (fallBool == true && groundCollide(g, joff))
			{
				fallBool = false;
				// System.out.println("Collides");
				break;
			}

		}
		for (Ground g : grass)
		{
			if (fallBool == true && groundCollide(g, joff))
			{
				fallBool = false;
				// System.out.println("Collides");
				break;
			}

		}

		if (fallBool == true && gravity == true)
		{
			joff.setY(joff.getY() + 5);
		}
		if (gravity == false)
		{
			joff.setY(joff.getY() - 256);
		}

	}

	public void gravityL2(boolean gravity)
	{
		for (Ground g : dirt2)
		{
			if (fallBool == true && groundCollide(g, joff))
			{
				fallBool = false;
				// System.out.println("Collides");
				break;
			}

		}
		for (Ground g : grass2)
		{
			if (fallBool == true && groundCollide(g, joff))
			{
				fallBool = false;
				// System.out.println("Collides");
				break;
			}

		}

		for (Ground g : water2)
		{
			if (fallBool == true && waterCollide(g, joff))
			{
				fallBool = false;
				// System.out.println("Collides");
				joff.setHealth(joff.getHealth() - 1);
				break;
			}

		}

		if (fallBool == true && gravity == true)
		{
			joff.setY(joff.getY() + 5);
		}
		if (gravity == false)
		{
			joff.setY(joff.getY() - 256);
		}

	}

	public void gravityL3(boolean gravity)
	{
		for (Ground g : dirt3)
		{
			if (fallBool == true && groundCollide(g, joff))
			{
				fallBool = false;
				// System.out.println("Collides");
				break;
			}

		}
		for (Ground g : grass3)
		{
			if (fallBool == true && groundCollide(g, joff))
			{
				fallBool = false;
				// System.out.println("Collides");
				break;
			}

		}

		for (Ground g : water3)
		{
			if (fallBool == true && waterCollide(g, joff))
			{
				fallBool = false;
				// System.out.println("Collides");
				joff.setHealth(joff.getHealth() - 1);
				break;
			}

		}

		if (fallBool == true && gravity == true)
		{
			joff.setY(joff.getY() + 5);
		}
		if (gravity == false)
		{
			joff.setY(joff.getY() - 256);
		}

	}

	public boolean groundCollide(Ground g, Player p)
	{
		boolean bool = false;
		int pXL = p.getX();
		int pYB = p.getY() + 64;
		int gXL = g.getX();
		int gYT = g.getY();

		if ((Math.abs(pXL - gXL) < 48) && (gYT - pYB > -64) && (gYT - pYB < 0))// top
		{
			bool = true;
		}

		return bool;
	}

	public boolean waterCollide(Ground g, Player p)
	{
		boolean bool = false;
		int pXL = p.getX();
		int pYB = p.getY() + 32;
		int gXL = g.getX();
		int gYT = g.getY();

		if ((Math.abs(pXL - gXL) < 48) && (gYT - pYB > -64) && (gYT - pYB < 0))// top
		{
			bool = true;
		}

		return bool;
	}

	/**
	 * @param args
	 */
	public void states()
	{
		switch (state)
		{
		case MENU:/* MENU */
			menu();
			break;
		case PLAY: /* PLAY */
			play();
			break;
		case END: /* END */
			end();
			break;
		case OPTIONS:
			options();
			break;
		default:
			menu();
			break;
		}
	}

	public void initObjs()
	{

		// init objects
		joff = new Player(200, 200, "Joffreey.png", 6, 100);
		penguin = new Enemy((1280 - (64 * 4)), (720 - (64 * 3)), "tux1.png", 2);
		penguin2 = new Enemy((1280 - (64 * 9)), (720 - (64 * 4)), "tux1.png", 2);
		penguin3 = new Enemy((64 * 4), (720 - (64 * 4)), "tux1.png", 2);
		penguin4 = new Enemy((64 * 6), (720 - (64 * 6)), "tux1.png", 3);
		penguin5 = new Enemy((64 * 8), (720 - (64 * 6)), "tux1.png", 2);
		penguin6 = new Enemy((64 * 10), (720 - (64 * 4)), "tux1.png", 4);
		penguin7 = new Enemy((64 * 12), (720 - (64 * 7)), "tux1.png", 2);
		penguin8 = new Enemy((64 * 16), (720 - (64 * 7)), "tux1.png", 7);
		penguin9 = new Enemy((64 * 17), (720 - (64 * 5)), "tux1.png", 1);
		door1 = new Door((1280 - 64), (720 - 128), "Door.png");
		food = new Food(64 * 11, (720 - (64 * 5)), "Food.png");

		{// makes grass for level 1
			int x = 0;
			int y = 720 - 64;
			for (int j = 0; j < 22; j++)
			{
				grass.add(new Ground(x, y));
				x += 64;
			}
		}

		{// makes dirt for level 1
			int x = 320;// starting pos x
			int y = 720 - (3 * 64);// starting pos y
			for (int j = 0; j < 3; j++)
			{
				dirt.add(new Ground(x, y));
				x += 64;
			}
		}
		// ///LEVEL TWO
		{// makes grass for level 2
			grass2.add(new Ground(64 * 0, (720 - (64 * 5))));
			grass2.add(new Ground(64 * 1, (720 - (64 * 4))));
			grass2.add(new Ground(64 * 2, (720 - (64 * 3))));
			grass2.add(new Ground(64 * 3, (720 - (64 * 2))));
			grass2.add(new Ground(64 * 4, (720 - (64 * 1))));
			grass2.add(new Ground(64 * 5, (720 - (64 * 1))));
			grass2.add(new Ground(64 * 6, (720 - (64 * 1))));
			grass2.add(new Ground(64 * 8, (720 - (64 * 3))));
			grass2.add(new Ground(64 * 9, (720 - (64 * 3))));

			grass2.add(new Ground(64 * 10, (720 - (64 * 3))));

			grass2.add(new Ground(64 * 12, (720 - (64 * 3))));
			grass2.add(new Ground(64 * 13, (720 - (64 * 3))));
			grass2.add(new Ground(64 * 14, (720 - (64 * 3))));

			grass2.add(new Ground(64 * 17, (720 - (64 * 1))));
			grass2.add(new Ground(64 * 18, (720 - (64 * 1))));
			grass2.add(new Ground(64 * 19, (720 - (64 * 1))));
		}

		{// makes dirt for level 2
			dirt2.add(new Ground(64 * 0, (720 - (64 * 4))));
			dirt2.add(new Ground(64 * 0, (720 - (64 * 3))));
			dirt2.add(new Ground(64 * 0, (720 - (64 * 2))));
			dirt2.add(new Ground(64 * 0, (720 - (64 * 1))));
			dirt2.add(new Ground(64 * 1, (720 - (64 * 3))));
			dirt2.add(new Ground(64 * 1, (720 - (64 * 2))));
			dirt2.add(new Ground(64 * 1, (720 - (64 * 1))));
			dirt2.add(new Ground(64 * 2, (720 - (64 * 2))));
			dirt2.add(new Ground(64 * 2, (720 - (64 * 1))));
			dirt2.add(new Ground(64 * 3, (720 - (64 * 1))));
		}

		{// makes water for level 2
			water2.add(new Ground(64 * 7, (720 - (64 * 1))));
			water2.add(new Ground(64 * 8, (720 - (64 * 1))));
			water2.add(new Ground(64 * 9, (720 - (64 * 1))));
			water2.add(new Ground(64 * 10, (720 - (64 * 1))));
			water2.add(new Ground(64 * 11, (720 - (64 * 1))));
			water2.add(new Ground(64 * 12, (720 - (64 * 1))));
			water2.add(new Ground(64 * 13, (720 - (64 * 1))));
			water2.add(new Ground(64 * 14, (720 - (64 * 1))));
			water2.add(new Ground(64 * 15, (720 - (64 * 1))));
			water2.add(new Ground(64 * 16, (720 - (64 * 1))));
		}
		// ///LEVEL THREE

		{// makes grass blocks for level 3
			grass3.add(new Ground(64 * 0, (720 - (64 * 5))));
			grass3.add(new Ground(64 * 1, (720 - (64 * 5))));
			grass3.add(new Ground(64 * 3, (720 - (64 * 3))));
			grass3.add(new Ground(64 * 5, (720 - (64 * 5))));
			grass3.add(new Ground(64 * 7, (720 - (64 * 7))));
			grass3.add(new Ground(64 * 9, (720 - (64 * 3))));
			grass3.add(new Ground(64 * 11, (720 - (64 * 5))));
			grass3.add(new Ground(64 * 13, (720 - (64 * 7))));
			grass3.add(new Ground(64 * 15, (720 - (64 * 5))));
			grass3.add(new Ground(64 * 19, (720 - (64 * 1))));

		}

		{// makes water blocks for level 3
			for (int i = 0; i < 19; i++)
			{
				water3.add(new Ground(64 * i, (720 - (64 * 1))));
			}
		}

		Display.setTitle("A Platformer Of Some Sort");

	}

	public void init()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Loading...");
			Display.setInitialBackground(0, 0, 0);
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		// init openGl here
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0f, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		// init textures
		try
		{
			menuTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Menu.png"));
			// System.out.println("Texture laoded: " + texture);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		try
		{
			optTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Options.png"));
			// System.out.println("Texture laoded: " + texture);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		try
		{
			skyTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Sky.png"));
			// System.out.println("Texture laoded: " + texture);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		try
		{
			endTex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/Credits.png"));
			// System.out.println("Texture laoded: " + texture);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		// init fonts
		font = new Font("Comic Sans MS", Font.PLAIN, 48);// font 1
		ttf = new TrueTypeFont(font, true);// font 1

		// init vars
		stopper = false;
		level = 1;
		stringToDraw = "Health: ";

		// init arraylists
		dirt = new ArrayList<Ground>();
		grass = new ArrayList<Ground>();

		dirt2 = new ArrayList<Ground>();
		grass2 = new ArrayList<Ground>();
		water2 = new ArrayList<Ground>();

		dirt3 = new ArrayList<Ground>();
		grass3 = new ArrayList<Ground>();
		water3 = new ArrayList<Ground>();

		initObjs();
	}// end init

	public void start()
	{
		init();

		while (stopper == false)
		{
			if (Display.isCloseRequested())
			{
				stopper = true;
				System.exit(0);
			}
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			// game rules
			states();
			Display.update();// updates screen
		}
		Display.destroy();

	}

	public void run()
	{
		// loops sound in another thread
		do
		{
			Sound.play("res/Sound1.wav");
			try
			{
				Thread.sleep(7000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		} while (true);

	}

	public static void main(String[] args)
	{
		new Thread(new PlatformerMain()).start();
		PlatformerMain g3d = new PlatformerMain();
		g3d.start();
	}
}