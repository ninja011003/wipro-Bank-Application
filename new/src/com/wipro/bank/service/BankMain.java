package com.wipro.bank.service;

import com.wipro.bank.util.InsufficientFundsException;

import java.util.Date;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.dao.*;
public class BankMain {
	
	
	
	public  String checkBalance(String accountNumber) {
		BankDAO bankobj = new BankDAO();
		if(bankobj.validate(accountNumber)){
			float balance = bankobj.findBalance(accountNumber);
			return "BALANCE IS:"+balance;
		}
		else
			return "ACCOUNT NUMBER INVALID";
	}
	
	
	
	public  String transfer(TransferBean transferBean) {
		BankDAO bankobj = new BankDAO();
		if(transferBean==null) {
			return "INVALID";
		}
		else if(bankobj.validate(transferBean.getFromAccountNumber())&&bankobj.validate(transferBean.getToAccountNumber())) {
			try {
				if(bankobj.findBalance(transferBean.getFromAccountNumber())>=transferBean.getAmount()) {
					
					float fromBalance =bankobj.findBalance(transferBean.getFromAccountNumber());
					float toBalance =bankobj.findBalance(transferBean.getToAccountNumber());
					float Debitamount = transferBean.getAmount();
					bankobj.updateBalance(transferBean.getFromAccountNumber(),fromBalance-Debitamount );
					bankobj.updateBalance(transferBean.getToAccountNumber(),toBalance+Debitamount );
					if(bankobj.transferMoney(transferBean))
						return "SUCCESS";
					return "SUCCESS";
				}
				else {
					throw new InsufficientFundsException();
				}
			}
			catch(InsufficientFundsException e) {
				return e.toString;//"INSUFFICIENT FUNDS";
			}
		}
		else {
			return "INVALID ACCOUNT";
		}
	}
    public static void main(String[] args){
    	/*
    	BankMain obj = new BankMain();
    	TransferBean tbobj = new TransferBean();
    	tbobj.setAmount(1000);
    	tbobj.setDateOfTransaction(new Date());
    	tbobj.setFromAccountNumber("67890");
    	tbobj.setToAccountNumber("12345");
    	System.out.println(obj.transfer(tbobj));
    	*/
    }
}
