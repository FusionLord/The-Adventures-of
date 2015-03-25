package net.fusionlord.adventuresof.game.screenmanager.screens.world;

import net.fusionlord.adventuresof.game.entity.Player;
import net.fusionlord.adventuresof.game.screenmanager.Screen;
import net.fusionlord.adventuresof.game.util.Reference;
import net.fusionlord.adventuresof.game.world.MapBase;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class WorldScreen extends Screen
{

	private MapBase map;
	private Player  player;

	public WorldScreen(GUIContext container, MapBase map, Player player)
	{
		super(container, "world");
		this.map = map;
		this.player = player;
		this.backgroundColor = Color.black;
	}

	@Override
	public void init(GUIContext container)
	{
		player.setxPos(Integer.parseInt(map.getMapProperty("spawnX", "0")));
		player.setyPos(Integer.parseInt(map.getMapProperty("spawnY", "0")));
	}

	@Override
	public void update(GUIContext container, int delta)
	{
		player.update(container, delta, map);
		map.executeSctipt(player.getxPos(), player.getyPos(), this);
		map.update(container, delta, player);
	}

	@Override
	protected void drawBackground(GUIContext container, Graphics g)
	{
		super.drawBackground(container, g);
		map.scale(g);
		map.drawBackground(container, g);
		map.descale(g);
	}

	@Override
	public void drawForeground(GUIContext container, Graphics g)
	{
		map.scale(g);
		map.drawObjects(container, g);
		player.draw(container, g, map);
		map.drawForeground(container, g);
		if (Reference.drawGrid)
		{
			g.setColor(Color.black);
			for (int i = 0; i < container.getWidth() / map.getTileWidth() + 1; i++)
			{
				g.drawLine(0, i * map.getTileHeight(), container.getWidth(), i * map.getTileHeight() + 1);
			}
			for (int i = 0; i < container.getHeight() / map.getTileHeight() + 1; i++)
			{
				g.drawLine(i * map.getTileWidth(), 0, i * map.getTileWidth() + 1, container.getHeight());
			}
		}
		map.descale(g);
		if (Reference.drawCrosshair)
		{
			g.setColor(Color.red);
			g.drawLine(container.getWidth() / 2, 0, container.getWidth() / 2 + 1, container.getHeight());
			g.drawLine(0, container.getHeight() / 2, container.getWidth(), container.getHeight() / 2 + 1);
		}
		Reference.SCREENMANAGER.debugScreen.addInfo(String.format("PlayerX: %s PlayerY:%s", player.getxPos(), player.getyPos()));
		g.setColor(Color.black);
	}

	public void setMap(String map)
	{
		try
		{
			this.map = new MapBase(this.container, map.concat(".txm"));
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}
