package ProxyBlocker.task;

import cn.nukkit.Player;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import ProxyBlocker.InfoEntry;

import java.net.URL;
import java.nio.charset.Charset;
import java.lang.StringBuffer;
import java.util.Collections;
import java.io.Reader;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ProxyChecker extends AsyncTask {

    private String[] data;

    public ProxyChecker(String[] data) {
        this.data = data;
    }

    public void onRun() {
        JsonElement object = null;
        InputStream is = null;
        try {
            is = new URL("http://legacy.iphub.info/api.php?ip=" + data[0] + "&showtype=4&email=" + data[2]).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = readAll(rd);
            object = new JsonParser().parse(json);
        } catch(IOException e) {
            e.printStackTrace();
        }
        boolean check = object.getAsJsonObject().get("proxy").getAsInt() == 1;
        String msg = TextFormat.GOLD + "Please turn your VPN off if you want to continue playing.";
        this.setResult(new InfoEntry(check, new String(new char[new Double(msg.length() / 2.25).intValue()]).replace("\0", " ") + TextFormat.RED + "VPN Detected.\n" + msg, TextFormat.ITALIC + (TextFormat.GRAY + "Kicked " + TextFormat.AQUA + data[1] + TextFormat.GRAY + " for using a proxy.")));
    }

    public void onCompletion(Server server) {
        if(this.getResult() instanceof InfoEntry) {
            InfoEntry res = (InfoEntry) this.getResult();
            if(res.hasVPN()) {
                server.getPlayerExact(data[1]).kick(res.getKickMessage(), false);
                for(String op : server.getOps().getAll().keySet()) {
                    Player pl = server.getPlayer(op);
                    if(pl instanceof Player) {
                        pl.sendMessage(res.getOpMessage());
                    }
                }
            }
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while((cp = rd.read()) != -1) {
          sb.append((char)cp);
        }
        return sb.toString();
    }
}
