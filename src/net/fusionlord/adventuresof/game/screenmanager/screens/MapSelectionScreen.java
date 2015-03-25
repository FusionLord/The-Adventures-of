package net.fusionlord.adventuresof.game.screenmanager.screens;

import net.fusionlord.adventuresof.game.entity.Player;
import net.fusionlord.adventuresof.game.screenmanager.Screen;
import net.fusionlord.adventuresof.game.screenmanager.screens.components.*;
import net.fusionlord.adventuresof.game.screenmanager.screens.world.WorldScreen;
import net.fusionlord.adventuresof.game.util.Reference;
import net.fusionlord.adventuresof.game.world.MapBase;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class MapSelectionScreen extends Screen
{
	String errorMsg;
	Player player;
	Menu   maps;
	Button play;
	Button editor;
	Button back;

	public MapSelectionScreen(GUIContext container, Player player)
	{
		super(container, "map_selection");
		this.player = player;
		backgroundColor = Color.darkGray;
	}

	@Override
	public void init(GUIContext container)
	{
		maps = new Menu(this);
		maps.setLocation(container.getWidth() / 2 - 150 / 2, 10 + container.getDefaultFont().getLineHeight());
		maps.setDimensions(150, container.getHeight() - 50 - container.getDefaultFont().getLineHeight() * 2);
		final File[] mapFiles = new File("./maps/").listFiles(
				new FileFilter()
				{
					@Override
					public boolean accept(File pathname)
					{
						return pathname.getName().endsWith(".tmx");
					}
				}
		);
		if (mapFiles != null && mapFiles.length > 0)
		{
			for (File map : mapFiles)
			{
				try
				{
					maps.addItem(new Map(this, new MapBase(container, map.getName())));
				}
				catch (SlickException e)
				{
					e.printStackTrace();
				}
			}
			maps.sortComponents();
		}

		int xOrigin = container.getWidth() / 2;
		int yOrigin = container.getHeight() - 30;

		back = new Button(
				this, xOrigin - 155, yOrigin, 100, 25, "Back", "redblackcloudy",
				new Action()
				{
					@Override
					public void doAction(BaseComponent component)
					{
						Reference.SCREENMANAGER.RemoveScreen((Screen) component.getParent());
						Reference.SCREENMANAGER.AddScreen(new CharacterSelectionScreen(component.getParent()));
					}
				}
		);
		editor = new Button(
				this, xOrigin - 50, yOrigin, 100, 25, "Editor", "redblackcloudy",
				new Action()
				{
					@Override
					public void doAction(BaseComponent component)
					{
						//TODO: ADD EDITOR
					}
				}
		);
		editor.setEnabled(false);
		play = new Button(this, xOrigin + 55, yOrigin, 100, 25, "Play!", "redblackcloudy",
						  new Action()
						  {
							  @Override
							  public void doAction(BaseComponent component)
							  {
								  Map map = (Map) maps.getSelectedItem();
								  if (map != null)
								  {
									  Reference.SCREENMANAGER.AddScreen(new WorldScreen(component.getParent(), map.getMap(), player));
								  }
								  else
								  {
									  errorMsg = "Please select a map to continue.";
								  }
							  }
						  }
		);

	}

	@Override
	public void update(GUIContext container, int delta)
	{
		maps.update(container, delta);
		back.update(container, delta);
		editor.update(container, delta);
		play.update(container, delta);
	}

	@Override
	public void drawForeground(GUIContext container, Graphics g)
	{
		g.setColor(Color.green);
		g.drawString("Map List", container.getWidth() / 2 - g.getFont().getWidth("Map List") / 2, 5);

		maps.draw(container, g);

		if (errorMsg != null && !errorMsg.equals("") )
		{
			g.setColor(Color.red);
			g.drawString(
					errorMsg,
					container.getWidth() / 2 - g.getFont().getWidth(errorMsg) / 2,
					container.getHeight() - g.getFont().getLineHeight() - 35
			);
		}

		back.draw(container, g);
		editor.draw(container, g);
		play.draw(container, g);
	}
}
