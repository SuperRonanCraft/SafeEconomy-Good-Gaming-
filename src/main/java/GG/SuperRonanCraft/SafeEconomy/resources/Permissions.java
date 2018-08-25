package GG.SuperRonanCraft.SafeEconomy.resources;

import org.bukkit.command.CommandSender;

public class Permissions {

    public boolean getZombie(CommandSender sendi){
        return sendi.hasPermission("safeeconomy.zombie");
    }

    public boolean getEco(CommandSender sendi){
        return sendi.hasPermission("safeeconomy.command.eco");
    }
}
