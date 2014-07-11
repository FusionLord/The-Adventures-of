package net.fusionlord.adventuresof.game.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.Log;

import java.io.*;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public abstract class Entity
{
	private String name;
	private long age;

	public Entity(String name)
	{
		this.name = name;
	}

	public void update()
	{
		age++;
		onUpdate();
	}

	public abstract void onUpdate();

	public void draw(GUIContext container, Graphics g)
	{
		onDraw(container, g);
	}

	public abstract void onDraw(GUIContext container, Graphics g);

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
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	protected abstract void onLoad(ObjectInputStream ois);

	public void save()
	{
		File file = new File(getDirectoryPrefix().concat("/").concat(this.name).concat(".ent"));
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(name);
			oos.writeLong(age);

			onSave(oos);

			oos.close();
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

	protected abstract void onSave(ObjectOutputStream oos);

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
}
