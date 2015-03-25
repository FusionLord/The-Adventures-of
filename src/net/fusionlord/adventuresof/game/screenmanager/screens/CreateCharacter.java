package net.fusionlord.adventuresof.game.screenmanager.screens;

import net.fusionlord.adventuresof.game.entity.Player;
import net.fusionlord.adventuresof.game.screenmanager.Screen;
import net.fusionlord.adventuresof.game.screenmanager.screens.components.*;
import net.fusionlord.adventuresof.game.screenmanager.screens.components.Character;
import net.fusionlord.adventuresof.game.util.Reference;
import net.fusionlord.adventuresof.game.util.Textures;
import net.fusionlord.adventuresof.game.util.TileTexture;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class CreateCharacter extends Screen
{

	private Player 		player;
	private Button 		confirm, cancel, skinNext, skinBack;
	private TextField   name;
	private Menu        characters;
	private int         skinIdx;
	private TileTexture skin;

	public CreateCharacter(GUIContext container, Menu characters)
	{
		super(container, "character_creation");
		this.characters = characters;
	}

	@Override
	public void init(GUIContext container)
	{
		player = new Player("NULL");
		name = new TextField(this);
		name.setLocation(container.getWidth() / 2 - 50, 40);
		name.setDimensions(100, 35);
		confirm = new Button(
				this, container.getWidth() / 2 - 105, container.getHeight() - 30, 100, 25, "Confirm", "redblackcloudy",
				new Action()
				{
					@Override
					public void doAction(BaseComponent component)
					{
						finalizeCharacter();
						Reference.SCREENMANAGER.RemoveScreen((Screen) component.getParent());
					}
				}
		);
		cancel = new Button(
				this, container.getWidth() / 2 + 5, container.getHeight() - 30, 100, 25, "Cancel", "redblackcloudy",
				new Action()
				{
					@Override
					public void doAction(BaseComponent component)
					{
						Reference.SCREENMANAGER.RemoveScreen((Screen) component.getParent());
					}
				}
		);
		skinBack = new Button(this, container.getWidth() / 2 - 130, 125, 100, 25, "<", "redblackcloudy",
							  new Action() {
								  @Override
								  public void doAction(BaseComponent component)
								  {
									  if (skinIdx - 1 >= 0)
									  {
										  skinIdx--;
									  }
									  updateSkin();
								  }
							  });
		skinNext = new Button(this, container.getWidth() / 2 + 31, 125, 100, 25, ">", "redblackcloudy",
							  new Action() {
								  @Override
								  public void doAction(BaseComponent component)
								  {
									  if (skinIdx + 1 <= 7)
									  {
										  skinIdx++;
									  }
									  updateSkin();
								  }
							  });
		skinIdx = 0;
		updateSkin();
	}

	private void updateSkin()
	{
		if (skinIdx < 0 || skinIdx > 7)
			return;
		skin = Textures.getSkin(skinIdx);
	}

	private void finalizeCharacter()
	{
		player.setName(name.getText());
		player.setSkinIdx(skinIdx);
		player.save();
		Character c = new Character(characters.getParent(), player);
		c.setDimensions(100, 100);
		characters.addItem(c);
	}

	@Override
	public void update(GUIContext container, int delta)
	{
		name.update(container, delta);
		confirm.update(container, delta);
		cancel.update(container, delta);
		skinBack.update(container, delta);
		skinNext.update(container, delta);
	}

	@Override
	public void drawForeground(GUIContext container, Graphics g)
	{
		name.draw(container, g);

		confirm.draw(container, g);
		cancel.draw(container, g);
		skinBack.draw(container, g);
		skin.drawPortrait(g, container.getWidth() / 2, 150 - 16);
		skinNext.draw(container, g);
	}
}
