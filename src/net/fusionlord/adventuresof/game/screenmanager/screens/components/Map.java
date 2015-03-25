package net.fusionlord.adventuresof.game.screenmanager.screens.components;

import net.fusionlord.adventuresof.game.world.MapBase;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Map extends BaseComponent
{

	private MapBase map;

	public Map(GUIContext container, MapBase map)
	{
		super(container);
		this.map = map;
	}

	@Override
	protected void onUpdate(GUIContext container, int delta)
	{}

	@Override
	protected void handleInput(Input input)
	{}

	@Override
	public void drawBackground(GUIContext container, Graphics g)
	{}

	@Override
	public void drawForeground(GUIContext container, Graphics g)
	{
		if (getDown() || getSelected())
		{
			g.setColor(Color.green);
		}
		else if (getHovered())
		{
			g.setColor(Color.yellow);
		}
		else
		{
			g.setColor(Color.darkGray);
		}

		int strWidth = g.getFont().getWidth(getName());
		int strHeight = g.getFont().getHeight(getName());
		if (getWidth() != strWidth || getHeight() != strHeight)
		{
			setDimensions(strWidth + 10, strHeight + 10);
		}
		g.drawRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, 6);
		g.setColor(Color.yellow);
		g.drawString(getName(), getX() + getWidth() / 2 - strWidth / 2, getY() + 5);
	}

	public String getName()
	{
		return map.getMapProperty("name", "map");
	}

	/*public void delete()
	{
		map.delete();
	}*/

	public MapBase getMap()
	{
		return map;
	}

}
