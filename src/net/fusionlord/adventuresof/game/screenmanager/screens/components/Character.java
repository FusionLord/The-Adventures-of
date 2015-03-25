package net.fusionlord.adventuresof.game.screenmanager.screens.components;

import net.fusionlord.adventuresof.game.entity.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Character extends BaseComponent
{
	private Player player;

	public Character(GUIContext container, Player player)
	{
		super(container);
		this.player = player;
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
		player.drawOnCharacterComponent(container, g, this);
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
		g.drawString(getName(), getX() + getWidth() / 2 - strWidth / 2, getY() + 5);
		g.drawRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, 6);
	}

	public String getName()
	{
		return player.getName();
	}

	public void delete()
	{
		player.delete();
	}

	public Player getPlayer()
	{
		return player;
	}
}
