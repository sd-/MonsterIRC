package org.monstercraft.irc.ircplugin;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.monstercraft.irc.MonsterIRC;
import org.monstercraft.irc.plugin.util.ChatType;
import org.monstercraft.irc.plugin.util.IRCColor;
import org.monstercraft.irc.plugin.util.StringUtils;
import org.monstercraft.irc.plugin.util.Variables;
import org.monstercraft.irc.plugin.wrappers.IRCChannel;

import com.gmail.nossr50.mcPermissions;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class IRC {

	private final static Logger logger = Logger.getLogger(IRC.class
			.getSimpleName());

	/**
	 * Fetches the logger.
	 * 
	 * @return The logger.
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Logs a message to the console.
	 * 
	 * @param msg
	 *            The message to print.
	 */
	public static void log(final String msg) {
		logger.log(Level.INFO, "[MonsterIRC] " + msg);
	}

	/**
	 * Logs debugging messages to the console.
	 * 
	 * @param error
	 *            The message to print.
	 */
	public static void debug(final String error, final boolean console) {
		if (console) {
			logger.log(Level.WARNING, "[MonsterIRC - Debug] " + error);
		}
	}

	/**
	 * Sends a message to the MonsterIRC channel.
	 * 
	 * @param channel
	 *            The channel to send it to.
	 * @param message
	 *            The message to send.
	 */
	public static void sendMessage(final IRCChannel channel,
			final String message) {
		MonsterIRC.getHandleManager().getIRCHandler()
				.sendMessage(channel.getChannel(), message);
	}

	/**
	 * Sends a message to the MonsterIRC channel.
	 * 
	 * @param channel
	 *            The channel to send it to.
	 * @param message
	 *            The message to send.
	 */
	public static void kick(final String channel, final String nick,
			final String reason) {
		MonsterIRC.getHandleManager().getIRCHandler()
				.kick(MonsterIRC.getIRCServer(), nick, channel, reason);
	}

	/**
	 * Sends a message to the MonsterIRC channel.
	 * 
	 * @param channel
	 *            The channel to send it to.
	 * @param message
	 *            The message to send.
	 */
	public static void ban(final String channel, final String nick) {
		MonsterIRC.getHandleManager().getIRCHandler()
				.ban(MonsterIRC.getIRCServer(), nick, channel);
	}

	/**
	 * Sends a message to the MonsterIRC channel.
	 * 
	 * @param channel
	 *            The channel to send it to.
	 * @param message
	 *            The message to send.
	 */
	public static void mode(final String channel, final String nick,
			final String mode) {
		MonsterIRC.getHandleManager().getIRCHandler()
				.mode(MonsterIRC.getIRCServer(), nick, channel, mode);
	}

	/**
	 * Sends a message to the MonsterIRC channel.
	 * 
	 * @param to
	 *            The person to send the message to.
	 * @param message
	 *            The message to send.
	 */
	public static void sendMessage(final String to, final String message) {
		MonsterIRC.getHandleManager().getIRCHandler().sendMessage(to, message);
	}

	/**
	 * Sends a message to a user on the MonsterIRC server.
	 * 
	 * @param to
	 *            The user to send it to.
	 * @param message
	 *            The message to send.
	 */
	public static void sendNotice(final String to, final String message) {
		MonsterIRC.getHandleManager().getIRCHandler().sendNotice(to, message);
	}

	/**
	 * Logs debugging messages to the console.
	 * 
	 * @param error
	 *            The message to print.
	 */
	public static void debug(final Exception error) {
		logger.log(Level.SEVERE, "[MonsterIRC - Critical error detected!]");
		error.printStackTrace();
	}

	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("MonsterIRC");
	}

	/**
	 * Handles a message accoradingly.
	 * 
	 * @param c
	 *            The IRCChannel to handle the message for.
	 * @param name
	 *            The sender's name.
	 * @param message
	 *            The message to handle.
	 */
	public static void sendGameMessage(final IRCChannel c, final String name,
			final String message) {
		try {
			if (c.getChatType() == ChatType.ADMINCHAT) {
				if (MonsterIRC.getHookManager().getmcMMOHook() != null) {
					String format = "�b" + "{" + "�f" + "[IRC] "
							+ StringUtils.getPrefix(name)
							+ StringUtils.getName(name)
							+ StringUtils.getSuffix(name) + "�b" + "} "
							+ message;
					for (Player p : getPlugin().getServer().getOnlinePlayers()) {
						if (p.isOp()
								|| mcPermissions.getInstance().adminChat(p))
							p.sendMessage(IRCColor.formatIRCMessage(format));
					}
				}
			} else if (c.getChatType() == ChatType.HEROCHAT && !Variables.hc4) {
				c.getHeroChatChannel().announce(
						IRCColor.formatIRCMessage(Variables.mcformat
								.replace("{name}", StringUtils.getName(name))
								.replace("{message}", message)
								.replace("{prefix}",
										StringUtils.getPrefix(name))
								.replace("{suffix}",
										StringUtils.getSuffix(name))
								.replace("{groupPrefix}",
										StringUtils.getGroupPrefix(name))
								.replace("{groupSuffix}",
										StringUtils.getGroupSuffix(name))
								.replace("{world}", StringUtils.getWorld(name))
								.replace("&", "�")));
			} else if (c.getChatType() == ChatType.HEROCHAT
					&& MonsterIRC.getHookManager().getHeroChatHook() != null
					&& Variables.hc4) {
				c.getHeroChatFourChannel().sendMessage(
						IRCColor.formatIRCMessage(Variables.mcformat
								.replace("{name}", StringUtils.getName(name))
								.replace("{message}", "")
								.replace(":", "")
								.replace("{prefix}",
										StringUtils.getPrefix(name))
								.replace("{suffix}",
										StringUtils.getSuffix(name))
								.replace("{groupPrefix}",
										StringUtils.getGroupPrefix(name))
								.replace("{groupSuffix}",
										StringUtils.getGroupSuffix(name))
								.replace("{world}", StringUtils.getWorld(name))
								.replace("&", "�")),
						IRCColor.formatIRCMessage(message),
						c.getHeroChatFourChannel().getMsgFormat(), false);
			} else if (c.getChatType() == ChatType.GLOBAL) {
				getPlugin().getServer().broadcastMessage(
						IRCColor.formatIRCMessage(Variables.mcformat
								.replace("{name}", StringUtils.getName(name))
								.replace(
										"{message}",
										IRCColor.WHITE.getMinecraftColor()
												+ message)
								.replace("{prefix}",
										StringUtils.getPrefix(name))
								.replace("{suffix}",
										StringUtils.getSuffix(name))
								.replace("{groupPrefix}",
										StringUtils.getGroupPrefix(name))
								.replace("{groupSuffix}",
										StringUtils.getGroupSuffix(name))
								.replace("{world}", StringUtils.getWorld(name))
								.replace("&", "�")));
			} else if (c.getChatType() == ChatType.TOWNYCHAT) {
				for (Player p : TownyUniverse.getOnlinePlayers()) {
					TownyMessaging.sendMessage(p, IRCColor.formatIRCMessage(c
							.getTownyChannel().getChannelTag()
							.replace("&", "�")
							+ Variables.mcformat
									.replace("{name}",
											StringUtils.getName(name))
									.replace(
											"{message}",
											c.getTownyChannel()
													.getMessageColour()
													+ message)
									.replace("{prefix}",
											StringUtils.getPrefix(name))
									.replace("{suffix}",
											StringUtils.getSuffix(name))
									.replace("{groupPrefix}",
											StringUtils.getGroupPrefix(name))
									.replace("{groupSuffix}",
											StringUtils.getGroupSuffix(name))
									.replace("{world}",
											StringUtils.getWorld(name))
									.replace("&", "�")));
				}
			} else if (c.getChatType() == ChatType.NONE) {
				return;
			}
		} catch (Exception e) {
			IRC.debug(e);
		}
	}

}
