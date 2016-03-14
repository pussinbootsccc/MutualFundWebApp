package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.customer.CustomerBuyFundAction;
import controller.customer.CustomerChangePwdAction;
import controller.customer.CustomerHomeAction;
import controller.customer.CustomerRequestCheckAction;
import controller.customer.CustomerSellFundAction;
import controller.customer.CustomerViewFundAction;
import controller.customer.CustomerViewTransactionHistoryAction;
import controller.employee.ChangeCustomerPwdAction;
import controller.employee.CreateCustomerAction;
import controller.employee.CreateEmployeeAction;
import controller.employee.CreateFundAction;
import controller.employee.DepositCheckAction;
import controller.employee.EmployeeChangePwdAction;
import controller.employee.EmployeeHomeAction;
import controller.employee.TransitionDayAction;
import controller.employee.ViewCustomerDetailAction;
import controller.employee.ViewCustomerListAction;
import model.Model;
import test.TestAction;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {

    @Override
    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction());
        Action.add(new CustomerHomeAction(model));
        Action.add(new CustomerBuyFundAction(model));
        Action.add(new CustomerChangePwdAction(model));
        Action.add(new CustomerRequestCheckAction(model));
        Action.add(new CustomerSellFundAction(model));
        Action.add(new CustomerViewFundAction(model));
        Action.add(new CustomerViewTransactionHistoryAction(model));
        Action.add(new CreateCustomerAction(model));
        Action.add(new ChangeCustomerPwdAction(model));
        Action.add(new CreateEmployeeAction(model));
        Action.add(new CreateFundAction(model));
        Action.add(new DepositCheckAction(model));
        Action.add(new EmployeeChangePwdAction(model));
        Action.add(new EmployeeHomeAction(model));
        Action.add(new TransitionDayAction(model));
        Action.add(new ViewCustomerDetailAction(model));
        Action.add(new ViewCustomerListAction(model));
        Action.add(new TestAction(model));

        DefaultDatabase defaultDatabase = new DefaultDatabase(model);
        defaultDatabase.makeDefaultDatabase();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage, request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String performTheAction(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String action = getActionName(servletPath);

        // if (action.equals("login.do")) {
        // // Allow these actions without logging in
        // return Action.perform(action, request);
        // }

        return Action.perform(action, request);
    }

    private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
            d.forward(request, response);
            return;
        }
    }

    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}