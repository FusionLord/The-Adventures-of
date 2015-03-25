package net.fusionlord.adventuresof.game.world;

import net.fusionlord.adventuresof.game.entity.Player;
import net.fusionlord.adventuresof.game.screenmanager.screens.world.WorldScreen;
import net.fusionlord.adventuresof.game.util.Textures;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class MapBase extends TiledMap
{

	public int OBJECTLAYER = getLayerIndex("objects");
	public int BACKGROUNDLAYER = getLayerIndex("background");
	public int FOREGROUNDLAYER = getLayerIndex("foreground");

	public int viewTileWidth, viewTileHeight, halfViewTileWidth, halfViewTileHeight;

	private int drawX, drawY;
	private int scale = 2;

	public MapBase(GUIContext container, String ref) throws SlickException
	{
		super("./maps/".concat(ref));

		viewTileWidth = container.getWidth() / getTileWidth() / scale;
		viewTileHeight = container.getHeight() / getTileHeight() / scale;
		if (viewTileWidth % 2 != 0)
		{
			viewTileWidth++;
		}
		if (viewTileHeight % 2 != 0)
		{
			viewTileHeight++;
		}
		halfViewTileWidth = viewTileWidth / 2;
		halfViewTileHeight = viewTileHeight / 2;
	}

	public void update(GUIContext container, int delta, Player player)
	{
		drawX = player.getxPos() - halfViewTileWidth - 1;
		drawY = player.getyPos() - halfViewTileHeight - 1;
	}

	public boolean isBlocked(int x, int y)
	{
		return x < 0 || x > getWidth() - 1 || y < 0 || y > getHeight() - 1 || getTileProperty(getTileId(x, y, OBJECTLAYER), "isSolid", "false").equals("true");
	}

	public boolean hasScript(int x, int y)
	{
		return getTileProperty(getTileId(x, y, OBJECTLAYER), "hasScript", "false").equals("true");
	}

	public void executeSctipt(int x, int y, WorldScreen world)
	{
		if (hasScript(x, y))
		{
			String script = getTileProperty(getTileId(x, y, OBJECTLAYER), "script", "null");
			String[] args = script.split("|");
			if (args[0].startsWith("t:"))
			{
				String[] teleArgs = args[0].split(":");
				if (teleArgs[1].equalsIgnoreCase("m"))
				{
					world.setMap(args[1]);
				}
			}
		}
	}

	public void drawBackground(GUIContext container, Graphics g)
	{
		if (BACKGROUNDLAYER != -1)
		{
//			if (OBJECTLAYER != -1) //TODO: find less frame intensive way to draw background;
//			{
//				for (int y = 0; y < viewTileHeight; y++)
//				{
//					for (int x = 0; x > viewTileWidth; x++)
//					{
//						Image back = getTileImage(0, 0, BACKGROUNDLAYER);
//						Image object = getTileImage(0, 0, OBJECTLAYER);
//					}
//				}
//			}
			render(
					-getTileWidth(),
					-getTileHeight(),
					drawX,
					drawY,
					viewTileWidth + 1,
					viewTileHeight + 1,
					BACKGROUNDLAYER,
					false
			);
		}
	}

	public void drawObjects(GUIContext container, Graphics g)
	{
		if (OBJECTLAYER != -1)
		{
			render(-getTileWidth(), -getTileHeight(), drawX, drawY, viewTileWidth + 1, viewTileHeight + 1, OBJECTLAYER, false);
		}
	}

	public void drawForeground(GUIContext container, Graphics g)
	{
		if (FOREGROUNDLAYER != -1)
		{
			render(-getTileWidth(), -getTileHeight(), drawX, drawY, viewTileWidth + 1, viewTileHeight + 1, FOREGROUNDLAYER, false);
		}
	}

	public int getScale()
	{
		return scale;
	}

	public float getReverseScale()
	{
		return 1 / scale;
	}

	public void scale(Graphics g)
	{
		g.scale(scale, scale);
	}

	public void descale(Graphics g)
	{
		g.scale(1f / scale, 1f / scale);
	}

	public int getHalfViewTileWidth()
	{
		return halfViewTileWidth;
	}

	public int getHalfViewTileHeight()
	{
		return halfViewTileHeight;
	}
}
