package controller.customer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import controller.Action;
import databeans.AllFundView;
import databeans.Fund;
import databeans.FundHistoryView;
import databeans.FundPriceHistory;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import util.Common;

public class CustomerViewFundAction extends Action {
    // private FormBeanFactory<ViewFundForm> formBeanFactory = FormBeanFactory.getInstance(ViewFundForm.class);
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private FundDAO fundDAO;

    public CustomerViewFundAction(Model model) {
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
        fundDAO = model.getFundDAO();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "view-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        // TODO Auto-generated method stub
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        if (!customerLoginValidate(request)) {
            return "login.do";
        }

        try {
            if (request.getParameter("fund-id") == null) {
                Fund[] funds = fundDAO.getAllFunds();
                AllFundView[] allFundViews = new AllFundView[funds.length];
                for (int i = 0; i < funds.length; i++) {
                    AllFundView allFundView = new AllFundView();
                    allFundView.setFundName(funds[i].getFundName());
                    allFundView.setFundId(funds[i].getFundId());
                    allFundView.setFundTicker(funds[i].getFundTicker());
                    if (funds[i].getFundPrice() == 0) {
                        allFundView.setFundPrice("-");
                    } else {
                        allFundView.setFundPrice(Common.convertLongPriceToString(funds[i].getFundPrice()));
                    }
                    allFundViews[i] = allFundView;
                }
                request.setAttribute("fundList", allFundViews);
                return "customer/view-fund.jsp";
            }

            String fundString = request.getParameter("fund-id");
            if (fundString == null) {
                errors.add("No such fund");
                return "error.jsp";
            }

            long fundId = Long.parseLong(fundString);
            Fund fund = fundDAO.getById(fundId);
            if (fund == null) {
                errors.add("No such fund");
                return "error.jsp";
            }

            List<FundPriceHistory> history = fundPriceHistoryDAO.getPriceHistory(fundId);
            List<FundHistoryView> fundHistoryViews = new ArrayList<>();
            DecimalFormat df = new DecimalFormat("######0.00");
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            for (FundPriceHistory fundPriceHistory : history) {
                FundHistoryView fundHistoryView = new FundHistoryView();
                fundHistoryView.setDate(simpleDateFormat.format(fundPriceHistory.getPriceDate()));
                fundHistoryView.setPrice(df.format(((double) fundPriceHistory.getPrice()) / 100));
                fundHistoryViews.add(fundHistoryView);
            }
            request.setAttribute("fundName", fund.getFundName());
            request.setAttribute("fundTicker", fund.getFundTicker());
            request.setAttribute("history", fundHistoryViews);
            return "customer/view-this-fund.jsp";
        } catch (NumberFormatException e) {
            // if fund-id is not long
            errors.add("No such fund");
            return "error.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }

}
