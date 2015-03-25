package net.fusionlord.adventuresof.game.util;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Textures
{

	private static final String PNG = "PNG";
	public static Texture missingTexture;

	public Textures()
	{
		try
		{
			missingTexture = TextureLoader.getTexture(
					PNG, ResourceLoader.getResourceAsStream(
							"assets/textures/missing.png"
					), GL11.GL_NEAREST
			);
		}
		catch (IOException e)
		{
			Log.error("NO MISSING TEXTURE FOUND!", e);
		}
	}

	public static void drawTexture(Graphics g, Texture texture, int x, int y)
	{
		drawTexture(g, texture, x, y, texture.getTextureWidth(), texture.getTextureHeight());
	}

	public static void drawTexture(Graphics g, Texture texture, int x, int y, int width, int height)
	{
		drawTexture(g, texture, x, y, width, height, 0, 0, width, height);
	}

	public static void drawTexture(Graphics g, Texture texture, int x, int y, int width, int height, float uMin, float vMin, float uMax, float vMax)
	{
		g.setColor(Color.white);
		texture.bind();

		float uMinf = uMin / (float) texture.getTextureWidth();
		float uMaxf = uMax / (float) texture.getTextureWidth();
		float vMinf = vMin / (float) texture.getTextureHeight();
		float vMaxf = vMax / (float) texture.getTextureHeight();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(uMinf, vMinf);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(uMinf + uMaxf, vMinf);
		GL11.glVertex2f(x + width, y);
		GL11.glTexCoord2f(uMinf + uMaxf, vMinf + vMaxf);
		GL11.glVertex2f(x + width, y + height);
		GL11.glTexCoord2f(uMinf, vMinf + vMaxf);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}

	public static Texture getTexture(String filename)
	{
		try
		{
			return TextureLoader.getTexture(
					PNG,
					ResourceLoader.getResourceAsStream("assets/textures/".concat(filename)),
					GL11.GL_NEAREST
			);
		}
		catch (IOException e)
		{
			Log.error("Could not find \"assets/textures/".concat(filename).concat("\" using missing texture."), e);
		}
		return missingTexture;
	}

	public static TileTexture getSkin(int skinIdx)
	{
		return new TileTexture(getTexture("characters/charset".concat(String.valueOf(skinIdx)).concat(".png")));
	}
}
