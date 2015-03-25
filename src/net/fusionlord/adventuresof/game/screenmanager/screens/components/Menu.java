package net.fusionlord.adventuresof.game.screenmanager.screens.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Menu extends BaseComponent
{

	private List<BaseComponent> componenets;
	private int                 selected = -1;
	private int 				yOffset;
	private boolean 			hasSorted;

	public Menu(GUIContext container)
	{
		super(container);
		componenets = new ArrayList<BaseComponent>();
	}

	@Override
	protected void onUpdate(GUIContext container, int delta)
	{
		for (BaseComponent item : componenets)
		{
			item.setLocation(item.getX(), item.getY() - yOffset);
			item.update(container, delta);
			if (item.needsUpdate() && !hasSorted)
			{
				sortComponents();
			}
		}
		hasSorted = false;
		yOffset = 0;
	}

	@Override
	protected void handleInput(Input input)
	{
		if (componenets.size() < 1)
		{
			return;
		}

		for (BaseComponent item : componenets)
		{
			if (item.isMouseHovering(input) && item.isEnabled())
			{
				if (!item.getHovered())
				{
					item.setHovered(true);
				}
				if (input.isMousePressed(0))
				{
					if (item.getSelected())
					{
						item.setSelected(false);
						selected = -1;
					}
					else
					{
						item.setSelected(true);
						selected = componenets.indexOf(item);
						for (BaseComponent item2 : componenets)
						{
							if (item2 != item)
							{
								item2.setSelected(false);
							}
						}
					}
				}
			}
			if (!item.isMouseHovering(input) && item.getHovered())
			{
				item.setHovered(false);
			}
		}
		if (input.isKeyPressed(Input.KEY_UP))
		{
			if (getSelectedItem() != null)
			{
				getSelectedItem().setSelected(false);
			}
			selected--;
			if (selected < 0)
			{
				selected = componenets.size() - 1;
			}
			while (!getSelectedItem().isEnabled())
			{
				selected--;
				if (selected < 0)
				{
					selected = componenets.size() - 1;
				}
			}
			getSelectedItem().setSelected(true);
			if (getSelectedItem().getY() < getY())
			{
				yOffset = (getSelectedItem().getY() - getY()) - 2;
			}
			if (getSelectedItem().getY() + getSelectedItem().getHeight() > getY() + getHeight())
			{
				yOffset = (getSelectedItem().getY() + getSelectedItem().getHeight()) - (getY() + getHeight() - 2);
			}
		}
		if (input.isKeyPressed(Input.KEY_DOWN))
		{
			if (getSelectedItem() != null)
			{
				getSelectedItem().setSelected(false);
			}
			selected++;
			if (selected > componenets.size() - 1)
			{
				selected = 0;
			}
			while (!getSelectedItem().isEnabled())
			{
				selected++;
				if (selected > componenets.size() - 1)
				{
					selected = 0;
				}
			}
			getSelectedItem().setSelected(true);
			if (getSelectedItem().getY() < getY())
			{
				yOffset = (getSelectedItem().getY() - getY()) - 2;
			}
			if (getSelectedItem().getY() + getSelectedItem().getHeight() > getY() + getHeight())
			{
				yOffset = (getSelectedItem().getY() + getSelectedItem().getHeight()) - (getY() + getHeight() - 2);
			}
		}

		if (getSelectedItem() != null)
		{
			if (input.isKeyPressed(Input.KEY_ENTER))
			{
				if (getSelectedItem().hasAction())
				{
					getSelectedItem().doAction();
				}
			}
			if (input.isKeyDown(Input.KEY_ENTER))
			{
				if (!getSelectedItem().getDown())
				{
					getSelectedItem().setDown(true);
				}
			}
			else
			{
				if (getSelectedItem().getDown())
				{
					getSelectedItem().setDown(false);
				}
			}
		}
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		if (componenets.size() < 1)
		{
			return;
		}
		BaseComponent first = componenets.get(0);
		BaseComponent last = componenets.get(componenets.size() - 1);
		boolean pos = change > 0;
		int interval = 75;
		int y = interval;
		if (pos)
		{
			if (first.getY() == getY() + 5)
			{
				return;
			}
			if (first.getY() + interval < getY() + 5)
			{
				y = (getY() + 5) - (first.getY() + interval);
			}
			y = -y;
		}
		else
		{
			if (last.getY() + last.getHeight() <= getY() + getHeight() - 5 && first.getY() <= getY() + 5)
			{
				return;
			}
			if (last.getY() + last.getHeight() - interval < getY() + getHeight() - 5)
			{
				y = (last.getY() + last.getHeight()) - (getY() + getHeight() - 5);
			}
		}
		yOffset = y;
	}

	@Override
	public void drawBackground(GUIContext container, Graphics g)
	{
		g.setColor(Color.black);
		g.fillRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, 6);
	}

	@Override
	public void drawForeground(GUIContext container, Graphics g)
	{
		Rectangle oldWorldClip = g.getWorldClip();

		g.setWorldClip(g.getClip());

		for (BaseComponent item : componenets)
		{
			item.draw(container, g);
		}

		g.setClip(g.getWorldClip());
		g.setWorldClip(oldWorldClip);

		g.setColor(Color.white);
		g.drawRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, 6);
	}

	public void addItem(BaseComponent item)
	{
		if (item instanceof Menu)
		{
			return;
		}
		componenets.add(item);
	}

	public void removeItem(BaseComponent item)
	{
		if (componenets.contains(item))
		{
			if (componenets.remove(item))
			{
				if (selected == componenets.indexOf(item))
				{
					selected = -1;
				}
				sortComponents();
			}
		}
	}

	public void sortComponents()
	{
		if (componenets.size() > 0)
		{
			for (BaseComponent item : componenets)
			{
				int x = getX() + getWidth() / 2 - item.getWidth() / 2;
				int y = getY() + 5;
				if (componenets.indexOf(item) > 0)
				{
					BaseComponent prevComp = componenets.get(componenets.indexOf(item) - 1);
					if (prevComp != null)
					{
						y = prevComp.getY() + prevComp.getHeight() + 5;
					}
				}
				item.setLocation(x, y);
			}
		}
		hasSorted = true;
	}

	public BaseComponent getSelectedItem()
	{
		if (componenets.size() > 0 && selected >= 0 && selected < componenets.size())
		{
			return componenets.get(selected);
		}
		return null;
	}
}
