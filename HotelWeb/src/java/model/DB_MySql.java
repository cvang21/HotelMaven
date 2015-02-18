package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
public class DB_MySql implements DB_Accessor{
    
    private Connection conn;
    
    public DB_MySql(){}

    @Override
    public void openConnection(String driverClassName, String url, String username, String password) 
	throws IllegalArgumentException, ClassNotFoundException, SQLException
	{
		String msg = "Error: url is null or zero length!";
		if( url == null || url.length() == 0 ) throw new IllegalArgumentException(msg);
		username = (username == null) ? "" : username;
		password = (password == null) ? "" : password;
		Class.forName (driverClassName);
		conn = DriverManager.getConnection(url, username, password);
	}


    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    @Override
    public List findRecords(String sqlString, boolean closeConnection) throws SQLException, Exception {
	Statement stmt = null;
	ResultSet rs = null;
	ResultSetMetaData metaData = null;
	final List list = new ArrayList();
	Map record = null;

            // do this in an exception handler so that we can depend on the
            // finally clause to close the connection
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			metaData = rs.getMetaData();
			final int fields = metaData.getColumnCount();

			while( rs.next() ) {
				record = new HashMap();
				for( int i=1; i <= fields; i++ ) {
					try {
						record.put( metaData.getColumnName(i), rs.getObject(i) );
					} catch(NullPointerException npe) { 
						// no need to do anything... if it fails, just ignore it and continue
					}
				} // end for
				list.add(record);
			} // end while

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				stmt.close();
				if(closeConnection) conn.close();
			} catch(SQLException e) {
				throw e;
			} // end try
		} // end finally

		return list; // will  be null if none found 
    
    }

    @Override
    public List<Map<String, Object>> getAllRecords(String tableName) throws SQLException {
	List<Map<String, Object>> records = new ArrayList<>();
	String sql = "select * from " + tableName;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSetMetaData metaData = null;

	try {
	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(sql);
	    metaData = rs.getMetaData();
	    final int fields = metaData.getColumnCount();

	    while (rs.next()) {
		Map<String, Object> record = new HashMap();
		for (int i = 1; i <= fields; i++) {
		    try {
			record.put(metaData.getColumnName(i), rs.getObject(i));
		    } catch (NullPointerException npe) {
			// no need to do anything... if it fails, just ignore it and continue
		    }
		} // end for
		records.add(record);
	    } // end while
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    if (stmt != null) {
		stmt.close();
	    }
	}

	return records;
    }

    @Override
    public Map getRecordById(String tableName, String keyName, Object keyValue) throws SQLException {
	String sql = "select * from " + tableName + " where " + keyName + " = ?";
	int index = 1;
	final Map<String, Object> record = new HashMap<>();
	ResultSetMetaData metaData = null;

	PreparedStatement pstmt = null;
	try {
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setObject(index++, keyValue);
	    ResultSet rs = pstmt.executeQuery();

	    metaData = rs.getMetaData();
	    metaData.getColumnCount();
	    final int fields = metaData.getColumnCount();

	    // Retrieve the raw data from the ResultSet and copy the values into a Map
	    // with the keys being the column names of the table.
	    if (rs.next()) {
		for (int i = 1; i <= fields; i++) {
		    record.put(metaData.getColumnName(i), rs.getObject(i));
		}
	    }
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	}

	return record;
    }

    @Override
    public long updateRecords(String tableName, Map<String, Object> keyValueSet, Map<String, Object> updateValueSet) throws SQLException {
	long ret = 0;

	if (updateValueSet.isEmpty()) {
	    throw new IllegalArgumentException("Need something to update!");
	}

	//Map<String,Integer>columns = this.getColumnTypes(tableName);
	StringBuilder sqlBuilder = new StringBuilder("update " + tableName + " set ");
	boolean firstOne = true;

	for (String colName : updateValueSet.keySet()) {
	    if (!firstOne) {
		sqlBuilder.append(", ");
	    } else {
		firstOne = false;
	    }
	    sqlBuilder.append(colName);
	    sqlBuilder.append(" = ?");
	}

	if (!keyValueSet.isEmpty()) {
	    sqlBuilder.append(" where ");
	    firstOne = true;
	    for (String colName : keyValueSet.keySet()) {
		if (!firstOne) {
		    sqlBuilder.append(" and ");
		} else {
		    firstOne = false;
		}
		sqlBuilder.append(colName);
		sqlBuilder.append(" = ?");
	    }
	}

	String sql = sqlBuilder.toString();
	ResultSetMetaData metaData = null;
	int index = 1;

	PreparedStatement pstmt = null;
	try {
	    pstmt = conn.prepareStatement(sql);
	    for (Object newValue : updateValueSet.values()) {
		pstmt.setObject(index++, newValue);
	    }
	    for (Object whereValue : keyValueSet.values()) {
		pstmt.setObject(index++, whereValue);
	    }
	    ret = pstmt.executeUpdate();
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	}

	return ret;
    }

    @Override
    public long insertRecord(String tableName, Map<String, Object> insertValueSet) throws SQLException {
	long ret = 0;

	if (insertValueSet.isEmpty()) {
	    throw new IllegalArgumentException("Need something to insert!");
	}

	//Map<String,Integer>columns = this.getColumnTypes(tableName);
	StringBuilder sqlBuilder = new StringBuilder("insert into " + tableName + " ( ");
	StringBuilder valuesBuilder = new StringBuilder("values ( ");

	boolean firstOne = true;

	for (String colName : insertValueSet.keySet()) {
	    if (!firstOne) {
		sqlBuilder.append(", ");
		valuesBuilder.append(", ");
	    } else {
		firstOne = false;
	    }
	    sqlBuilder.append(colName);
	    valuesBuilder.append("?");
	}
	sqlBuilder.append(" ) ");
	sqlBuilder.append(valuesBuilder.toString());
	sqlBuilder.append(" ) ");

	String sql = sqlBuilder.toString();
	int index = 1;

	PreparedStatement pstmt = null;
	try {
	    pstmt = conn.prepareStatement(sql);
	    for (Object newValue : insertValueSet.values()) {
		pstmt.setObject(index++, newValue);
	    }
	    ret = pstmt.executeUpdate();
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	}

	return ret;
    }

    @Override
    public long deleteRecords(String tableName, Map<String, Object> keyValueSet) throws SQLException {
	long ret = 0;

	if (keyValueSet.isEmpty()) {
	    throw new IllegalArgumentException("Can Not delete all rows");
	}

	//Map<String,Integer>columns = this.getColumnTypes(tableName);
	StringBuilder sqlBuilder = new StringBuilder("delete from " + tableName + " where ");
	boolean firstOne = true;
	for (String colName : keyValueSet.keySet()) {
	    if (!firstOne) {
		sqlBuilder.append(" and ");
	    } else {
		firstOne = false;
	    }
	    sqlBuilder.append(colName);
	    sqlBuilder.append(" = ?");
	}

	String sql = sqlBuilder.toString();
	int index = 1;

	PreparedStatement pstmt = null;
	try {
	    pstmt = conn.prepareStatement(sql);
	    for (Object newValue : keyValueSet.values()) {
		pstmt.setObject(index++, newValue);
	    }
	    ret = pstmt.executeUpdate();
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	}

	return ret;
    }
    
}
