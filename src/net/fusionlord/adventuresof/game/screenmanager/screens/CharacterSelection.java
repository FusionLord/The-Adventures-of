package net.fusionlord.adventuresof.game.screenmanager.screens;

import net.fusionlord.adventuresof.game.entity.Player;
import net.fusionlord.adventuresof.game.screenmanager.Screen;
import net.fusionlord.adventuresof.game.screenmanager.screens.components.*;
import net.fusionlord.adventuresof.game.screenmanager.screens.components.Character;
import net.fusionlord.adventuresof.game.util.Reference;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.ImageData;

import java.io.File;
import java.io.FileFilter;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class CharacterSelection extends Screen
{
	private Menu characters;
	private Button create, delete, play;
	private String errorMsg;

	public CharacterSelection(GUIContext container)
	{
		super(container, "character_selection");
	}

	@Override
	public void init(GUIContext container)
	{
		characters = new Menu(this);
		characters.setLocation(container.getWidth() / 2 - 150 / 2, 10 + container.getDefaultFont().getLineHeight());
		characters.setDimensions(150, container.getHeight() - 50 - container.getDefaultFont().getLineHeight() * 2);
		final File[] charactersFiles = new File("characters/").listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().endsWith(".ent");
			}
		});
		if(charactersFiles != null && charactersFiles.length > 0)
		{
			for(File character : charactersFiles)
			{
				Character characterComponent = new Character(this, new Player(character));
				characterComponent.setDimensions(100, 100);
				characters.addItem(characterComponent);
			}
		}

		int xOrigin = container.getWidth() / 2;
		int yOrigin = container.getHeight() - 30;

		create = new Button(this, xOrigin - 155, yOrigin, 100, 25, "Create", "redblackcloudy", new Action()
		{
			@Override
			public void doAction(BaseComponent component)
			{
				Reference.SCREENMANAGER.AddScreen(new CreateCharacter(component.getParent(), characters));
			}
		});
		delete = new Button(this, xOrigin - 50, yOrigin, 100, 25, "Delete", "redblackcloudy", new Action()
		{
			@Override
			public void doAction(BaseComponent component)
			{
				Character character = (Character) characters.getSelectedItem();
				if (character == null)
				{
					errorMsg = "Please select a character to delete.";
						return;
				}

				Reference.SCREENMANAGER.AddScreen(
						new ConfirmationDialog(component.getParent(),
								"Are you sure you want to delete ".concat(character.getName()).concat("?"),
								new Action()
								{
									@Override
									public void doAction(BaseComponent component)
									{
										Character character = (Character) characters.getSelectedItem();
										character.delete();
										characters.removeItem(character);
										Reference.SCREENMANAGER.RemoveScreen((Screen) component.getParent());
									}
								}
						));
			}
		});
		play = new Button(this, xOrigin + 55, yOrigin, 100, 25, "Play", "redblackcloudy", new Action()
		{
			@Override
			public void doAction(BaseComponent component)
			{
				Character character = (Character) characters.getSelectedItem();
				if (character == null)
				{
					errorMsg = "Please select a character to play.";
					return;
				}
				Reference.SCREENMANAGER.AddScreen(new MapSelectionScreen(component.getParent(), character.getPlayer()));
				Reference.setTitle(character.getName());
			}
		});
	}

	@Override
	public void update(GUIContext container, int delta)
	{
		characters.update(container, delta);
		create.update(container, delta);
		delete.update(container, delta);
		play.update(container, delta);
	}

	@Override
	public void draw(GUIContext container, Graphics g)
	{
		g.setColor(Color.green);
		g.drawString("Character List", container.getWidth() / 2 - g.getFont().getWidth("Character List") / 2, 5);

		characters.draw(container, g);

		if (errorMsg != "" && errorMsg != null)
		{
			g.setColor(Color.red);
			g.drawString(errorMsg, container.getWidth() / 2 - g.getFont().getWidth(errorMsg) / 2, container.getHeight() - g.getFont().getLineHeight() - 35);
		}

		create.draw(container, g);
		delete.draw(container, g);
		play.draw(container, g);
	}
}
