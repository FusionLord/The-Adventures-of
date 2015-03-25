package net.fusionlord.adventuresof.game.screenmanager.screens.components;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class TextField extends BaseComponent
{

	private static final int     INITIAL_KEY_REPEAT_INTERVAL = 400;
	private static final int     KEY_REPEAT_INTERVAL         = 50;
	private              int     maxCharacter                = 10000;
	private              String  value                       = "";
	private              Color   border                      = Color.white;
	private              Color   text                        = Color.white;
	private              Color   background                  = Color.black;
	private              boolean realFocus                   = false;
	private              boolean visibleCursor               = true;
	private              int     lastKey                     = -1;
	private              char    lastChar                    = 0;
	private				 int	 lastUpdate;
	private long   repeatTimer;
	private String oldText;
	private int    cursorPos;
	private int    oldCursorPos;
	private Font   font;

	public TextField(GUIContext container)
	{
		super(container);
		font = container.getDefaultFont();
	}

	@Override
	protected void onUpdate(GUIContext container, int delta)
	{
		if ((lastUpdate += delta) >= 500)
		{
			lastUpdate = 0;
			visibleCursor = !visibleCursor;
		}
	}

	@Override
	protected void handleInput(Input input)
	{
		if (isMouseHovering(input))
		{
			if (input.isMousePressed(0))
			{
				realFocus = true;
			}
		}

		if (lastKey != -1)
		{
			if (input.isKeyDown(lastKey))
			{
				if (repeatTimer < System.currentTimeMillis())
				{
					repeatTimer = System.currentTimeMillis() + KEY_REPEAT_INTERVAL;
					keyPressed(lastKey, lastChar);
				}
			}
			else
			{
				lastKey = -1;
			}
		}
	}

	@Override
	public void drawBackground(GUIContext container, Graphics g)
	{
		g.setColor(background);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	public void drawForeground(GUIContext container, Graphics g)
	{
		g.setColor(text);
		int cpos = font.getWidth(value.substring(0, cursorPos));
		int tx = 0;
		if (cpos > getWidth())
		{
			tx = getWidth() - cpos - font.getWidth("_");
		}

		g.translate(tx + 2, 0);
		g.drawString(value, getX() + 1, getY() + 1);

		if (realFocus && visibleCursor)
		{
			g.drawString("_", getX() + 1 + cpos + 2, getY() + 1);
		}

		g.translate(-tx - 2, 0);

		if (border != null)
		{
			g.setColor(border);
			g.drawRect(getX(), getY(), getWidth(), getHeight());
		}
		g.setColor(text);
	}

	public String getText()
	{
		return value;
	}

	public void setText(String value)
	{
		this.value = value;
		if (cursorPos > value.length())
		{
			cursorPos = value.length();
		}
	}

	public void setCursorPos(int pos)
	{
		cursorPos = pos;
		if (cursorPos > value.length())
		{
			cursorPos = value.length();
		}
	}

	public void setCursorVisible(boolean visibleCursor)
	{
		this.visibleCursor = visibleCursor;
	}

	public void setMaxLength(int length)
	{
		maxCharacter = length;
		if (value.length() > maxCharacter)
		{
			value = value.substring(0, maxCharacter);
		}
	}

	protected void doPaste(String text)
	{
		recordOldPosition();

		for (int i = 0; i < text.length(); i++)
		{
			keyPressed(-1, text.charAt(i));
		}
	}

	protected void recordOldPosition()
	{
		oldText = getText();
		oldCursorPos = cursorPos;
	}

	protected void doUndo(int oldCursorPos, String oldText)
	{
		if (oldText != null)
		{
			setText(oldText);
			setCursorPos(oldCursorPos);
		}
	}

	public void keyPressed(int key, char c)
	{
		if (realFocus)
		{
			if (key != -1)
			{
				if ((key == Input.KEY_V) &&
						((input.isKeyDown(Input.KEY_LCONTROL)) || (input.isKeyDown(Input.KEY_RCONTROL))))
				{
					String text = Sys.getClipboard();
					if (text != null)
					{
						doPaste(text);
					}
					return;
				}
				if ((key == Input.KEY_Z) &&
						((input.isKeyDown(Input.KEY_LCONTROL)) || (input.isKeyDown(Input.KEY_RCONTROL))))
				{
					if (oldText != null)
					{
						doUndo(oldCursorPos, oldText);
					}
					return;
				}

				// alt and control keys don't come through here
				if (input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL))
				{
					return;
				}
				if (input.isKeyDown(Input.KEY_LALT) || input.isKeyDown(Input.KEY_RALT))
				{
					return;
				}
			}

			if (lastKey != key)
			{
				lastKey = key;
				repeatTimer = System.currentTimeMillis() + INITIAL_KEY_REPEAT_INTERVAL;
			}
			else
			{
				repeatTimer = System.currentTimeMillis() + KEY_REPEAT_INTERVAL;
			}
			lastChar = c;

			if (key == Input.KEY_LEFT)
			{
				if (cursorPos > 0)
				{
					cursorPos--;
				}
			}
			else if (key == Input.KEY_RIGHT)
			{
				if (cursorPos < value.length())
				{
					cursorPos++;
				}
			}
			else if (key == Input.KEY_BACK)
			{
				if ((cursorPos > 0) && (value.length() > 0))
				{
					if (cursorPos < value.length())
					{
						value = value.substring(0, cursorPos - 1)
								+ value.substring(cursorPos);
					}
					else
					{
						value = value.substring(0, cursorPos - 1);
					}
					cursorPos--;
				}
			}
			else if (key == Input.KEY_DELETE)
			{
				if (value.length() > cursorPos)
				{
					value = value.substring(0, cursorPos) + value.substring(cursorPos + 1);
				}
			}
			else if ((c < 127) && (c > 31) && (value.length() < maxCharacter))
			{
				if (cursorPos < value.length())
				{
					value = value.substring(0, cursorPos) + c
							+ value.substring(cursorPos);
				}
				else
				{
					value = value.substring(0, cursorPos) + c;
				}
				cursorPos++;
			}
		}
	}
}

