package TESTS;

import DTNmecanisms.DTN_Client;

import java.io.IOException;

/**
 * Created by fernando on 10-06-2017.
 */
public class TEST_DTNclient {
    public static void main(final String[] args) throws InterruptedException, IOException {
        DTN_Client dtn_client = new DTN_Client("::1", 3333);
        dtn_client.resumeStreaming("12345", String.valueOf(34));
        dtn_client.resumeStreaming("12345", String.valueOf(35));

    }
}
