package TESTS;

import Network.NetOperations;

/**
 * Created by fernando on 07-06-2017.
 */
public class TEST_DTN {

    public static void main(final String[] args) throws InterruptedException {
        NetOperations netOperations = new NetOperations();
        netOperations.timeoutTask("127.0.0.1", 5000);
    }
}
