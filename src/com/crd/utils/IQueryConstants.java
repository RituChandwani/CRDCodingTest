package com.crd.utils;

public interface IQueryConstants {
	
	static String TOTAL_PORTFOLIO_AMT = "select sum(AMT) as TOTALAMT from HOLDING";
	
	static String RECORD_HOLDING_INSERT="insert into public.HOLDING(SEC,AMT) values (?,?)";
	
	static String RECORD_ORD_INSERT ="insert into public.ORD(SEC,TRANS,AMT)values(?,?,?)";
	
	static String REMOVE_HOLDING_RECORD="delete from HOLDING where SEC = ? ";
	
	static String UPDATE_HOLDING_RECORD="Update HOLDING set AMT = ? where SEC = ? ";
	
	static String GET_PORTFOLIO_DETAILS ="SELECT h.sec,COALESCE(h.amt,0) as AMT, COALESCE(h.AMT/(select sum(AMT) from holding)*100,0) as \"holdingpercent\","+
						"COALESCE(mo.percen,0) as MODELPERCENT FROM holding h  LEFT JOIN model mo ON mo.sec = h.sec"+
						" UNION "+
						"SELECT mo.sec,COALESCE(h.amt,0)as AMT,COALESCE(h.AMT/(select sum(AMT) from holding)*100,0) as \"holdingpercent\","+
						"COALESCE(mo.percen,0) as MODELPERCENT FROM holding h RIGHT JOIN model mo ON mo.sec = h.sec";
}
