package Models;

/**
 * Created by fernando on 24-04-2017.
 */
public class Hop {
    private String IPaddress;
    private String next_hop;

    public String getIPaddress() {
        return IPaddress;
    }

    public void setIPadress(String IPaddress) {
        this.IPaddress = IPaddress;
    }

    public String getNext_hop() {
        return next_hop;
    }

    public void setNext_hop(String next_hop) {
        this.next_hop = next_hop;
    }

    public Hop(String IPaddress, String next_hop) {
        this.IPaddress = IPaddress;
        this.next_hop = next_hop;
    }
}
