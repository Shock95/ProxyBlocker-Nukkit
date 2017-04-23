package ProxyBlocker;

public class InfoEntry {

	public boolean status;
	public String kickMessage;
	public String opMessage;

	public InfoEntry(boolean status, String kickMessage, String opMessage) {
		this.status = status;
		this.kickMessage = kickMessage;
		this.opMessage = opMessage;
	}

	public boolean hasVPN() {
		return this.status;
	}

	public String getKickMessage() {
		return this.kickMessage;
	}

	public String getOpMessage() {
		return this.opMessage;
	}
}