package net.fusionlord.adventuresof.game.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.GUIContext;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Player extends Entity
{
	public Player(String name)
	{
		super(name);
	}

	public Player(File character)
	{
		this("NULL");
		load(character);
	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void onDraw(GUIContext container, Graphics g)
	{

	}

	public void onLoad(ObjectInputStream ois)
	{
		//TODO : load stats
	}

	public void onSave(ObjectOutputStream oos)
	{
		//TODO : save stats
	}

	@Override
	protected String getDirectoryPrefix()
	{
		return "characters";
	}

	public void delete()
	{
		backup();
		getFile().delete();
	}

	private void backup()
	{
		try
		{
			File target = new File("characters/backups/".concat(getName()).concat(".ent"));
			if (!target.getParentFile().exists())
				target.getParentFile().mkdirs();
			Files.copy(getFile().toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
