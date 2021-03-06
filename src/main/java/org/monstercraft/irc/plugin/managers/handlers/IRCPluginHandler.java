package org.monstercraft.irc.plugin.managers.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.monstercraft.irc.MonsterIRC;
import org.monstercraft.irc.ircplugin.IRC;
import org.monstercraft.irc.ircplugin.IRCPlugin;
import org.monstercraft.irc.ircplugin.PluginManifest;
import org.monstercraft.irc.ircplugin.service.FilePluginSource;
import org.monstercraft.irc.ircplugin.service.IRCPluginDefinition;
import org.monstercraft.irc.ircplugin.service.JavaCompiler;
import org.monstercraft.irc.plugin.Configuration;

public class IRCPluginHandler extends MonsterIRC {

	private final HashMap<Integer, IRCPlugin> pluginsToRun = new HashMap<Integer, IRCPlugin>();
	private final HashMap<Integer, Thread> pluginThreads = new HashMap<Integer, Thread>();
	private final List<IRCPluginDefinition> plugins;

	public IRCPluginHandler(MonsterIRC plugin) {
		loadSources();
		this.plugins = new ArrayList<IRCPluginDefinition>();
		plugins.addAll(new FilePluginSource(getPluginsFolder()).list());
		for (IRCPluginDefinition def : plugins) {
			try {
				IRC.log("Loading IRC plugin " + def.name + ".");
				runplugin(def.source.load(def));
			} catch (Exception e) {
				IRC.debug(e);
			}
		}

	}

	private static File getPluginsFolder() {
		return new File(Configuration.Paths.PLUGINS);
	}

	public void runplugin(IRCPlugin plugin) {
		PluginManifest prop = plugin.getClass().getAnnotation(
				PluginManifest.class);
		Thread t = new Thread(plugin, "plugin-" + prop.name());
		addpluginToPool(plugin, t);
		t.start();
	}

	private void addpluginToPool(IRCPlugin plugin, Thread t) {
		plugin.setID(pluginsToRun.size());
		pluginsToRun.put(pluginsToRun.size(), plugin);
		pluginThreads.put(pluginThreads.size(), t);
	}

	public void stopPlugins() {
		Thread curThread = Thread.currentThread();
		for (int i = 0; i < pluginsToRun.size(); i++) {
			IRCPlugin plugin = pluginsToRun.get(i);
			if (plugin != null && plugin.isActive()) {
				if (pluginThreads.get(i) == curThread) {
					stopPlugin(i);
				}
			}
		}
		if (curThread == null) {
			throw new ThreadDeath();
		}
	}

	public void stopPlugin(int id) {
		IRCPlugin plugin = pluginsToRun.get(id);
		if (plugin != null) {
			plugin.deactivate(id);
			pluginsToRun.remove(id);
			pluginThreads.remove(id);
		}
	}

	private void loadSources() {
		final String javaExt = ".java";
		File[] files = getPluginsFolder().listFiles();
		for (File f : files) {
			String name = f.getName();
			if (name.endsWith(javaExt) && !name.startsWith(".")
					&& !name.contains("!") && !name.contains("$")) {
				if (JavaCompiler.isAvailable()) {
					String classpath = Configuration.getClassPath() + ";"
							+ System.getProperty("java.class.path");
					boolean result = JavaCompiler.run(f, classpath);
					if (result) {
						IRC.log("Compiled: " + name);
					} else {
						IRC.log("Error compiling: " + name);
					}
				}
			}
		}
	}
}
