package net.fusionlord.adventuresof.game.screenmanager.screens.components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.FontUtils;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Label extends BaseComponent
{

	/**
	 * Create a new component
	 *
	 * @param container The container displaying this component
	 */
	public Label(GUIContext container, int x, int y, String text, FontUtils.Alignment align)
	{
		super(container);
	}

	@Override
	protected void onUpdate(GUIContext container, int delta)
	{

	}

	@Override
	protected void handleInput(Input input)
	{

	}

	@Override
	public void drawBackground(GUIContext container, Graphics g)
	{

	}

	@Override
	public void drawForeground(GUIContext container, Graphics g)
	{

	}
}
