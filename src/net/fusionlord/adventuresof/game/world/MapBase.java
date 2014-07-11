package net.fusionlord.adventuresof.game.world;

import net.fusionlord.adventuresof.game.util.Reference;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class MapBase
{
	private String name;
	private int width;
	private int height;
	private int tileSize;
	private int tileSet;
	private Tile[][] tileList;

	public MapBase(String fileName)
	{
		//MapLoader.loadMap(fileName, this);
		name = fileName;
	}

	public void load()
	{
		Reference.TEXTURES.changeWorldTileset(tileSet);
	}

	public String getName()
	{
		return name;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setTile(int x, int y, Tile tile)
	{
		tileList[x][y] = tile;
	}

	public Tile getTile(int x, int y)
	{
		return tileList[x][y];
	}

	@Override
	public String toString() {
		return new StringBuffer(" name : ").append(this.name)
						.append(" width : ").append(this.width)
						.append(" height : ").append(this.height)
						.append(" tileSize : ").append(this.tileSize)
						.append(" tileSet : ").append(this.tileSet)
						.toString();
	}
}
