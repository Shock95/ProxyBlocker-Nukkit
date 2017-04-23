package ProxyBlocker;

import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerLoginEvent;

public class EventListener implements Listener {

    private Main plugin;
    
    public EventListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        this.plugin.checkForProxy(event.getPlayer());
    }
}