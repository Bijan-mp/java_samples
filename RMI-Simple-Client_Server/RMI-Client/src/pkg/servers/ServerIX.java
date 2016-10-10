package pkg.servers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerIX extends Remote {

    public int sum(int i,int j)throws RemoteException;
    public String sumString(String s1, String s2)throws RemoteException;
    public Class1 test()throws RemoteException;

}
