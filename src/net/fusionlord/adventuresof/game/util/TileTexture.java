package net.fusionlord.adventuresof.game.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.opengl.Texture;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class TileTexture implements Texture
{

	protected int     tileWidth;
	protected int     tileHeight;
	protected Texture texture;

	public TileTexture(Texture texture)
	{
		this.texture = texture;
		tileWidth = getImageWidth() / 3;
		tileHeight = getImageHeight() / 4;
	}

	public void drawPortrait(Graphics g, int x, int y)
	{
		draw(g, x - 32, y - 32, 64, 64, 1, 0);
	}

	public void draw(Graphics g, int x, int y, int width, int height, int xIndex, int yIndex)
	{
		Textures.drawTexture(
				g,
				texture,
				x,
				y,
				width,
				height,
				xIndex * tileWidth,
				yIndex * tileHeight,
				tileWidth,
				tileHeight
		);
	}

	@Override
	public boolean hasAlpha()
	{
		return texture.hasAlpha();
	}

	@Override
	public String getTextureRef()
	{
		return texture.getTextureRef();
	}

	@Override
	public void bind()
	{
		texture.bind();
	}

	@Override
	public int getImageHeight()
	{
		return texture.getImageHeight();
	}

	@Override
	public int getImageWidth()
	{
		return texture.getImageWidth();
	}

	@Override
	public float getHeight()
	{
		return texture.getHeight();
	}

	@Override
	public float getWidth()
	{
		return texture.getWidth();
	}

	@Override
	public int getTextureHeight()
	{
		return texture.getTextureHeight();
	}

	@Override
	public int getTextureWidth()
	{
		return texture.getTextureWidth();
	}

	@Override
	public void release()
	{
		texture.release();
	}

	@Override
	public int getTextureID()
	{
		return texture.getTextureID();
	}

	@Override
	public byte[] getTextureData()
	{
		return texture.getTextureData();
	}

	@Override
	public void setTextureFilter(int textureFilter)
	{
		texture.setTextureFilter(textureFilter);
	}
}
