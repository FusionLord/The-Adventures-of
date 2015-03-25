package net.fusionlord.adventuresof.game.screenmanager.screens.components;

import net.fusionlord.adventuresof.game.util.Reference;
import net.fusionlord.adventuresof.game.util.Textures;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.Texture;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Button extends BaseComponent
{

	private Texture normalTexture, hoverTexture, downTexture;
	private String text;

	/**
	 * @param container The parent container;
	 * @param x         The x coordinate of this button;
	 * @param y         The y coordinate of this button;
	 * @param width     The width of this button;
	 * @param height    The height of this button;
	 * @param text      The text displayed on the button;
	 * @param texture   The name texture of the button. (Ex: *.png *_hover.png *_down.png)
	 * @param action    The action the the button that preforms;
	 */
	public Button(GUIContext container, int x, int y, int width, int height, String text, String texture, Action action)
	{
		super(container);
		setLocation(x, y);
		setDimensions(width, height);
		this.text = text;
		this.normalTexture = Reference.TEXTURES.getTexture("buttons/".concat(texture).concat(".png"));
		this.hoverTexture = Reference.TEXTURES.getTexture("buttons/".concat(texture).concat("_hover.png"));
		this.downTexture = Reference.TEXTURES.getTexture("buttons/".concat(texture).concat("_down.png"));
		setAction(action);
	}

	@Override
	protected void onUpdate(GUIContext container, int delta)
	{}

	@Override
	protected void handleInput(Input input)
	{
		if (isMouseHovering(input))
		{
			if (!input.isMouseButtonDown(0))
			{
				if (!getHovered())
				{
					setHovered(true);
				}
				if (getDown())
				{
					setDown(false);
				}
			}
			else
			{
				setDown(true);
			}
			if (input.isMousePressed(0))
			{
				doAction();
			}
		}
		else if (!isMouseHovering(input))
		{
			setHovered(false);
		}
	}

	@Override
	public void drawBackground(GUIContext container, Graphics g)
	{
		if (getDown() && !isEnabled())
		{
			Textures.drawTexture(g, downTexture, getX(), getY());
		}
		else if (getHovered())
		{
			Textures.drawTexture(g, hoverTexture, getX(), getY());
		}
		else
		{
			Textures.drawTexture(g, normalTexture, getX(), getY());
		}
	}

	@Override
	public void drawForeground(GUIContext container, Graphics g)
	{
		Font font = g.getFont();
		g.drawString(
				text,
				getX() + (getWidth() / 2 - font.getWidth(text) / 2),
				getY() + (getHeight() / 2 - font.getHeight(text) / 2)
		);
	}
}
