package GG.SuperRonanCraft.SafeEconomy.events;

import GG.SuperRonanCraft.SafeEconomy.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void leave(PlayerQuitEvent e) {
        Main.getInstance().getData().uploadPlayer(e.getPlayer().getUniqueId().toString());
    }
}
