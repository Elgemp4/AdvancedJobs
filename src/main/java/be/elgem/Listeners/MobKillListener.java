package be.elgem.Listeners;

import be.elgem.Jobs.Misc.EXpMethod;
import be.elgem.Jobs.Player.ServerWideJobHandler;
import be.elgem.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobKillListener implements Listener {
    @EventHandler
    public void onMobKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) {return;}
        if(!ListenerManager.canGainExperience(event.getEntity().getKiller())) {return;}

        String mobName = event.getEntity().getType().toString();
        Player player = event.getEntity().getKiller();

        Main.getMain().getServerWideJobHandler().getPlayerJobsHandler(player.getUniqueId()).addXpToJob(EXpMethod.KILL, mobName);
    }
}
