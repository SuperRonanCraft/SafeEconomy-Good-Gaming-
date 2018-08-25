package GG.SuperRonanCraft.SafeEconomy.events;

import GG.SuperRonanCraft.SafeEconomy.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void join(PlayerJoinEvent e) {
        Main.getInstance().getData().downloadPlayer(e.getPlayer().getUniqueId().toString());
    }
}
