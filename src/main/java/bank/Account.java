package bank;

import bank.exceptions.AmountException;
import bank.exceptions.InsufficientFundsException;

public class Account {

  private int id;
  private String type;
  private double balance;

  public Account(int id, String type, double balance) {
    setId(id);
    setType(type);
    setBalance(balance);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void deposit(double amount) throws AmountException {
    if (amount < 1) {
      throw new AmountException("The minimum deposit is 1");
    } else {
      double newBalance = balance + amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, balance);
    }
  }

  public void withdrawl(double amount) throws AmountException, InsufficientFundsException {
    if (amount < 1) {
      throw new AmountException("The minimum withdrawl is 1");
    } else if (amount > balance) {
      throw new InsufficientFundsException("You dont have that kind of money my guy");
    } else {
      double newBalance = balance - amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, balance);
    }
  }

}
