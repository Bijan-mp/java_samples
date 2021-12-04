package model.dao;

import model.to.CustomerInformationTO;
import model.to.CustomerInformationTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class CustomerInformationDAO extends DAO{

    public CustomerInformationDAO() throws Exception{
        setConnection();
    }

    public CustomerInformationDAO(Connection connection,PreparedStatement statement) {
        this.connection=connection;
        this.statement=statement;
    }

    public void insert(CustomerInformationTO customerInformationTO)throws Exception{

    }

    public ArrayList selectAll(CustomerInformationTO customerInformationTO)throws Exception{

        return new ArrayList();
    }

    public CustomerInformationTO update(CustomerInformationTO customerInformationTO , CustomerInformationTO oldCustomerInformationTO)throws Exception{

        return new CustomerInformationTO();
    }
}
