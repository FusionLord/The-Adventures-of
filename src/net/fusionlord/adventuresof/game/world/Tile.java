package net.fusionlord.adventuresof.game.world;


/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */

public class Tile
{
	private int tileIndex;
	private boolean isBlocked;
	private boolean hasTriggerScript;
	private String triggerScript;

	@Override
	public String toString() {
		return new StringBuffer(" tileIndex : ").append(this.tileIndex)
				.append(" isBlocked : ").append(this.isBlocked)
				.append(" hasTriggerScript : ").append(this.hasTriggerScript)
				.append(" triggerScript : ").append(this.triggerScript)
				.toString();
	}
}
