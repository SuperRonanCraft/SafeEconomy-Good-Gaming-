package GG.SuperRonanCraft.SafeEconomy;

import GG.SuperRonanCraft.SafeEconomy.events.BalanceCMD;
import GG.SuperRonanCraft.SafeEconomy.events.EconomyCMD;
import GG.SuperRonanCraft.SafeEconomy.events.Join;
import GG.SuperRonanCraft.SafeEconomy.events.Leave;
import GG.SuperRonanCraft.SafeEconomy.resources.Messages;
import GG.SuperRonanCraft.SafeEconomy.resources.MysqlDatabase;
import GG.SuperRonanCraft.SafeEconomy.resources.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {

    //Commands
    private EconomyCMD economyCMD = new EconomyCMD();
    private BalanceCMD balanceCMD = new BalanceCMD();
    //Messages
    private Permissions perms = new Permissions();
    private Messages msg = new Messages();
    //Data
    private MysqlDatabase data = new MysqlDatabase();
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        data.load();
        //Set Peaceful mode
        for (World world : Bukkit.getWorlds())
            world.setDifficulty(Difficulty.PEACEFUL);
    }

    @Override
    public void onDisable() {
        data.disconnect();
    }

    private void registerCommands(CommandSender sendi, Command cmd, String label, String[] args) {
        Bukkit.getPluginCommand("economy").setExecutor(economyCMD);
        Bukkit.getPluginCommand("economy").setTabCompleter(economyCMD);
        Bukkit.getPluginCommand("balance").setExecutor(balanceCMD);
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Leave(), this);
        Bukkit.getPluginManager().registerEvents(new Join(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public MysqlDatabase getData() {
        return data;
    }

    public Messages getMsg() {
        return msg;
    }

    public Permissions getPerms() {
        return perms;
    }
}
