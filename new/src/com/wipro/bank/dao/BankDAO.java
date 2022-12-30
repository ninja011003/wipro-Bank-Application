package com.wipro.bank.dao;
import com.wipro.bank.bean.TransferBean;
import java.util.Date;
//import java.sql.Date;
import com.wipro.bank.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class BankDAO {
	private Connection connect = DBUtil.getDBConnection();
    public int generateSequenceNumber(){
        int seq=1000;
        try {
			Statement stmt = connect.createStatement();
			ResultSet result= stmt.executeQuery("SELECT transactionId_seq.NEXTVAL FROM dual");
			result.next();
			if(result!=null) {
				seq= result.getInt(1);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//System.out.println("generateseqnum");
		}
        return seq;
    }
    public boolean validate(String accountNumber){
    	boolean val=false;
    	try {
			Statement stmt = connect.createStatement();
			ResultSet result= stmt.executeQuery("select count(*) from ACCOUNT_TBL where Account_Number='"+accountNumber+"'");
			result.next();
			if(result.getInt(1)==1) {
				val=true;
			}
		} catch (Exception e) {
			//System.out.println("validate");
			//e.printStackTrace();
		}
    	return val;
    }
    
    public float findBalance(String accountNumber){
    	BankDAO obj = new BankDAO();
    	int Balance=-1;
    	if(obj.validate(accountNumber)) {
    		Statement stmt;
			try {
				stmt = connect.createStatement();
				ResultSet result = stmt.executeQuery("select Balance from ACCOUNT_TBL where Account_Number='"+accountNumber+"'");
				result.next();
				Balance=result.getInt(1);
			} catch (Exception e) {
				System.out.println("findbalance");
				//e.printStackTrace();
			}
        	
    	}
    	return Balance;
    }
    
	public boolean transferMoney(TransferBean transferBean){
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
    	BankDAO obj = new BankDAO();
    	transferBean.setTransactionID(obj.generateSequenceNumber());
    	String date="1-oct-2003";
    	Statement stmt;
		try {
			
			try {
				date = formatter1.format(formatter.parse(transferBean.getDateOfTransaction().toString()));
			} catch (Exception e) {
				//System.out.println("transfermoney-dateconversion");
				//e.printStackTrace();
			}
			int num=-1;
			stmt = connect.createStatement();
			num= stmt.executeUpdate("insert into TRANSFER_TBL values("+transferBean.getTransactionID()+",'"+transferBean.getFromAccountNumber()+"',"+transferBean.getToAccountNumber()+",'"+date+"',"+transferBean.getAmount()+")");
			if(num==1)
				return true;
		} catch (Exception e) {
			//System.out.println("transfermoney-insertion in tranfsfertable");
			//e.printStackTrace();
		}
    	return false;
    }
    
    public boolean updateBalance(String accountNumber,float newBalance){
    	BankDAO obj = new BankDAO();
        boolean val=false;
        if(obj.validate(accountNumber)) {
    		Statement stmt;
			try {
				stmt = connect.createStatement();
				int num=stmt.executeUpdate("update ACCOUNT_TBL set Balance="+newBalance+" where Account_Number='"+accountNumber+"'");
				if(num==1)
					val=true;
			} catch (Exception e) {
				//System.out.println("updateBalance");
				//e.printStackTrace();
			}
        	
    	}
        
        return val;
    }
    
}
