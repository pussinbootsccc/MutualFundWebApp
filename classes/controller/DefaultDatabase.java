package controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.genericdao.RollbackException;

import databeans.Customer;
import databeans.Employee;
import databeans.Fund;
import databeans.FundPriceHistory;
import databeans.MyTransaction;
import databeans.Position;
import model.CustomerDAO;
import model.EmployeeDAO;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

public class DefaultDatabase {
    private Model model;

    public DefaultDatabase(Model model) {
        this.model = model;
    }

    public void makeDefaultDatabase() {
        CustomerDAO customerDAO = model.getCustomerDAO();
        EmployeeDAO employeeDAO = model.getEmployeeDAO();
        TransactionDAO transactionDAO = model.getTransactionDAO();
        FundDAO fundDAO = model.getFundDAO();
        PositionDAO positionDAO = model.getPositionDAO();
        FundPriceHistoryDAO priceDAO = model.getFundPriceHistoryDAO();

        try {
            makeDefaultCustomer(customerDAO);
            makeDefaultEmployee(employeeDAO);
            makeDefaultTransaction(transactionDAO);
            makeDefaultFund(fundDAO);
            makeDefaultPosition(positionDAO);
            makeDefaultFundPriceHistory(priceDAO);
        } catch (RollbackException e) {

        }
    }

    private void makeDefaultFundPriceHistory(FundPriceHistoryDAO priceDAO) throws RollbackException {
        // FundPriceHistory[] old = priceDAO.match();
        // for (FundPriceHistory bean : old) {
        // priceDAO.delete(bean.getId());
        // }

        if (priceDAO.getCount() > 0) {
            return;
        }
        GregorianCalendar date1 = new GregorianCalendar();
        date1.add(Calendar.DATE, -4);
        GregorianCalendar date2 = new GregorianCalendar();
        date2.add(Calendar.DATE, -3);
        GregorianCalendar date3 = new GregorianCalendar();
        date3.add(Calendar.DATE, -2);
        GregorianCalendar date4 = new GregorianCalendar();
        date4.add(Calendar.DATE, -1);
        
        FundPriceHistory fph = new FundPriceHistory();
        // set fund 1 history
        fph.setFundId(1);
        fph.setPrice(4500);
        fph.setPriceDate(date1.getTime());
        priceDAO.create(fph);
        
        fph.setPrice(5000);
        fph.setPriceDate(date2.getTime());
        priceDAO.create(fph);

        fph.setPrice(6500);
        fph.setPriceDate(date3.getTime());
        priceDAO.create(fph);

        fph.setPrice(6000);
        fph.setPriceDate(date4.getTime());
        priceDAO.create(fph);

        // set fund 2 history
        fph.setFundId(2);
        fph.setPrice(2500);
        fph.setPriceDate(date1.getTime());
        priceDAO.create(fph);

        fph.setPrice(3000);
        fph.setPriceDate(date2.getTime());
        priceDAO.create(fph);

        fph.setPrice(3200);
        fph.setPriceDate(date3.getTime());
        priceDAO.create(fph);

        fph.setPrice(4500);
        fph.setPriceDate(date4.getTime());
        priceDAO.create(fph);

        // set fund 3 history
        fph.setFundId(3);
        fph.setPrice(1000);
        fph.setPriceDate(date1.getTime());
        priceDAO.create(fph);

        fph.setPrice(1300);
        fph.setPriceDate(date2.getTime());
        priceDAO.create(fph);

        fph.setPrice(1200);
        fph.setPriceDate(date3.getTime());
        priceDAO.create(fph);

        fph.setPrice(1500);
        fph.setPriceDate(date4.getTime());
        priceDAO.create(fph);
    }

    private void makeDefaultPosition(PositionDAO positionDAO) throws RollbackException {
        if (positionDAO.getCount() > 0) {
            return;
        }
        Position p = new Position();
        p.setCustomerId(1);
        p.setFundId(1);
        p.setShares(10000);
        positionDAO.create(p);
        
        p.setCustomerId(2);
        p.setFundId(3);
        p.setShares(10000);
        positionDAO.create(p);
    }

