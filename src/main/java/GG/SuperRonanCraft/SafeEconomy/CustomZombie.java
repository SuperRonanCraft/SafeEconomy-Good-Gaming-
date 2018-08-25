package GG.SuperRonanCraft.SafeEconomy;

import net.minecraft.server.v1_8_R1.EntityZombie;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;

public class CustomZombie extends EntityZombie {

    public CustomZombie(World world) {
        super(((CraftWorld) world).getHandle());
        this.setBaby(true);
        this.setCustomName("SafeZombie");
        this.setCustomNameVisible(true);
    }
}
