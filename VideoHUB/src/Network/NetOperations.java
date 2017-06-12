package Network;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by fernando on 07-06-2017.
 */
public class NetOperations {

    public NetOperations(){}

    public boolean icmpRequest(String ipAddress, int timeout)throws IOException {
        final InetAddress host = InetAddress.getByName(ipAddress);
        //System.out.println("icmpRequest " + ipAddress + " = " + host.isReachable(timeout));
        return host.isReachable(timeout);
    }

    public boolean timeoutTask(String ipAddress, int timeout) throws InterruptedException {
        TimeoutThread timeoutThread = new TimeoutThread(ipAddress, timeout);
        Thread thread = new Thread(timeoutThread);
        thread.start();
        thread.join();
        System.out.println("Result: " + timeoutThread.getResult());
        return timeoutThread.getResult();
    }

}
