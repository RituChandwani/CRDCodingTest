package com.crd.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crd.utils.DBConstants;
import com.crd.utils.DBUtils;
import com.crd.utils.IQueryConstants;

public class DBOperations {
	
	private static Connection conn = null;
	
	public Connection createorGetDBConnection() throws ClassNotFoundException, SQLException{
		
		if(conn == null || conn.isClosed()){
		conn=DBUtils.getDatabaseConnection();
		}
		return conn;
	}
	
	/*
	 * This method fetches the details of all the stocks by putting full join Holding and Model Table	
	 */
	public List getPortfolioDetails() throws SQLException, ClassNotFoundException{
		
		Connection conn=createorGetDBConnection();
		List<HashMap> stockDetails=new ArrayList<HashMap>();
		String query=IQueryConstants.GET_PORTFOLIO_DETAILS;
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs=null;
		boolean isResult= pstmt.execute();
		if(isResult){
			rs=pstmt.getResultSet();
			while(rs.next()){
				Map stockMap=new HashMap();
								
				stockMap.put(DBConstants.SEC, rs.getString(DBConstants.SEC));
				stockMap.put(DBConstants.AMT, rs.getDouble(DBConstants.AMT));
				stockMap.put(DBConstants.HOLDINGPERCENT, rs.getDouble(DBConstants.HOLDINGPERCENT));
				stockMap.put(DBConstants.MODELPERCENT, rs.getDouble(DBConstants.MODELPERCENT));
				stockDetails.add((HashMap) stockMap); 
				
			}
				
			}
		return stockDetails;
		
	}

	/*
	 * This method inserts all the transactions(buy/sell) in ord table for tracking
	 */
	public void addRecordInOrders(String stockName, String SELL_STOCK,
			Double transactionAmt) throws SQLException, ClassNotFoundException {
		
		Connection conn=createorGetDBConnection();
		String query=IQueryConstants.RECORD_ORD_INSERT;
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, stockName);
		pstmt.setString(2, SELL_STOCK);
		pstmt.setDouble(3, transactionAmt);
		pstmt.executeUpdate();
		
	}

	/*
	 * This method remove the entry of the stock from the Holding table if all the amount has been sold as it didnt find record in Model Table 
	 */
	public void removeRecordFromHolding(String stockName) throws SQLException, ClassNotFoundException {
		Connection conn=createorGetDBConnection();
		String query=IQueryConstants.REMOVE_HOLDING_RECORD;
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, stockName);
		pstmt.executeUpdate();
		
	}


	/*
	 * This method update the amount of stock in Holding table after the buy/sell of the stock
	 */
	public void updateRecordInHolding(String stockName, Double updatedAmt) throws SQLException, ClassNotFoundException {
		Connection conn=createorGetDBConnection();
		String query=IQueryConstants.UPDATE_HOLDING_RECORD;
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setDouble(1, updatedAmt);
		pstmt.setString(2, stockName);
		pstmt.executeUpdate();
				
	}

    /*
     * This method queries the Holding table to get the sum of total amount present in the table
     */
	public Double getTotalPortfolioAmount() throws SQLException, ClassNotFoundException {
		Connection conn=createorGetDBConnection();
		String query=IQueryConstants.TOTAL_PORTFOLIO_AMT;
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs=null;
		boolean isResult= pstmt.execute();
		if(isResult){
			rs=pstmt.getResultSet();
			while(rs.next()){
				Double totalAmt=rs.getDouble(DBConstants.TOTALAMT);
				return totalAmt;
			}
		}
		return null;
	}

	/*
	 * this method insert a new stock Record in Holding table which id found from Model Table
	 */
	public void addRecordInHolding(String stockName, Double newHoldingAmt) throws SQLException, ClassNotFoundException {
		Connection conn=createorGetDBConnection();
		String query=IQueryConstants.RECORD_HOLDING_INSERT;
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, stockName);
		pstmt.setDouble(2, newHoldingAmt);
		pstmt.executeUpdate();
				
	}

}
