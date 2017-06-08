package Network;

import java.io.IOException;

/**
 * Created by fernando on 07-06-2017.
 */
public class TimeoutThread implements Runnable {

        private volatile boolean isTimeout;
        private String ipAddress;
        private int timeout;

        public TimeoutThread(String ipAddress, int timeout){
            this.ipAddress = ipAddress;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            NetOperations netOperations = new NetOperations();
            try {
                isTimeout = netOperations.icmpRequest(ipAddress, timeout);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean getResult() {
            return isTimeout;
        }
}
