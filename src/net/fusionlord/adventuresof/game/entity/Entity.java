package net.fusionlord.adventuresof.game.entity;

import net.fusionlord.adventuresof.game.util.TileTexture;
import net.fusionlord.adventuresof.game.world.MapBase;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;

import java.io.*;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public abstract class Entity
{
	protected enum Direction
	{
		RIGHT,
		LEFT,
		UP,
		DOWN,;
	}

	protected String      name;
	protected long        age;
	protected long		  startedWalking;
	protected TileTexture skin;
	protected int         skinIdx;
	protected int         skinX;
	protected int         skinY;
	protected int         xPos;
	protected int         yPos;
	protected boolean walking;

	public Entity(String name)
	{
		this.name = name;
	}

	public void update(GUIContext container, int delta, MapBase map)
	{
		age++;
		onUpdate(container, delta, map);
		handleInput(container.getInput(), map);
		if (walking && age > startedWalking)
		{
			walking = false;
		}
	}

	public abstract void onUpdate(GUIContext container, int delta, MapBase map);

	protected abstract void handleInput(Input input, MapBase map);

	public void draw(GUIContext container, Graphics g, MapBase map)
	{
		drawEntityBackground(container, g);
		drawEntity(container, g, map);
		drawEntityForeground(container, g);
	}

	public abstract void drawEntityBackground(GUIContext container, Graphics g);

	public abstract void drawEntity(GUIContext container, Graphics g, MapBase map);

	public abstract void drawEntityForeground(GUIContext container, Graphics g);

	public void load(File file)
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);

			name = (String) ois.readObject();
			age = ois.readLong();

			onLoad(ois);

			ois.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	protected abstract void onLoad(ObjectInputStream ois) throws IOException;

	public void save()
	{
		File file = new File(getDirectoryPrefix().concat("/").concat(this.name).concat(".ent"));
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(name);
			oos.writeLong(age);

			onSave(oos);

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

	public void face(Direction direction)
	{
		switch (direction)
		{
			case RIGHT:
				skinY = 2;
				break;
			case LEFT:
				skinY = 1;
				break;
			case UP:
				skinY = 3;
				break;
			case DOWN:
				skinY = 0;
				break;
		}
	}

	protected void move(Direction direction)
	{
		switch (direction)
		{
			case RIGHT:
				xPos++;
				break;
			case LEFT:
				xPos--;
				break;
			case UP:
				yPos--;
				break;
			case DOWN:
				yPos++;
				break;
		}
		startedWalking = age + 75;
		walking = true;
	}

	protected abstract void onSave(ObjectOutputStream oos) throws IOException;

	public File getFile()
	{
		return new File(getDirectoryPrefix().concat("/").concat(name).concat(".ent"));
	}

	protected abstract String getDirectoryPrefix();

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getxPos()
	{
		return xPos;
	}

	public void setxPos(int xPos)
	{
		this.xPos = xPos;
	}

	public int getyPos()
	{
		return yPos;
	}

	public void setyPos(int yPos)
	{
		this.yPos = yPos;
	}

	public int getSkinIdx()
	{
		return skinIdx;
	}

	public void setSkinIdx(int skinIdx)
	{
		this.skinIdx = skinIdx;
	}

	public boolean isWalking()
	{
		return walking;
	}

}
