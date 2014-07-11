package net.fusionlord.adventuresof.game.screenmanager.screens.world;

import net.fusionlord.adventuresof.game.screenmanager.Screen;
import net.fusionlord.adventuresof.game.world.MapBase;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.opengl.ImageData;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class WorldScreen extends Screen
{
	private MapBase map;

	public WorldScreen(GUIContext container, MapBase map)
	{
		super(container, "world");
		this.map = map;
		map.load();
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
