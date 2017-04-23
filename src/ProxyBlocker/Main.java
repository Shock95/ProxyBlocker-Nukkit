package ProxyBlocker;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import ProxyBlocker.task.ProxyChecker;

import java.util.concurrent.ThreadLocalRandom;

public class Main extends PluginBase {

    private Server server;
    private String email;

    public void onEnable() {
        this.saveDefaultConfig();
        (this.server = this.getServer()).getPluginManager().registerEvents(new EventListener(this), this);
        this.server.getLogger().info("ProxyBlocker is now enabled.");
        String[] emails = {"gmail.com", "yahoo.com", "hotmail.com"};
        String random = "email" +  ThreadLocalRandom.current().nextInt(10) + ThreadLocalRandom.current().nextInt(10) + ThreadLocalRandom.current().nextInt(10) + ThreadLocalRandom.current().nextInt(10) + "@" + emails[ThreadLocalRandom.current().nextInt(2)];
        this.email = this.getConfig().getString("email") == null ? random : this.getConfig().getString("email");
    }
    
    public void checkForProxy(Player player) {
        this.server.getScheduler().scheduleAsyncTask(new ProxyChecker(new String[]{player.getAddress(), player.getName(), this.email}));
    }
}