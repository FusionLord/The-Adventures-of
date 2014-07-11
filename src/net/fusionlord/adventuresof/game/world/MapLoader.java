package net.fusionlord.adventuresof.game.world;

import java.io.*;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class MapLoader
{
	public static void loadMap(String fileName, MapBase map)
	{
		try
		{
			FileInputStream fileIn = new FileInputStream(new File("maps/".concat(fileName)));
			ObjectInputStream ois = new ObjectInputStream(fileIn);

			map = (MapBase) ois.readObject();

			for (int x = 0; x < map.getWidth(); x++)
			{
				for (int y = 0; y < map.getHeight(); y++)
				{
					map.setTile(x, y, (Tile) ois.readObject());
				}
			}
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void saveMap(MapBase map)
	{
		FileOutputStream fileOut = null;
		ObjectOutputStream oos;
		try
		{
			fileOut = new FileOutputStream(new File("maps/".concat(map.getName()).concat(".map")));
			oos = new ObjectOutputStream(fileOut);
			//TODO: WRITE FILE INFO TO FILE!!!

			oos.writeObject(map);

			for (int x = 0; x < map.getWidth(); x++)
			{
				for (int y = 0; y < map.getHeight(); y++)
				{
					oos.writeObject(map.getTile(x, y));
				}
			}
			oos.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
