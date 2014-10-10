package com.clashwars.cwreward.commands;

import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwreward.CWReward;
import com.clashwars.cwreward.config.PluginCfg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {
    private CWReward cwr;
    private PluginCfg cfg;

    public Commands(CWReward cwr) {
        this.cwr = cwr;
        this.cfg = cwr.getCfg();
    }


    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("somecmd")) {
            if (args.length >= 1) {
                //##########################################################################################################################
                //###################################################### some cmd ######################################################
                //##########################################################################################################################
                if (args[0].equalsIgnoreCase("somearg")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(CWUtil.formatCWMsg("Player command only."));
                        return true;
                    }
                    Player player = (Player) sender;

                    return true;
                }
            }
            sender.sendMessage(CWUtil.integrateColor("&8===== &4&lCommand Help &6/" + label + " &8====="));
            sender.sendMessage(CWUtil.integrateColor("&6/" + label + " &8- &5Show this page."));
            sender.sendMessage(CWUtil.integrateColor("&6/" + label + " somearg &8- &5desc."));
            return true;
        }
        return false;
    }
}
