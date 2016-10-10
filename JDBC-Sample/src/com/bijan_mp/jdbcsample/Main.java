package com.bijan_mp.jdbcsample;

import com.bijan_mp.jdbcsample.dao.Table1DAO;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {

            Table1DAO table1DAO = new Table1DAO();
            ResultSet resultSet = table1DAO.select();
            while (resultSet.next())
            {
                System.out.println(resultSet.getString("NAME"));
                System.out.println(resultSet.getString("FAMILY"));
            }

            //Use resultset metadata
            //ResultSetMetaData rmd = resultSet.getMetaData();
            //System.out.println(rmd.getColumnCount());
            //System.out.println(rmd.getColumnTypeName(2));
            //System.out.println(rmd.getColumnLabel(2));

            table1DAO.close();

        } catch (SQLException e) {
            e.getStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
