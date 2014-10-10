package com.clashwars.cwreward.commands;

import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwreward.CWReward;
import com.clashwars.cwreward.config.PluginCfg;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Commands {
    private CWReward cwr;
    private PluginCfg cfg;

    public Commands(CWReward cwr) {
        this.cwr = cwr;
        this.cfg = cwr.getCfg();
    }


    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //##########################################################################################################################
        //############################################### /reward {player} {reward} ################################################
        //##########################################################################################################################
        if (label.equalsIgnoreCase("reward")) {
            if (!sender.isOp() && !sender.hasPermission("cw.reward")) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInsufficient permissions."));
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInvalid command usage. &4/" + label + " {player} {reward}"));
                sender.sendMessage(CWUtil.formatCWMsg("&cIf force is true it will make sure there is at least 1 reward."));
                return true;
            }

            OfflinePlayer oPlayer = cwr.getServer().getOfflinePlayer(args[0]);
            if (oPlayer == null || !oPlayer.hasPlayedBefore()) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInvalid player."));
                return true;
            }

            String result = cwr.getRM().giveSpecificReward(oPlayer.getName(), args[1]);
            if (result != "") {
                sender.sendMessage(CWUtil.formatCWMsg("&6Given &a" + result + " &6as reward to &5" + oPlayer.getName() + "&6."));
                if (oPlayer.isOnline()) {
                    ((Player)oPlayer).sendMessage(CWUtil.formatCWMsg("&6You have been rewarded &a" + result + "&6!"));
                }
            } else {
                sender.sendMessage(CWUtil.formatCWMsg("&cNo reward found with this name."));
            }
            return true;
        }



        //##########################################################################################################################
        //############################################ /randomreward {player} {reward} #############################################
        //##########################################################################################################################
        if (label.equalsIgnoreCase("randomreward")) {
            if (!sender.isOp() && !sender.hasPermission("cw.reward")) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInsufficient permissions."));
                return true;
            }

            if (args.length < 1) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInvalid command usage. &4/" + label + " {player} [category=*]"));
                return true;
            }

            OfflinePlayer oPlayer = cwr.getServer().getOfflinePlayer(args[0]);
            if (oPlayer == null || !oPlayer.hasPlayedBefore()) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInvalid player."));
                return true;
            }

            if (args.length > 1 && cwr.getRM().getRewardsByCategory(args[1]).size() <= 0) {
                sender.sendMessage(CWUtil.formatCWMsg("&cNo category found with this name."));
                return true;
            }

            String result = cwr.getRM().giveRandomReward(oPlayer.getName(), args.length > 1 ? args[1] : "*");
            if (result != "") {
                sender.sendMessage(CWUtil.formatCWMsg("&6Given &a" + result + " &6as reward to &5" + oPlayer.getName() + "&6."));
                if (oPlayer.isOnline()) {
                    ((Player)oPlayer).sendMessage(CWUtil.formatCWMsg("&6You have been rewarded &a" + result + "&6!"));
                }
            } else {
                sender.sendMessage(CWUtil.formatCWMsg("&cNo reward given."));
            }
            return true;
        }



        //##########################################################################################################################
        //########################################## /rewards {player} [category] [force] ##########################################
        //##########################################################################################################################
        if (label.equalsIgnoreCase("rewards")) {
            if (!sender.isOp() && !sender.hasPermission("cw.reward")) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInsufficient permissions."));
                return true;
            }

            if (args.length < 1) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInvalid command usage. &4/" + label + " {player} [category=*] [force=false]"));
                return true;
            }

            OfflinePlayer oPlayer = cwr.getServer().getOfflinePlayer(args[0]);
            if (oPlayer == null || !oPlayer.hasPlayedBefore()) {
                sender.sendMessage(CWUtil.formatCWMsg("&cInvalid player."));
                return true;
            }

            if (args.length > 1 && cwr.getRM().getRewardsByCategory(args[1]).size() <= 0) {
                sender.sendMessage(CWUtil.formatCWMsg("&cNo category found with this name."));
                return true;
            }

            boolean force = false;
            if (args.length > 2 && (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("yes") || args[2].equalsIgnoreCase("force"))) {
                force = true;
            }

            List<String> result = cwr.getRM().giveRewards(oPlayer.getName(), args.length > 1 ? args[1] : "*", force);
            if (result.size() > 0) {
                sender.sendMessage(CWUtil.formatCWMsg("&6Given the following rewards to &5" + oPlayer.getName() + "&8: &a" + CWUtil.implode(result, "&8, &a")));
                if (oPlayer.isOnline()) {
                    ((Player)oPlayer).sendMessage(CWUtil.formatCWMsg("&6You received the following rewards&8: &a" + CWUtil.implode(result, "&8, &a")));
                }
            } else {
                sender.sendMessage(CWUtil.formatCWMsg("&cNo rewards given."));
                if (oPlayer.isOnline()) {
                    if (!force) {
                        ((Player)oPlayer).sendMessage(CWUtil.formatCWMsg("&cYou didn't get any rewards. :("));
                    }
                }
            }
            return true;
        }

        return false;
    }
}
