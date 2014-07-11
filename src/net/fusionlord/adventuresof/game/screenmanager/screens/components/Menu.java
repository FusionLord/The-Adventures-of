package net.fusionlord.adventuresof.game.screenmanager.screens.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.Log;

import java.util.List;
import java.util.ArrayList;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Menu extends BaseComponent
{
	private List<BaseComponent> componenets;
	private int selected;

	public Menu(GUIContext container)
	{
		super(container);
		componenets = new ArrayList<BaseComponent>();
	}

	@Override
	protected void onUpdate(GUIContext container, int delta)
	{
		for(BaseComponent item : componenets)
		{
			item.update(container, delta);
		}
	}

	@Override
	protected void handleInput(Input input)
	{
		if (componenets.size() < 1)
			return;

		if (input.isKeyPressed(Input.KEY_UP))
		{
			getSelectedItem().setHovered(false);
			selected--;
			if (selected < 0)
				selected = componenets.size() - 1;
			while (!getSelectedItem().isEnabled())
			{
				selected--;
				if (selected < 0)
					selected = componenets.size() - 1;
			}
			getSelectedItem().setHovered(true);
		}
		if (input.isKeyPressed(Input.KEY_DOWN))
		{
			getSelectedItem().setHovered(false);
			selected++;
			if (selected > componenets.size() - 1)
				selected = 0;
			while (!getSelectedItem().isEnabled())
			{
				selected++;
				if (selected > componenets.size() - 1)
					selected = 0;
			}
			getSelectedItem().setHovered(true);
		}
		if (input.isKeyPressed(Input.KEY_ENTER))
		{
			if (getSelectedItem().hasAction())
			{
				getSelectedItem().doAction();
			}
			else
				getSelectedItem().setSelected(true);
		}
		if (input.isKeyDown(Input.KEY_ENTER))
		{
			if (!getSelectedItem().getDown())
				getSelectedItem().setDown(true);
		}
		else
		{
			if (getSelectedItem().getDown())
				getSelectedItem().setDown(false);
		}
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		BaseComponent first = componenets.get(0);
		BaseComponent last = componenets.get(componenets.size() - 1);
		int interval = 75;
		int y = interval;
		boolean pos = change > 0;
		if (pos)
			if (first.getY() + interval > getY() + 5)
				y = first.getY() - getY() - 5;
			else
				y *= -1;
		else
			if (last.getY() - interval < getY() + getHeight() - last.getHeight() - 5)
				y = last.getY() - getY() - getHeight() + last.getHeight() + 5;

		for (BaseComponent component : componenets)
			component.setLocation(component.getX(), component.getY() - y);
	}

	@Override
	public void draw(GUIContext container, Graphics g)
	{
		Rectangle oldClip = g.getClip();
		g.setClip(getX(), getY(), getWidth(), getHeight());

		g.setColor(Color.black);
		g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 6);
		g.setColor(Color.white);
		g.drawRoundRect(getX(), getY(), getWidth(), getHeight(), 6);

		for (BaseComponent item : componenets)
		{
			item.draw(container, g);
		}

		g.setClip(oldClip);
	}

	public void addItem(BaseComponent item)
	{
		if (item instanceof Menu)
			return;
		int x = getX() + getWidth() / 2 - item.getWidth() / 2;
		int y = getY() + 5;
		if (componenets.size() > 0)
			y = componenets.get(componenets.size() - 1).getY() + componenets.get(componenets.size() - 1).getHeight() + 5;

		item.setLocation(x, y);

		componenets.add(item);
	}

	public void removeItem(BaseComponent item)
	{
		if (componenets.contains(item))
			componenets.remove(item);

		List<BaseComponent> tempList = new ArrayList<BaseComponent>();

		for (BaseComponent baseComponent : componenets)
			tempList.add(baseComponent);

		componenets.clear();

		for (BaseComponent baseComponent : tempList)
			addItem(baseComponent);
	}

	public BaseComponent getSelectedItem()
	{
		if (componenets.size() > 0)
			return componenets.get(selected);
		return null;
	}
}
