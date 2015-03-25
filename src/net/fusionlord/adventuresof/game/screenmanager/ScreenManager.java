package net.fusionlord.adventuresof.game.screenmanager;

import net.fusionlord.adventuresof.game.screenmanager.screens.DebugScreen;
import net.fusionlord.adventuresof.game.util.Reference;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class ScreenManager
{

	protected List<Screen> curScreens = new ArrayList<Screen>();
	protected List<Screen> newScreens = new ArrayList<Screen>();
	protected List<Screen> remScreens = new ArrayList<Screen>();

	public DebugScreen debugScreen;

	public ScreenManager (GUIContext container)
	{
		debugScreen = new DebugScreen(container, "DebugScreen");
	}

	public void update(GUIContext container, int delta)
	{
		debugScreen.addInfo(String.format("FPS: %s", ((GameContainer) container).getFPS()));
		for (Screen screen : newScreens)
		{
			curScreens.add(screen);
		}
		newScreens.clear();

		for (Screen screen : remScreens)
		{
			curScreens.remove(screen);
			curScreens.get(curScreens.size() - 1).setFocused(true);
		}
		remScreens.clear();

		for (Screen screen : curScreens)
		{
			if (screen.isFocused())
			{
				screen.update(container, delta);
			}
		}
		debugScreen.update(container, delta);
	}

	public void Draw(GUIContext container, Graphics g)
	{
		for (Screen screen : curScreens)
		{
			if (screen.isFocused())
			{
				screen.draw(container, g);
			}
		}
		debugScreen.draw(container, g);
	}

	public void AddScreen(Screen screen)
	{
		screen.init(screen.container);
		screen.setFocused(true);
		newScreens.add(screen);

		for (Screen s : curScreens)
		{
			if (s.isFocused())
			{
				s.setFocused(false);
			}
		}
	}

	public void RemoveScreen(Screen screen)
	{
		if (screen == debugScreen)
			return;
		remScreens.add(screen);
	}
}
