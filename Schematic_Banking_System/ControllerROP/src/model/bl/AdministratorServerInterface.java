package model.bl;

import model.to.BankCheckRequestTO;

import java.rmi.Remote;
import java.util.ArrayList;

public interface AdministratorServerInterface extends Remote {

    public ArrayList<BankCheckRequestTO> viewUnCheckedBankCheckRequestList()throws Exception;
}
