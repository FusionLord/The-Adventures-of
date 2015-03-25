package net.fusionlord.adventuresof.game;

import net.fusionlord.adventuresof.game.screenmanager.ScreenManager;
import net.fusionlord.adventuresof.game.screenmanager.screens.CharacterSelectionScreen;
import net.fusionlord.adventuresof.game.util.Reference;
import net.fusionlord.adventuresof.game.util.Textures;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Game extends BasicGame
{

	public Game(String title)
	{
		super(title);
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
		Reference.TEXTURES = new Textures();
		Reference.SCREENMANAGER = new ScreenManager(container);

		Reference.SCREENMANAGER.AddScreen(new CharacterSelectionScreen(container));
		container.getGraphics().setBackground(Color.white);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException
	{
		if (container.isPaused())
		{
			return;
		}

		if (!Display.getTitle().equals(Reference.getTITLE()))
		{
			Display.setTitle(Reference.getTITLE());
		}

		Reference.SCREENMANAGER.update(container, delta);
		Input input = container.getInput();

		if (input.isKeyPressed(Input.KEY_F1))
		{
			Reference.toggleDebug();
		}
		if (input.isKeyPressed(Input.KEY_F11))
		{
			System.out.println("F11 pushed");
			container.setFullscreen(!container.isFullscreen());
		}
		if (input.isKeyPressed(Input.KEY_F2))
		{
			Reference.toggleGrid();
		}
		if (input.isKeyPressed(Input.KEY_F3))
		{
			Reference.toggleCrosshair();
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException
	{
		if (container.isPaused())
		{
			return;
		}
		Reference.SCREENMANAGER.Draw(container, g);
	}

	public static void initialize(String[] args)
	{
		int width = 1024;
		int height = 768;
		if (args.length >= 2)
		{
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		}
		try
		{

			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game(""));
			appgc.setDisplayMode(width, height, false);
			appgc.setShowFPS(false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
