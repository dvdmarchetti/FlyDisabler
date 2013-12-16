/*
 * FlyDisabler: to manage fly related things on bukkit server.
 * Copyright (C) 2013 _abc33_
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.github.abc33;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyDisabler extends JavaPlugin implements Listener {
	public final File fConfig = new File(getDataFolder(), "config.yml");
	public final String prefix = ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "FlyDisabler" + ChatColor.DARK_GREEN + "] " + ChatColor.YELLOW;
	public final PluginManager pm = Bukkit.getServer().getPluginManager();
	public Logger log = Bukkit.getLogger();

	@Override
	public void onEnable() {
		log.info("[FlyDisabler] Plugin author: _abc33_");
		log.info("[FlyDisabler] Checking configuration...");
		createConfig();
		
		/* Event Handlers */
		pm.registerEvents(this, this);
	}
	
	private void createConfig(){
		if (!getDataFolder().exists()){
			log.info("[FlyDisabler] Data folder not found! Attempting to create it...");
			getDataFolder().mkdir();
			if (!getDataFolder().exists()){
				log.log(Level.SEVERE, "[FlyDisabler] Unable to create data folder!");
			}
		}
		if (getDataFolder().listFiles().length == 0){
			log.info("[FlyDisabler] Default configuration created.");
			List<String> defWorlds = Arrays.asList("world1","world2");
			this.getConfig().set("worldsDisableFly", defWorlds);
		
			this.saveConfig();
		}
	}

	@EventHandler
	public void onPlayerFly(PlayerToggleFlightEvent event){
		Player p = event.getPlayer();
		String pWorld = p.getWorld().getName();

		List<String> worlds = getConfig().getStringList("worldsDisableFly");
		for (String wName : worlds){
			if (wName.equals(pWorld) && !(p.isOp()) && !(p.hasPermission("flydisabler.exempt"))){
				p.setFlying(false);
				p.setAllowFlight(false);
				p.sendMessage(this.prefix + "You can't fly in this world!");
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("flydisabler") || label.equalsIgnoreCase("fd")) {
			if (args.length > 0){
				if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
					if (sender.hasPermission("flydisabler.admin.reload") || sender.isOp()) {
						reloadConfig();
						sender.sendMessage(this.prefix + "Configuration reloaded");
					} else {
						sender.sendMessage(this.prefix + "You don't have permission to do this!");
					}
				} else {
					sender.sendMessage(this.prefix + "Command not found!");
				}
			} else {
				sender.sendMessage(this.prefix + "Author: _abc33_ || Version: 0.1");
			}
			return true;
		}
		return false;
	}
}
