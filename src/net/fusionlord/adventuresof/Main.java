package net.fusionlord.adventuresof;

import java.util.logging.Logger;
import java.util.logging.Level;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import net.fusionlord.adventuresof.game.Game;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Main
{
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game(""));
			appgc.setDisplayMode(640, 480, false);
			//appgc.setShowFPS(false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
