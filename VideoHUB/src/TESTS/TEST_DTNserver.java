package TESTS;

import DTNmecanisms.DTN_Server;

/**
 * Created by fernando on 07-06-2017.
 */
public class TEST_DTNserver {

    public static void main(final String[] args) throws InterruptedException {
        DTN_Server dtn_server = new DTN_Server();
        dtn_server.activateNode();
    }
}
