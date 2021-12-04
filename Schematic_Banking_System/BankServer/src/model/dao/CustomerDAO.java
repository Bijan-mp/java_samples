package model.dao;

import model.to.CustomerInformationTO;
import model.to.CustomerTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerDAO extends DAO {

    public CustomerDAO() throws Exception {
        setConnection();
    }

    public CustomerDAO(Connection connection, PreparedStatement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    //===================
    public void insert(CustomerTO customerTO) throws Exception {

        this.statement = this.connection.prepareStatement("INSERT INTO CUSTOMER " +
                "(NAME," +
                "FAMILY," +
                "FATHER_NAME," +
                "NATIONAL_ID," +
                "BIRTH_CERTIFICATE_ID," +
                "BIRTH_DATE," +
                "BIRTH_PLACE," +
                "DOCUMENT_PICTURE_ADDRESS) " +
                "VALUES (?,?,?,?,?,?,?,?)");
        this.statement.setString(1, customerTO.getName());
        this.statement.setString(2, customerTO.getFamily());
        this.statement.setLong(3, customerTO.getNationalId());
        this.statement.setLong(4, customerTO.getBirthCertificateId());
        this.statement.setLong(5, customerTO.getBirthDate());
        this.statement.setString(6, customerTO.getBirthPlace());
        this.statement.setString(7, customerTO.getDocumentPictureAddress());
        this.statement.executeUpdate();

        long customerId = 0;// It must set with select

        this.statement = this.connection.prepareStatement("INSERT INTO CUSTOMER_INFORMATION " +
                "(CITY," +
                "ADDRESS," +
                "PHONE," +
                "CELLPHONE," +
                "EMAIL," +
                "CUSTOMER_ID) " +
                "VALUES (?,?,?,?,?,?)");
        this.statement.setString(1, customerTO.getCustomerInformationTO().getCity());
        this.statement.setString(2, customerTO.getCustomerInformationTO().getAddress());
        this.statement.setLong(3, customerTO.getCustomerInformationTO().getPhone());
        this.statement.setLong(4, customerTO.getCustomerInformationTO().getCellPhone());
        this.statement.setString(5, customerTO.getCustomerInformationTO().getEmail());
        this.statement.setLong(6, customerId);
        this.statement.executeUpdate();

    }

    public CustomerTO selectById(long id) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT * " +
                " FROM CUSTOMER "+
                " WHERE  ID = ?"
                );
        this.statement.setLong(1, id);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();

        CustomerTO customerTO = new CustomerTO();
        customerTO.setId(resultSet.getLong("ID"));
        customerTO.setName(resultSet.getString("NAME"));
        customerTO.setFamily(resultSet.getString("FAMILY"));
        customerTO.setFatherName(resultSet.getString("FATHER_NAME"));
        customerTO.setNationalId(resultSet.getLong("NATIONAL_ID"));
        customerTO.setBirthCertificateId(resultSet.getLong("BIRTH_CERTIFICATE_ID"));
        customerTO.setBirthDate(resultSet.getLong("BIRTH_DATE"));
        customerTO.setBirthPlace(resultSet.getString("BIRTH_PLACE"));
        customerTO.setDocumentPictureAddress(resultSet.getString("DOCUMENT_PICTURE_ADDRESS"));
        return customerTO;

        /*this.statement = this.connection.prepareStatement("SELECT " +
                "CUSTOMER.ID C_ID ," +
                "CUSTOMER.NAME C_NAME," +
                "CUSTOMER.FAMILY C_FAMILY," +
                "CUSTOMER.FATHER_NAME C_FATHER_NAME," +
                "CUSTOMER.NATIONAL_ID C_NATIONAL_ID," +
                "CUSTOMER.BIRTH_CERTIFICATE_ID C_BIRTH_CERTIFICATE_ID," +
                "CUSTOMER.BIRTH_DATE C_BIRTH_DATE," +
                "CUSTOMER.BIRTH_PLACE C_BIRTH_PLACE," +
                "CUSTOMER.DOCUMENT_PICTURE_ADDRESS C_DOCUMENT_PICTURE_ADDRESS," +
                "CUSTOMER_INFORMATION.ID INF_ID ," +
                "CUSTOMER_INFORMATION.ADDRESS INF_ADDRESS," +
                "CUSTOMER_INFORMATION.CITY INF_CITY," +
                "CUSTOMER_INFORMATION.PHONE INF_PHONE," +
                "CUSTOMER_INFORMATION.CELLPHONE INF_CELLPHONE," +
                "CUSTOMER_INFORMATION.EMAIL INF_EMAIL," +
                "CUSTOMER_INFORMATION.CUSTOMER_ID INF_CUSTOMER_ID ," +
                "CUSTOMER_INFORMATION.POSTAL_CODE INF_POSTAL_CODE " +
                " FROM CUSTOMER c,CUSTOMER_INFORMATION inf " +
                " WHERE  c.ID = ?" +
                " AND c.ID = inf.ID ");

        this.statement.setLong(1, id);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();
        System.out.println(resultSet);
        CustomerInformationTO customerInformationTO = new CustomerInformationTO();
        customerInformationTO.setId(resultSet.getLong("INF_ID"));
        customerInformationTO.setCity(resultSet.getString("INF_CITY"));
        customerInformationTO.setAddress(resultSet.getString("INF_ADDRESS"));
        customerInformationTO.setPostalCode(resultSet.getLong("INF_POSTAL_CODE"));
        customerInformationTO.setPhone(resultSet.getLong("INF_PHONE"));
        customerInformationTO.setCellPhone(resultSet.getLong("INF_CELLPHONE"));
        customerInformationTO.setEmail(resultSet.getString("INF_EMAIL"));

        CustomerTO customerTO = new CustomerTO();
        customerTO.setId(resultSet.getLong("C_ID"));
        customerTO.setName(resultSet.getString("C_NAME"));
        customerTO.setFamily(resultSet.getString("C_FAMILY"));
        customerTO.setFatherName(resultSet.getString("C_FATHER_NAME"));
        customerTO.setNationalId(resultSet.getLong("C_NATIONAL_ID"));
        customerTO.setBirthCertificateId(resultSet.getLong("C_BIRTH_CERTIFICATE_ID"));
        customerTO.setBirthDate(resultSet.getLong("C_BIRTH_DATE"));
        customerTO.setBirthPlace(resultSet.getString("C_BIRTH_PLACE"));
        customerTO.setDocumentPictureAddress(resultSet.getString("C_DOCUMENT_PICTURE_ADDRESS"));
        customerTO.setCustomerInformationTO(customerInformationTO);

        return customerTO;*/

    }


    public CustomerTO selectByCustomerInformation(String name, String family, long nationalId) throws Exception {

        this.statement = this.connection.prepareStatement("SELECT " +
                "CUSTOMER.ID CID ," +
                "CUSTOMER.NAME ," +
                "CUSTOMER.FAMILY ," +
                "CUSTOMER.FATHER_NAME ," +
                "CUSTOMER.NATIONAL_ID ," +
                "CUSTOMER.BIRTH_CERTIFICATE_ID ," +
                "CUSTOMER.BIRTH_DATE ," +
                "CUSTOMER.BIRTH_PLACE," +
                "CUSTOMER.DOCUMENT_PICTURE_ADDRESS" +
                "CUSTOMER_INFORMATION.ID INFOID ," +
                "CUSTOMER_INFORMATION.ADDRESS," +
                "CUSTOMER_INFORMATION.CITY ," +
                "CUSTOMER_INFORMATION.PHONE," +
                "CUSTOMER_INFORMATION.CELLPHONE," +
                "CUSTOMER_INFORMATION.EMAIL," +
                "CUSTOMER_INFORMATION.CUSTOMER_ID INFOCID ," +
                "CUSTOMER_INFORMATION.POSTAL_CODE " +
                "FROM CUSTOMER,CUSTOMER_INFORMATION " +
                "WHERE " +
                "CUSTOMER.NAME = ? AND " +
                "CUSTOMER.FAMILY = ? AND " +
                "CUSTOMER.NATIONAL_ID = ? " +
                "AND CID = INFOCID ");
        this.statement.setString(1, name);
        this.statement.setString(2, family);
        this.statement.setLong(3, nationalId);
        ResultSet resultSet = this.statement.executeQuery();
        resultSet.next();

        CustomerInformationTO customerInformationTO = new CustomerInformationTO();
        customerInformationTO.setId(resultSet.getLong("INFOID"));
        customerInformationTO.setCity(resultSet.getString("CITY"));
        customerInformationTO.setAddress(resultSet.getString("ADDRESS"));
        customerInformationTO.setPostalCode(resultSet.getLong("POSTAL_CODE"));
        customerInformationTO.setPhone(resultSet.getLong("PHONE"));
        customerInformationTO.setCellPhone(resultSet.getLong("CELLPHONE"));
        customerInformationTO.setEmail(resultSet.getString("EMAIL"));

        CustomerTO customerTO = new CustomerTO();
        customerTO.setId(resultSet.getLong("CID"));
        customerTO.setName(resultSet.getString("NAME"));
        customerTO.setFamily(resultSet.getString("FAMILY"));
        customerTO.setFatherName(resultSet.getString("FATHER_NAME"));
        customerTO.setNationalId(resultSet.getLong("NATIONAL_ID"));
        customerTO.setBirthCertificateId(resultSet.getLong("BIRTH_CERTIFICATE_ID"));
        customerTO.setBirthDate(resultSet.getLong("BIRTH_DATE"));
        customerTO.setBirthPlace(resultSet.getString("BIRTH_PLACE"));
        customerTO.setDocumentPictureAddress(resultSet.getString("DOCUMENT_PICTURE_ADDRESS"));
        customerTO.setCustomerInformationTO(customerInformationTO);

        return customerTO;
    }

}
