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
	{
		if (isMouseHovering(input))
		{
			if (!getHovered())
				setHovered(true);
			if (input.isMousePressed(0))
			{
				if(!getSelected())
					setSelected(true);
			}
		}
		if (!isMouseHovering(input) && getHovered())
			setHovered(false);
	}

	@Override
	public void draw(GUIContext container, Graphics g)
	{
		if(getDown() || getSelected())
			g.setColor(Color.green);
		else if (getHovered())
			g.setColor(Color.yellow);
		else
			g.setColor(Color.darkGray);

		g.drawRoundRect(getX(), getY(), getWidth(), getHeight(), 6);
		g.setColor(Color.yellow);
		int strWidth = g.getFont().getWidth(player.getName());
		g.drawString(player.getName(), getX() + getWidth() / 2 - strWidth / 2, getY() + 5);
		player.draw(container, g);
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
