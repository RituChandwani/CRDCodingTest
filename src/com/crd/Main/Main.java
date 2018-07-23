package com.crd.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.crd.controller.DBOperations;
import com.crd.controller.PortfolioOperations;
import com.crd.utils.ResourceUtils;

public class Main {

	public static void main(String[] args) {
		
		try {
		ResourceUtils.initResources();
				
		DBOperations dbOperations=new DBOperations();
		dbOperations.createorGetDBConnection();
		PortfolioOperations portfolioOp =new PortfolioOperations();
		
		Double totalPortfolioAmt= dbOperations.getTotalPortfolioAmount();
		
		List<HashMap> stockDetails=dbOperations.getPortfolioDetails();
			
			for(HashMap map: stockDetails){
				
				Double holdingPercent=(Double) map.get("HOLDINGPERCENT");
				Double modelPercent=(Double) map.get("MODELPERCENT");
				Double holdingAmt = (Double) map.get("AMT");
				String stockName = (String) map.get("SEC");
				Double differencePercent = holdingPercent - modelPercent;
				/**
				 * if difference is positive Fred needs to sell the stocks,
				 * if difference is negative Fred needs to buy the stocks
				 * if its zero, do Nothing
				 */
				Boolean success=Boolean.FALSE;
				
				if(differencePercent >0){						
					 success=portfolioOp.sellStock(differencePercent,holdingAmt,stockName,totalPortfolioAmt);
					
				}
				else if(differencePercent < 0){
						if(holdingAmt == 0)
							success=portfolioOp.buyNewStock(differencePercent,totalPortfolioAmt,stockName);
						else
							success=portfolioOp.buyExistingStock(differencePercent,holdingAmt,stockName,totalPortfolioAmt);
				}
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Database Operation Failed");
			e.printStackTrace();
		} catch(IOException iEx){
			System.out.println("Resource initialization Failed");
			iEx.printStackTrace();
		}
		
		

		}

}
