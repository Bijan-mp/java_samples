package pkg.servers;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerClass extends UnicastRemoteObject implements ServerIX {

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        ServerClass serverClass = new ServerClass();

    }

    public ServerClass() throws MalformedURLException, RemoteException {

        //RMI port must be 1099 and more(>1099)
        java.rmi.registry.LocateRegistry.createRegistry(1099);

        //define a name to access server
        Naming.rebind("myServer",this);

        System.out.println("myServer ready on 1099 ...");
    }

    public int sum(int i, int j) throws RemoteException {
        System.out.println("sum invoked : "+i+" + "+j+" = "+(i+j));
        return i+j;
    }

    public String sumString(String s1, String s2) throws RemoteException {
        System.out.println("sumString invoked : "+s1+" + "+s2+" = "+s1+s2);
        return s1+s2;
    }

    public Class1 test() throws RemoteException {
        return new Class1();
    }
}
