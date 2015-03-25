package net.fusionlord.adventuresof.game.util;

import net.fusionlord.adventuresof.game.screenmanager.ScreenManager;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Reference
{

	public static ScreenManager SCREENMANAGER;
	public static Textures      TEXTURES;
	public static boolean       isDebug;
	private static String TITLE = "The Adventures of...";
	public static boolean drawGrid;
	public static boolean drawCrosshair;

	public static void toggleDebug()
	{
		isDebug = !isDebug;
	}

	public static void setTitle(String name)
	{
		TITLE = "The Adventures of ".concat(name);
	}

	public static String getTITLE()
	{
		return TITLE;
	}

	public static void toggleGrid()
	{
		drawGrid = !drawGrid;
	}

	public static void toggleCrosshair()
	{
		drawCrosshair = !drawCrosshair;
	}
}
