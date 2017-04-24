package Models;

/**
 * Created by fernando on 24-04-2017.
 */
public class Hops {
    private String ip_adress;
    private String next_hop;

    public String getIp_adress() {
        return ip_adress;
    }

    public void setIp_adress(String ip_adress) {
        this.ip_adress = ip_adress;
    }

    public String getNext_hop() {
        return next_hop;
    }

    public void setNext_hop(String next_hop) {
        this.next_hop = next_hop;
    }

    public Hops(String ip_adress, String next_hop) {
        this.ip_adress = ip_adress;
        this.next_hop = next_hop;
    }
}
