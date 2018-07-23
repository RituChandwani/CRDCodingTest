package com.crd.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crd.utils.DBUtils;
import com.crd.utils.IQueryConstants;

public class DBOperations {
	
	private static Connection conn = null;
	
	public Connection createorGetDBConnection() throws ClassNotFoundException, SQLException{
		
		if(conn == null){
		conn=DBUtils.getDatabaseConnection();
		}
		return conn;
	}
	
		
	public List getPortfolioDetails() throws SQLException{
		
		List<HashMap> stockDetails=new ArrayList<HashMap>();
		String query=IQueryConstants.GET_PORTFOLIO_DETAILS;
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs=null;
		boolean isResult= pstmt.execute();
		if(isResult){
			rs=pstmt.getResultSet();
			while(rs.next()){
				Map stockMap=new HashMap();
				String SEC=rs.getString(1);
				Double AMT=rs.getDouble(2);
				Double HOLDINGPERCENT=rs.getDouble(3);
				Double MODELPERCENT=rs.getDouble(4);
				
				stockMap.put("SEC", SEC);
				stockMap.put("AMT", AMT);
				stockMap.put("MODELPERCENT", MODELPERCENT);
				stockMap.put("HOLDINGPERCENT", HOLDINGPERCENT);
				stockDetails.add((HashMap) stockMap); 
				
			}
				
			}
		return stockDetails;
		
	}

	public Boolean addRecordInOrders(String stockName, String SELL_STOCK,
			Double transactionAmt) throws SQLException {
		String query=IQueryConstants.RECORD_ORD_INSERT;
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, stockName);
		pstmt.setString(2, SELL_STOCK);
		pstmt.setDouble(3, transactionAmt);
		pstmt.executeUpdate();
		return Boolean.TRUE;
	}


	public Boolean removeRecordFromHolding(String stockName) throws SQLException {
		String query=IQueryConstants.REMOVE_HOLDING_RECORD;
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, stockName);
		pstmt.executeUpdate();
		return Boolean.TRUE;
	}


	public Boolean updateRecordInHolding(String stockName, Double updatedAmt) throws SQLException {
		String query=IQueryConstants.UPDATE_HOLDING_RECORD;
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setDouble(1, updatedAmt);
		pstmt.setString(2, stockName);
		pstmt.executeUpdate();
		return Boolean.TRUE;
		
	}


	public Double getTotalPortfolioAmount() throws SQLException {
		String query=IQueryConstants.TOTAL_PORTFOLIO_AMT;
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs=null;
		boolean isResult= pstmt.execute();
		if(isResult){
			rs=pstmt.getResultSet();
			while(rs.next()){
				Double totalAmt=rs.getDouble(1);
				return totalAmt;
			}
		}
		return null;
	}


	public Boolean addRecordInHolding(String stockName, Double newHoldingAmt) throws SQLException {
		String query=IQueryConstants.RECORD_HOLDING_INSERT;
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, stockName);
		pstmt.setDouble(2, newHoldingAmt);
		pstmt.executeUpdate();
		return Boolean.TRUE;
		
	}

}
