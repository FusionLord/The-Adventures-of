package net.fusionlord.adventuresof.game.screenmanager;

import org.lwjgl.input.Cursor;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.ImageData;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public abstract class Screen implements GUIContext
{

	public  GUIContext	container;
	public  String		screenName;
	private boolean		focused;
	protected Color		backgroundColor;

	public Screen(GUIContext container, String name)
	{
		this.container = container;
		this.screenName = name;
	}

	public abstract void init(GUIContext container);

	public abstract void update(GUIContext container, int delta);

	public final void draw(GUIContext container, Graphics g)
	{
		drawBackground(container, g);
		drawForeground(container, g);
	}

	protected void drawBackground(GUIContext container, Graphics g)
	{
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	protected abstract void drawForeground(GUIContext container, Graphics g);

	@Override
	public Input getInput()
	{
		return container.getInput();
	}

	@Override
	public int getScreenWidth()
	{
		return container.getScreenWidth();
	}

	@Override
	public int getScreenHeight()
	{
		return container.getScreenHeight();
	}

	@Override
	public int getWidth()
	{
		return container.getWidth();
	}

	@Override
	public int getHeight()
	{
		return container.getHeight();
	}


	@Override
	public long getTime()
	{
		return container.getTime();
	}

	@Override
	public Font getDefaultFont()
	{
		return container.getDefaultFont();
	}

	@Override
	public void setMouseCursor(String ref, int hotSpotX, int hotSpotY) throws SlickException
	{

	}

	@Override
	public void setMouseCursor(ImageData data, int hotSpotX, int hotSpotY) throws SlickException
	{

	}

	@Override
	public void setMouseCursor(Cursor cursor, int hotSpotX, int hotSpotY) throws SlickException
	{

	}

	@Override
	public void setDefaultMouseCursor()
	{

	}

	public boolean isFocused()
	{
		return focused;
	}

	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}
}
