package example5;

public class Server implements Loggable {

    private String lastIP;

    private int numRequests;
    private int numSuccessful;

    public Server() {
        lastIP = "";
        numRequests = 0;
        numSuccessful = 0;
    }

    public void serve(String ip) {
        lastIP = ip;
        numRequests++;
        if(Math.random() > 0.5) {
            numSuccessful++;
        }
    }

    @Override
    public void log() {
        if(numRequests == 0) {
            System.out.println("No requests yet!");
        } else {
            int failures = numRequests - numSuccessful;
            System.out.println("Successful Requests: " + numSuccessful + " Failed Requests: " + failures + " Total: " + numRequests);
            System.out.println("Last IP served: " + lastIP);
        }
    }
}
