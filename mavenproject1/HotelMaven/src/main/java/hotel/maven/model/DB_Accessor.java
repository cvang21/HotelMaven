package hotel.maven.model;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cvadmin
 */
public interface DB_Accessor {

    void closeConnection() throws SQLException;

    long deleteRecords(String tableName, Map<String, Object> keyValueSet) throws SQLException;

    List findRecords(String sqlString, boolean closeConnection) throws SQLException, Exception;

    public List<Map<String, Object>> getAllRecords(String tableName) throws SQLException;
    
    Map getRecordById(String tableName, String keyName, Object keyValue) throws SQLException;

    long insertRecord(String tableName, Map<String, Object> insertValueSet) throws SQLException;

    void openConnection(String driverClassName, String url, String username, String password) throws IllegalArgumentException, ClassNotFoundException, SQLException;

    long updateRecords(String tableName, Map<String, Object> keyValueSet, Map<String, Object> updateValueSet) throws SQLException;
    
}
