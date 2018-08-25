package GG.SuperRonanCraft.SafeEconomy.resources;

import GG.SuperRonanCraft.SafeEconomy.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {
    private String pre = "Messages.";

    private FileConfiguration getConfig() {
        return Main.getInstance().getConfig();
    }

    public void getInvalid(CommandSender sendi, String label) {
        sms(sendi, getConfig().getString(pre + "Invalid").replaceAll("%command%", label));
    }

    public void getGivePlayerInvalid(CommandSender sendi, String player) {
        sms(sendi, getConfig().getString(pre + "Give.Player").replaceAll("%player%", player));
    }

    public void getGiveAmountInvalid(CommandSender sendi, String amount) {
        sms(sendi, getConfig().getString(pre + "Give.Amount").replaceAll("%amount%", amount));
    }

    public void getGiveAmountSuccess(CommandSender sendi, String player, String amount) {
        sms(sendi, getConfig().getString(pre + "Give.Success").replaceAll("%player%", player).replaceAll("%amount%",
                amount));
    }

    public void getBalance(CommandSender sendi, String amount) {
        sms(sendi, getConfig().getString(pre + "Balance").replaceAll("%amount%", amount));
    }

    public void getUsageGice(CommandSender sendi, String label) {
        sms(sendi, getConfig().getString(pre + "Usage.Give").replaceAll("%command%", label));
    }

    private void sms(CommandSender sendi, String msg) {
        sendi.sendMessage(color(msg));
    }

    public String colorPre(String msg) {
        return color(msg.replace("%prefix%", getPrefix()));
    }

    private String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', colorPre(msg));
    }

    private String getPrefix() {
        return getConfig().getString(pre + "Prefix");
    }
}
