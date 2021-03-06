package com.vreco.rocket.zeromq.client;

import org.zeromq.ZMsg;

/**
 * Majordomo Protocol client example, asynchronous. Uses the mdcli API to hide
 * all MDP aspects
 * 
 * @author Arkadiusz Orzechowski <aorzecho@gmail.com>
 * 
 */
public class Client {

    public static void main(String[] args) {
        boolean verbose = (args.length > 0 && "-v".equals(args[0]));
        Mdcliapi2 clientSession = new Mdcliapi2("tcp://localhost:5555", verbose);

        int count;
        for (count = 0; count < 100000; count++) {
            ZMsg request = new ZMsg();
            String mockMessage = "{\"boomQueue\":\"wooQ\", \"private\":{\"woo\":\"woo\"}, \"something\":\"something\"}";
            request.addString(mockMessage);
            clientSession.send("echo", request);
        }
        for (count = 0; count < 100000; count++) {
            ZMsg reply = clientSession.recv();
            if (reply != null)
                reply.destroy();
            else
                break; // Interrupt or failure
        }

        System.out.printf("%d requests/replies processed\n", count);
        clientSession.destroy();
    }

}
