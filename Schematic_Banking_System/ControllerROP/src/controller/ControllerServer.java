package controller;

import model.bl.CustomerServerInterface;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class ControllerServer extends UnicastRemoteObject implements ControllerServerInterface {
    public ControllerServer() throws Exception {
        java.rmi.registry.LocateRegistry.createRegistry(1100);
        Naming.rebind("ControllerServer", this);
        System.out.println("----------------------------\n" +
                "ROP Controller Server is running on 1099 port \n" +
                "---------------------------");
    }

    public String login(String userName, String password) throws Exception {

        CustomerServerInterface customerServer = (CustomerServerInterface) Naming.lookup("CustomerServer");
        return customerServer.login(userName, password);
    }
}
