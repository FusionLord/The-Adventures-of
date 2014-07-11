package net.fusionlord.adventuresof.game;

import net.fusionlord.adventuresof.game.screenmanager.ScreenManager;
import net.fusionlord.adventuresof.game.screenmanager.screens.CharacterSelection;
import net.fusionlord.adventuresof.game.util.Reference;
import net.fusionlord.adventuresof.game.util.Textures;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

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
		Reference.SCREENMANAGER = new ScreenManager();

		Reference.SCREENMANAGER.AddScreen(new CharacterSelection(container));
		container.getGraphics().setBackground(Color.white);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException
	{
		if (Display.getTitle() != Reference.getTITLE())
			Display.setTitle(Reference.getTITLE());

		Reference.SCREENMANAGER.update(container, delta);
		Input input = container.getInput();

		if (input.isKeyPressed(Input.KEY_F1))
			Reference.toggleDebug();
		if (container.isShowingFPS() != Reference.isDebug)
			container.setShowFPS(Reference.isDebug);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException
	{
		Reference.SCREENMANAGER.Draw(container, g);
	}
}
