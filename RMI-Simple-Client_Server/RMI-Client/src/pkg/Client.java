package pkg;

import pkg.servers.Class1;
import pkg.servers.ServerIX;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        ServerIX myServer = (ServerIX) Naming.lookup("//localhost/myServer");
        System.out.println(myServer.sum(10, 20));
        Class1 class1= myServer.test();
        System.out.println(class1.class2.c2s);
    }
}
