package GG.SuperRonanCraft.SafeEconomy.events;

import GG.SuperRonanCraft.SafeEconomy.CustomZombie;
import GG.SuperRonanCraft.SafeEconomy.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EconomyCMD implements CommandExecutor, TabCompleter {

    private String[] cmds = {"zombie", "give"};

    public boolean onCommand(CommandSender sendi, Command cmd, String label, String[] args) {
        if (args.length >= 1)
            if (args[0].equalsIgnoreCase(cmds[0]))
                zombie(sendi, label, args);
            else if (args[0].equalsIgnoreCase(cmds[1]))
                give(sendi, label, args);
        return true;
    }

    public List<String> onTabComplete(CommandSender sendi, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<String>();
        if (args.length == 1) {
            //Spill out all commands that starts with the input
            for (String s : cmds)
                if (s.startsWith(args[0].toLowerCase()) && getPermOf(sendi, s))
                    list.add(s);
        } else if (args.length == 2) {
            //List all players that starts with the input
            if (args[0].equalsIgnoreCase(cmds[1]))
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getName().startsWith(args[1].toLowerCase()))
                        list.add(p.getName());
        } else if (args.length == 3) {
            //List all players that starts with the input
            if (args[0].equalsIgnoreCase(cmds[1]))
                for (int i = 1; i < 10; i++)
                    list.add(Integer.toString(i * 10));
        }
        return list;
    }

    private void zombie(CommandSender sendi, String label, String[] args) {
        if (!isPlayer(sendi))
            return;
        if (args.length == 1) {
            //Spawn zombie at player location
            CustomZombie zombie = new CustomZombie(((Player) sendi).getWorld());
            Location loc = ((Player) sendi).getLocation();
            zombie.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            zombie.getWorld().addEntity(zombie);
        } else
            invalid(sendi, label);
    }

    private void give(CommandSender sendi, String label, String[] args) {
        if (args.length == 3) {
            //If null, player is offline or does not exist
            Player p = Bukkit.getPlayer(args[1]);
            Main pl = Main.getInstance();
            if (p != null) {
                int amount = getInt(args[2]);
                if (amount >= 1) {
                    pl.getData().addBalance(p, amount);
                    pl.getMsg().getGiveAmountSuccess(sendi, p.getName(), Integer.toString(amount));
                } else
                    pl.getMsg().getGiveAmountInvalid(sendi, args[2]);
            } else
                pl.getMsg().getGivePlayerInvalid(sendi, args[1]);
        } else
            usage(sendi, label, cmds[1]);
    }

    private void invalid(CommandSender sendi, String label) {
        Main.getInstance().getMsg().getInvalid(sendi, label);
    }

    private void usage(CommandSender sendi, String label, String cmd) {
        if (cmd.equalsIgnoreCase(cmds[1]))
            Main.getInstance().getMsg().getUsageGice(sendi, label);
        else
            sendi.sendMessage("Something went wrong!");
    }

    //Make sure the value is a positive integer
    private int getInt(String value) {
        try {
            return Integer.getInteger(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean isPlayer(CommandSender sendi) {
        if (sendi instanceof Player)
            return true;
        else
            sendi.sendMessage("Only players can execute this command!");
        return false;
    }

    private boolean getPermOf(CommandSender sendi, String cmd) {
        if (cmd.equalsIgnoreCase(cmds[0]))
            return Main.getInstance().getPerms().getZombie(sendi);
        else if (cmd.equalsIgnoreCase(cmds[1]))
            return Main.getInstance().getPerms().getEco(sendi);
        return false;
    }
}
