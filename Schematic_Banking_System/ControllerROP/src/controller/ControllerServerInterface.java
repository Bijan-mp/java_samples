package controller;

import java.rmi.Remote;

public interface ControllerServerInterface extends Remote{

    public String login(String userName, String password) throws Exception;

}
