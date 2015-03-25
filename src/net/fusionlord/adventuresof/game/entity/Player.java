package net.fusionlord.adventuresof.game.entity;

import net.fusionlord.adventuresof.game.screenmanager.screens.components.Character;
import net.fusionlord.adventuresof.game.util.Textures;
import net.fusionlord.adventuresof.game.world.MapBase;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	public void onUpdate(GUIContext container, int delta, MapBase map)
	{}

	@Override
	protected void handleInput(Input input, MapBase map)
	{
		if (!isWalking())
		{
			if (input.isKeyDown(Input.KEY_RIGHT))
			{
				face(Direction.RIGHT);
				if (!map.isBlocked(xPos + 1, yPos))
				{
					move(Direction.RIGHT);
				}
			}
			if (input.isKeyDown(Input.KEY_LEFT))
			{
				face(Direction.LEFT);
				if (!map.isBlocked(xPos - 1, yPos))
				{
					move(Direction.LEFT);
				}
			}
			if (input.isKeyDown(Input.KEY_UP))
			{
				face(Direction.UP);
				if (!map.isBlocked(xPos, yPos - 1))
				{
					move(Direction.UP);
				}
			}
			if (input.isKeyDown(Input.KEY_DOWN))
			{
				face(Direction.DOWN);
				if (!map.isBlocked(xPos, yPos + 1))
				{
					move(Direction.DOWN);
				}
			}
		}
	}

	@Override
	public void drawEntityBackground(GUIContext container, Graphics g)
	{}

	@Override
	public void drawEntity(GUIContext container, Graphics g, MapBase map)
	{
		if (skin == null)
		{
			skin = Textures.getSkin(getSkinIdx());
		}
		skin.draw(g, map.getHalfViewTileWidth() * map.getTileWidth(), map.getHalfViewTileHeight() * map.getTileWidth(), map.getTileWidth(), map.getTileHeight(), skinX + 1, skinY);
		g.setColor(Color.black);
	}

	@Override
	public void drawEntityForeground(GUIContext container, Graphics g)
	{}

	public void drawOnCharacterComponent(GUIContext container, Graphics g, Character character)
	{
		if (skin == null)
		{
			skin = Textures.getSkin(getSkinIdx());
		}
		skin.drawPortrait(
				g,
				character.getX() + character.getWidth() / 2,
				character.getY() + character.getHeight() / 2 + 5
		);
	}

	public void onLoad(ObjectInputStream ois) throws IOException
	{
		//TODO : load stats
		setSkinIdx(ois.readInt());
	}

	public void onSave(ObjectOutputStream oos) throws IOException
	{
		//TODO : save stats
		oos.writeInt(getSkinIdx());
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
			{
				target.getParentFile().mkdirs();
			}
			Files.copy(getFile().toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
