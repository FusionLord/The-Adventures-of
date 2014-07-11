package net.fusionlord.adventuresof.game.screenmanager;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.Log;

import java.util.List;
import java.util.ArrayList;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class ScreenManager
{
	protected List<Screen> curScreens = new ArrayList<Screen>();
	protected List<Screen> newScreens = new ArrayList<Screen>();
	protected List<Screen> remScreens = new ArrayList<Screen>();

	public void update(GUIContext container, int delta)
	{
		for (Screen screen : newScreens)
			curScreens.add(screen);
		newScreens.clear();

		for (Screen screen : remScreens)
		{
			curScreens.remove(screen);
			curScreens.get(curScreens.size() - 1).setFocused(true);
		}
		remScreens.clear();

		for (Screen screen : curScreens)
			if (screen.isFocused())
				screen.update(container, delta);
	}

	public void Draw(GUIContext container, Graphics g)
	{
		for (Screen screen : curScreens)
		{
			if (screen.isFocused())
				screen.draw(container, g);
		}
	}

	public void AddScreen(Screen screen)
	{
		screen.init(screen.container);
		screen.setFocused(true);
		newScreens.add(screen);

		for (Screen s : curScreens)
			if (s.isFocused())
				s.setFocused(false);
	}

	public void RemoveScreen(Screen screen)
	{
		remScreens.add(screen);
	}
}
