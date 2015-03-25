package net.fusionlord.adventuresof.game.screenmanager.screens;

import net.fusionlord.adventuresof.game.screenmanager.Screen;
import net.fusionlord.adventuresof.game.screenmanager.screens.components.Action;
import net.fusionlord.adventuresof.game.screenmanager.screens.components.BaseComponent;
import net.fusionlord.adventuresof.game.screenmanager.screens.components.Button;
import net.fusionlord.adventuresof.game.util.Reference;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class ConfirmationDialog extends Screen //TODO: make dialog super
{

	private Button confirm, deny;
	private String message;
	private Action action;

	public ConfirmationDialog(GUIContext container, String message, Action action)
	{
		super(container, "confirmation_dialog");
		this.message = message;
		this.action = action;
	}

	@Override
	public void init(GUIContext container)
	{
		confirm = new Button(
				this,
				container.getWidth() / 2 - 105,
				container.getHeight() / 2 + 25,
				100,
				25,
				"Confirm",
				"redblackcloudy",
				new Action()
				{
					@Override
					public void doAction(BaseComponent component)
					{
						action.doAction(component);
					}
				}
		);

		deny = new Button(
				this,
				container.getWidth() / 2 + 5,
				container.getHeight() / 2 + 25,
				100,
				25,
				"Deny",
				"redblackcloudy",
				new Action()
				{
					@Override
					public void doAction(BaseComponent component)
					{
						Reference.SCREENMANAGER.RemoveScreen((Screen) component.getParent());
					}
				}
		);
	}

	@Override
	public void update(GUIContext container, int delta)
	{
		confirm.update(container, delta);
		deny.update(container, delta);
	}

	@Override
	public void drawBackground(GUIContext container, Graphics g)
	{
		int x = getWidth() / 2 - 200;
		int y = getHeight() / 2 - 50;
		g.setColor(Color.black);
		g.fillRoundRect(x, y, 400, 100, 6);
		g.setColor(Color.white);
		g.drawRoundRect(x, y, 400, 100, 6);
	}

	@Override
	public void drawForeground(GUIContext container, Graphics g)
	{
		int y = getHeight() / 2 - 50;

		int strWidth = g.getFont().getWidth(message);
		g.drawString(message, getWidth() / 2 - strWidth / 2, y + 25);

		confirm.draw(container, g);
		deny.draw(container, g);
	}
}
