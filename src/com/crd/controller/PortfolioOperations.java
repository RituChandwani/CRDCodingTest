package com.crd.controller;

import java.sql.Connection;
import java.sql.SQLException;

import com.crd.utils.DBConstants;

public class PortfolioOperations {

	/**
	 * For any transaction(Buy/Sell) we need to follow 4 steps:
	 * 1. Calculate the transactionAmount
	 * 2. Calculate the UpdatedAmount
	 * 3. Insert record into ORD table with transaction Amount
	 * 4. Update HOLDING table with updated amount / Remove Record from Holding / Insert new record into Holding
	 * @param removeStock 
	 * @param stockName 
	 * @param holdingAmt 
	 * @param differencePercent 
	 * 
	 * @return
	 */
	
	DBOperations dbOperations=new DBOperations();
	
	/*
	 * This method sells the stock by calculating transaction amount using differencePercent and totalPortfolioAmt
	 * It puts the record of selling worth transactionAmt in ORD table
	 * It calculates the updatedAmt,
	 * if the updatedAmt is zero, record is removed from the Holding table
	 * else record is updated with new amount still left in the Holding Table
	 */
	
	public Boolean sellStock(Double differencePercent, Double holdingAmt, String stockName, Double totalPortfolioAmt) throws SQLException, ClassNotFoundException {
		
		Connection conn=dbOperations.createorGetDBConnection();
		conn.setAutoCommit(Boolean.FALSE);
		try{
		Double transactionAmt=(differencePercent/100)*totalPortfolioAmt;
		Double updatedAmt=holdingAmt-transactionAmt;
		dbOperations.addRecordInOrders(stockName,DBConstants.SELL_STOCK,transactionAmt);
		//if updatedAmt is 0 then remove the entry of the stock from Holding Table
		if(updatedAmt == 0){
			dbOperations.removeRecordFromHolding(stockName);
		}else{
			dbOperations.updateRecordInHolding(stockName,updatedAmt);
		}
		conn.commit();
		}catch (SQLException e ) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                conn.rollback();
	            } catch(SQLException excep) {
	                excep.printStackTrace();
	            }
	        }
	    } finally {
	        conn.setAutoCommit(true);
	        
	    }
		return Boolean.TRUE;
		
		
	}

	/**
	 * This method buys a new stock whose entry we received from Model Table and
	 * insert a new record in the Holding table with the amount matching the model percent.
	 * @param differencePercent
	 * @param totalPortfolioAmt
	 * @param stockName
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Boolean buyNewStock(Double differencePercent,
		Double totalPortfolioAmt, String stockName) throws SQLException, ClassNotFoundException {
		
		Connection conn=dbOperations.createorGetDBConnection();
		conn.setAutoCommit(Boolean.FALSE);
		try{
			 Double newHoldingAmt=(Math.abs(differencePercent)/100)*totalPortfolioAmt;
			 dbOperations.addRecordInOrders(stockName,DBConstants.BUY_STOCK,newHoldingAmt);
			 dbOperations.addRecordInHolding(stockName,newHoldingAmt);
			 conn.commit();
		}catch (SQLException e ) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                conn.rollback();
	            } catch(SQLException excep) {
	                excep.printStackTrace();
	            }
	        }
	    } finally {
	        conn.setAutoCommit(true);
	        
	    }
		return Boolean.TRUE;
	}

	/*
	 * This method buys the existing stock to match the mdoel percent.
	 * It puts the buy record in the ORD table
	 * And update the HOlding table with the new AMT adding up the bought stocks.
	 */
	public Boolean buyExistingStock(Double differencePercent,
			Double holdingAmt, String stockName, Double totalPortfolioAmt) throws SQLException, ClassNotFoundException {
		
		Connection conn=dbOperations.createorGetDBConnection();
		conn.setAutoCommit(Boolean.FALSE);
		try{
			Double transactionAmt=(Math.abs(differencePercent)/100)*totalPortfolioAmt;
			Double updatedAmt=holdingAmt+transactionAmt;
			dbOperations.addRecordInOrders(stockName,DBConstants.BUY_STOCK,transactionAmt);
			dbOperations.updateRecordInHolding(stockName,updatedAmt);
		}catch (SQLException e ) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                conn.rollback();
	            } catch(SQLException excep) {
	                excep.printStackTrace();
	            }
	        }
	    } finally {
	        conn.setAutoCommit(true);
	        
	    }
		return Boolean.TRUE;
	}
	
	/**
	 * This methos closes the database connection and frees up the resource
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void releaseResources() throws ClassNotFoundException, SQLException{
		dbOperations.createorGetDBConnection().close();
	}



}
