package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;
import bank.exceptions.InsufficientFundsException;

public class Menu {

  private Scanner scanner;

  private void showMenu(Customer customer, Account account) {
    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("========================================");
      System.out.println("What you want?");
      System.out.println("1: deposit");
      System.out.println("2: withdrawl");
      System.out.println("3: check balance");
      System.out.println("4: exit");
      System.out.println("========================================");

      selection = scanner.nextInt();
      double amount = 0;

      switch (selection) {
        case 1:
          System.out.println("How much?");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            System.out.println("Uh oh" + e.getMessage());
            System.out.println("Try again.");
          }

          break;
        case 2:
          System.out.println("How much?");
          amount = scanner.nextDouble();
          try {
            account.withdrawl(amount);
          } catch (InsufficientFundsException e) {
            System.out.println("Uh oh" + e.getMessage());
            System.out.println("Try again.");
          } catch (AmountException e) {
            System.out.println("Uh oh" + e.getMessage());
            System.out.println("Try again.");
          }

          break;
        case 3:
          System.out.println("Balance: " + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("Thanks for banking");
          break;
        default:
          System.out.println("Invalid input");
          break;
      }
    }
  }

  private Customer authenticateUser() {
    System.out.println("Please enter username");
    String username = scanner.next();
    System.out.println("Please enter password");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error" + e.getMessage());
    }
    return customer;
  }

  public static void main(String[] args) {
    System.out.println("Welcome to the Bank!");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();
    if (customer != null) {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }
}