    private void makeDefaultFund(FundDAO fundDAO) throws RollbackException {
        if (fundDAO.getCount() > 0) {
            return;
        }

        Fund fund = new Fund();
        fund.setFundName("Google");
        fund.setFundTicker("GOOG");
        fund.setFundPrice(6000);
        fundDAO.createFund(fund);

        Fund fund1 = new Fund();
        fund1.setFundName("Yahoo");
        fund1.setFundTicker("YHOO");
        fund1.setFundPrice(4500);
        fundDAO.createFund(fund1);

        Fund fund2 = new Fund();
        fund2.setFundName("Amazon");
        fund2.setFundTicker("AMZN");
        fund2.setFundPrice(1500);
        fundDAO.createFund(fund2);

    }

    private void makeDefaultTransaction(TransactionDAO transactionDAO) throws RollbackException {
        // MyTransaction[] old = transactionDAO.match();
        // for (MyTransaction bean : old) {
        // transactionDAO.delete(bean.getTransactionId());
        // }

        if (transactionDAO.getCount() > 0) {
            return;
        }
        GregorianCalendar date4 = new GregorianCalendar();
        date4.add(Calendar.DATE, -1);
        
        MyTransaction t = new MyTransaction();
        
        // customer: user
        t.setCustomerId(1);
        t.setFundId(1);
        t.setShares(10000);
        t.setTransactionType(MyTransaction.BUY_FUND);
        t.setExecuteDate(date4.getTime());
        t.setPrice(6000);
        t.setAmount(60000);
        transactionDAO.create(t);
        // customer: user
        t.setCustomerId(1);
        t.setFundId(1);
        t.setShares(0);
        t.setTransactionType(MyTransaction.BUY_FUND);
        t.setExecuteDate(null);
        t.setPrice(0);
        t.setAmount(200000);
        transactionDAO.create(t);
        
        // customer: potus
        t.setCustomerId(2);
        t.setFundId(3);
        t.setShares(10000);
        t.setTransactionType(MyTransaction.BUY_FUND);
        t.setExecuteDate(date4.getTime());
        t.setPrice(1500);
        t.setAmount(15000);
        transactionDAO.create(t);
    }

    private void makeDefaultEmployee(EmployeeDAO employeeDAO) throws RollbackException {
        // Employee[] old = employeeDAO.match();
        // for (Employee bean : old) {
        // employeeDAO.delete(bean.getEmployeeId());
        // }

        if (employeeDAO.getCount() > 0) {
            return;
        }

        Employee employee1 = new Employee();
        employee1.setUserName("ace");
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setPassword("ace");
        employeeDAO.createEmployee(employee1);

        Employee employee2 = new Employee();
        employee2.setUserName("admin");
        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setPassword("admin");
        employeeDAO.createEmployee(employee2);
    }

    private void makeDefaultCustomer(CustomerDAO customerDAO) throws RollbackException {
        // Customer[] old = customerDAO.match();
        // for (Customer bean : old) {
        // customerDAO.delete(bean.getCustomerId());
        // }

        if (customerDAO.getCount() > 0) {
            return;
        }

        Customer customer1 = new Customer();
        customer1.setUserName("user");
        customer1.setPassword("123");
        customer1.setAddr_line1("5000 Forbes Ave");
        customer1.setAddr_line2("Walking to the Sky");
        customer1.setCity("Pittsburgh");
        customer1.setState("PA");
        customer1.setZip("15213");
        customer1.setFirstName("Andrew");
        customer1.setLastName("Carnegie");
        customer1.setCash(100000);
        customerDAO.add(customer1);

        Customer customer2 = new Customer();
        customer2.setUserName("potus");
        customer2.setPassword("secret");
        customer2.setAddr_line1("1600 Pennsylvania Ave");
        customer2.setAddr_line2("Apt 74");
        customer2.setCity("Washington");
        customer2.setState("DC");
        customer2.setZip("20500");
        customer2.setFirstName("Barack");
        customer2.setLastName("Obama");
        customer2.setCash(100000);
        customerDAO.add(customer2);
    }

}
