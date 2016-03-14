package test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import controller.Action;
import databeans.Fund;
import databeans.FundHistoryView;
import databeans.FundPriceHistory;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.PositionDAO;
import model.viewDAO.CustomerFundDAO;

public class TestAction extends Action {

    private CustomerFundDAO customerFundDao;
    private FundDAO fundDao;
    private PositionDAO positionDao;
    private FundPriceHistoryDAO fundPriceHistoryDao;

    @Override
    public String getName() {
        return "test.do";
    }

    public TestAction(Model model) {
        customerFundDao = model.getCustomerFundDAO();
        fundDao = model.getFundDAO();
        positionDao = model.getPositionDAO();
        fundPriceHistoryDao = model.getFundPriceHistoryDAO();
    }

    @Override
    public String perform(HttpServletRequest request) {

        try {

            int fundId = 1;
            Fund fund = fundDao.getById(fundId);
            List<FundPriceHistory> history = fundPriceHistoryDao.getPriceHistory(fundId);
            List<FundHistoryView> fundHistoryViews = new ArrayList<>();
            DecimalFormat df = new DecimalFormat("######0.00");
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            for (FundPriceHistory fundPriceHistory : history) {
                FundHistoryView fundHistoryView = new FundHistoryView();
                fundHistoryView.setDate(simpleDateFormat.format(fundPriceHistory.getPriceDate()));
                System.out.println(simpleDateFormat.format(fundPriceHistory.getPriceDate()));
                fundHistoryView.setPrice(df.format(((double) fundPriceHistory.getPrice()) / 100));
                fundHistoryViews.add(fundHistoryView);
            }
            request.setAttribute("fundName", fund.getFundName());
            request.setAttribute("fundTicker", fund.getFundTicker());
            request.setAttribute("fundViews", fundHistoryViews);
            request.setAttribute("fundHistory", fundHistoryViews.get(0));
            return "test.jsp";

        } catch (RollbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
