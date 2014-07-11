package net.fusionlord.adventuresof.game.screenmanager.screens;

import net.fusionlord.adventuresof.game.entity.Player;
import net.fusionlord.adventuresof.game.screenmanager.Screen;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.ImageData;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class MapSelectionScreen extends Screen
{
	Player player;

	public MapSelectionScreen(GUIContext container, Player player)
	{
		super(container, "map_selection");
		this.player = player;
	}

	@Override
	public void init(GUIContext container)
	{

	}

	@Override
	public void update(GUIContext container, int delta)
	{

	}

	@Override
	public void draw(GUIContext container, Graphics g)
	{

	}
}
