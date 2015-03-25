package net.fusionlord.adventuresof.game.screenmanager.screens.components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public abstract class BaseComponent extends AbstractComponent
{

	private int     x;
	private int     y;
	private int     width;
	private int     height;
	private boolean selected, hovered, down, disabled, needsUpdate;
	private Action action;

	/**
	 * Create a new component
	 *
	 * @param container The container displaying this component
	 */
	public BaseComponent(GUIContext container)
	{
		super(container);
	}

	public void update(GUIContext container, int delta)
	{
		handleInput(container.getInput());
		onUpdate(container, delta);
	}

	/**
	 * Call update(), NOT this.
	 */
	protected abstract void onUpdate(GUIContext container, int delta);

	protected abstract void handleInput(Input input);

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException
	{
		draw(container, g);
	}

	public final void draw(GUIContext container, Graphics g)
	{
		Rectangle oldClip = g.getClip();
		g.setClip(getX(), getY(), getWidth(), getHeight());

		drawBackground(container, g);
		drawForeground(container, g);

		g.setClip(oldClip);
	}

	public abstract void drawBackground(GUIContext container, Graphics g);

	public abstract void drawForeground(GUIContext container, Graphics g);

	@Override
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setDimensions(int width, int height)
	{
		this.width = width;
		this.height = height;
		needsUpdate = true;
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	public boolean getDown()
	{
		return down;
	}

	public void setDown(boolean down)
	{
		this.down = down;
	}

	public boolean getSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public boolean getHovered()
	{
		return hovered;
	}

	public void setHovered(boolean hovered)
	{
		this.hovered = hovered;
	}

	public boolean isMouseHovering(Input input)
	{
		return input.getMouseX() > x && input.getMouseX() < x + width && input.getMouseY() > y && input.getMouseY() < y + height;
	}

	public GUIContext getParent()
	{
		return this.container;
	}

	public boolean isEnabled()
	{
		return !disabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.disabled = !enabled;
	}

	public boolean hasAction()
	{
		return action != null;
	}

	public void setAction(Action action)
	{
		this.action = action;
	}

	public void doAction()
	{
		action.doAction(this);
	}

	public boolean needsUpdate()
	{
		if (needsUpdate)
		{
			needsUpdate = false;
			return true;
		}
		return false;
	}
}
