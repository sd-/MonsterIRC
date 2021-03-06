package org.monstercraft.irc.plugin.command.irccommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.monstercraft.irc.MonsterIRC;
import org.monstercraft.irc.ircplugin.IRC;
import org.monstercraft.irc.plugin.command.IRCCommand;
import org.monstercraft.irc.plugin.util.Variables;
import org.monstercraft.irc.plugin.wrappers.IRCChannel;
import org.monstercraft.irc.plugin.wrappers.IRCCommandSender;

public class Other extends IRCCommand {

	@Override
	public boolean canExecute(String sender, String message, IRCChannel channel) {
		return MonsterIRC.getHandleManager().getIRCHandler()
				.isConnected(MonsterIRC.getIRCServer());
	}

	@Override
	public boolean execute(String sender, String message, IRCChannel channel) {
		if (MonsterIRC.getHandleManager().getIRCHandler().isOp(channel, sender)) {
			if (channel.getOpCommands().contains("*")) {
				try {
					IRCCommandSender console = new IRCCommandSender(sender);
					Bukkit.getServer().dispatchCommand(
							console,
							message.substring(message
									.indexOf(Variables.commandPrefix) + 1));
					return true;
				} catch (CommandException e) {
					IRC.debug(e);
					MonsterIRC
							.getHandleManager()
							.getIRCHandler()
							.sendNotice(
									sender,
									sender
											+ ": Error executing ingame command! "
											+ e.toString());
				}
			} else if (!channel.getOpCommands().isEmpty()) {
				for (String s : channel.getOpCommands()) {
					String lol = message.substring(message
							.indexOf(Variables.commandPrefix) + 1);
					if (lol != null) {
						if (lol.toLowerCase().startsWith(s.toLowerCase())) {
							try {
								IRCCommandSender console = new IRCCommandSender(
										sender);
								Bukkit.getServer()
										.dispatchCommand(
												console,
												message.substring(message
														.indexOf(Variables.commandPrefix) + 1));
								return true;
							} catch (CommandException e) {
								IRC.debug(e);
								MonsterIRC
										.getHandleManager()
										.getIRCHandler()
										.sendNotice(
												sender,
												sender
														+ ": Error executing ingame command! "
														+ e.toString());
							}
						}

					}
				}
				if (!channel.getVoiceCommands().isEmpty()) {
					for (String s : channel.getVoiceCommands()) {
						String lol = message.substring(message
								.indexOf(Variables.commandPrefix) + 1);
						if (lol != null) {
							if (lol.toLowerCase().startsWith(s.toLowerCase())) {
								try {
									IRCCommandSender console = new IRCCommandSender(
											sender);
									Bukkit.getServer()
											.dispatchCommand(
													console,
													message.substring(message
															.indexOf(Variables.commandPrefix) + 1));
									return true;
								} catch (CommandException e) {
									IRC.debug(e);
									MonsterIRC
											.getHandleManager()
											.getIRCHandler()
											.sendMessage(
													sender
															+ ": Error executing ingame command! "
															+ e.toString(),
													sender);
								}
							}

						}
					}
				}
				if (!channel.getUserCommands().isEmpty()) {
					for (String s : channel.getUserCommands()) {
						String lol = message.substring(message
								.indexOf(Variables.commandPrefix) + 1);
						if (lol != null) {
							if (lol.toLowerCase().startsWith(s.toLowerCase())) {
								try {
									IRCCommandSender console = new IRCCommandSender(
											sender);
									Bukkit.getServer()
											.dispatchCommand(
													console,
													message.substring(message
															.indexOf(Variables.commandPrefix) + 1));
									return true;
								} catch (CommandException e) {
									IRC.debug(e);
									MonsterIRC
											.getHandleManager()
											.getIRCHandler()
											.sendMessage(
													sender
															+ ": Error executing ingame command! "
															+ e.toString(),
													sender);
								}
							}

						}
					}
				}
				IRC.sendNotice(sender, "You cannot use that command from IRC.");
			} else {
				IRC.sendNotice(sender,
						"You are not allowed to execute that command from IRC.");
				return true;
			}
		} else if (MonsterIRC.getHandleManager().getIRCHandler()
				.isVoice(channel, sender)) {
			if (channel.getVoiceCommands().contains("*")) {
				try {
					IRCCommandSender console = new IRCCommandSender(sender);
					Bukkit.getServer().dispatchCommand(
							console,
							message.substring(message
									.indexOf(Variables.commandPrefix) + 1));
					return true;
				} catch (CommandException e) {
					IRC.debug(e);
					MonsterIRC
							.getHandleManager()
							.getIRCHandler()
							.sendMessage(
									sender
											+ ": Error executing ingame command! "
											+ e.toString(), sender);
				}
			} else if (!channel.getVoiceCommands().isEmpty()) {
				for (String s : channel.getVoiceCommands()) {
					String lol = message.substring(message
							.indexOf(Variables.commandPrefix) + 1);
					if (lol != null) {
						if (lol.toLowerCase().startsWith(s.toLowerCase())) {
							try {
								IRCCommandSender console = new IRCCommandSender(
										sender);
								Bukkit.getServer()
										.dispatchCommand(
												console,
												message.substring(message
														.indexOf(Variables.commandPrefix) + 1));
								return true;
							} catch (CommandException e) {
								IRC.debug(e);
								MonsterIRC
										.getHandleManager()
										.getIRCHandler()
										.sendMessage(
												sender
														+ ": Error executing ingame command! "
														+ e.toString(), sender);
							}
						}

					}
				}
				if (!channel.getUserCommands().isEmpty()) {
					for (String s : channel.getUserCommands()) {
						String lol = message.substring(message
								.indexOf(Variables.commandPrefix) + 1);
						if (lol != null) {
							if (lol.toLowerCase().startsWith(s.toLowerCase())) {
								try {
									IRCCommandSender console = new IRCCommandSender(
											sender);
									Bukkit.getServer()
											.dispatchCommand(
													console,
													message.substring(message
															.indexOf(Variables.commandPrefix) + 1));
									return true;
								} catch (CommandException e) {
									IRC.debug(e);
									MonsterIRC
											.getHandleManager()
											.getIRCHandler()
											.sendMessage(
													sender
															+ ": Error executing ingame command! "
															+ e.toString(),
													sender);
								}
							}

						}
					}
				}
				IRC.sendNotice(sender, "You cannot use that command from IRC.");
			} else {
				IRC.sendNotice(sender,
						"You are not allowed to execute that command from IRC.");
				return true;
			}
		} else {
			if (channel.getUserCommands().contains("*")) {
				try {
					IRCCommandSender console = new IRCCommandSender(sender);
					Bukkit.getServer().dispatchCommand(
							console,
							message.substring(message
									.indexOf(Variables.commandPrefix) + 1));
					return true;
				} catch (CommandException e) {
					IRC.debug(e);
					MonsterIRC
							.getHandleManager()
							.getIRCHandler()
							.sendMessage(
									sender
											+ ": Error executing ingame command! "
											+ e.toString(), sender);
				}
			} else if (!channel.getUserCommands().isEmpty()) {
				for (String s : channel.getUserCommands()) {
					String lol = message.substring(message
							.indexOf(Variables.commandPrefix) + 1);
					if (lol != null) {
						if (lol.toLowerCase().startsWith(s.toLowerCase())) {
							try {
								IRCCommandSender console = new IRCCommandSender(
										sender);
								Bukkit.getServer()
										.dispatchCommand(
												console,
												message.substring(message
														.indexOf(Variables.commandPrefix) + 1));
								return true;
							} catch (CommandException e) {
								IRC.debug(e);
								MonsterIRC
										.getHandleManager()
										.getIRCHandler()
										.sendMessage(
												sender
														+ ": Error executing ingame command! "
														+ e.toString(), sender);
							}
						}

					}
				}
				IRC.sendNotice(sender, "You cannot use that command from IRC.");
			} else {
				IRC.sendNotice(sender,
						"You are not allowed to execute that command from IRC.");
				return true;
			}
		}
		return false;
	}
}
