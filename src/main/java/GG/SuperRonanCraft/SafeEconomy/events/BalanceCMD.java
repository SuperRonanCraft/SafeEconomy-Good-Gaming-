package GG.SuperRonanCraft.SafeEconomy.events;

import GG.SuperRonanCraft.SafeEconomy.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sendi, Command cmd, String label, String[] args) {
        if (getPerm(sendi))
            return false;
        if (args.length == 0)
            balance(sendi, label, args);
        return true;
    }

    private void balance(CommandSender sendi, String label, String[] args) {
        if (!isPlayer(sendi))
            return;
        if (args.length == 1)
            Main.getInstance().getMsg().getBalance(sendi, Integer.toString(Main.getInstance().getData().getBalance(
                    (Player) sendi)));
        else
            invalid(sendi, label);
    }

    private void invalid(CommandSender sendi, String label) {
        Main.getInstance().getMsg().getInvalid(sendi, label);
    }

    private boolean isPlayer(CommandSender sendi) {
        if (sendi instanceof Player)
            return true;
        else
            sendi.sendMessage("Only players can execute this command!");
        return false;
    }

    private boolean getPerm(CommandSender sendi) {
        return Main.getInstance().getPerms().getEco(sendi);
    }
}
