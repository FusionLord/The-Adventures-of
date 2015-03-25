package net.fusionlord.adventuresof.game.screenmanager.screens;

import net.fusionlord.adventuresof.game.screenmanager.Screen;
import net.fusionlord.adventuresof.game.util.Reference;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;

import java.util.ArrayList;
import java.util.List;

public class DebugScreen extends Screen
{

	protected List<String> info = new ArrayList<String>();

	public DebugScreen(GUIContext container, String name)
	{
		super(container, name);
		backgroundColor = new Color(0, 0, 0, 0);
	}

	@Override
	public void init(GUIContext container)
	{}

	protected void handleInput(Input input)
	{}

	@Override
	public void update(GUIContext container, int delta)
	{
		if (Reference.isDebug)
		{
			return;
		}
		handleInput(container.getInput());
	}

	@Override
	protected void drawForeground(GUIContext container, Graphics g)
	{
		if (Reference.isDebug)
		{
			return;
		}
		int x = -1;
		int y = 0;
		int strHeight = 0;
		for (String s : info)
		{
			int h = g.getFont().getHeight(s);
			if (h > strHeight)
			{
				strHeight = h;
			}
		}
		for (String s : info)
		{
			int strWidth = g.getFont().getWidth(s);
			if (x + strWidth > container.getWidth())
			{
				y++;
				x = 0;
			}
			g.setColor(Color.black);
			g.fillRoundRect(x + 5, y * (10 + strHeight) + 10, strWidth + 6, strHeight + 6, 6);
			g.setColor(Color.white);
			g.drawString(s, x + 7, y * (10 + strHeight) + 12);
			x += 11 + strWidth;
		}
		info.clear();
	}

	public void addInfo(String s)
	{
		if (Reference.isDebug)
		{
			return;
		}
		info.add(s);
	}
}
