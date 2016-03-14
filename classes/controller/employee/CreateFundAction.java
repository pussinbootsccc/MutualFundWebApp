package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import databeans.Fund;
import formbeans.CreateFundForm;
import model.FundDAO;
import model.Model;

public class CreateFundAction extends Action {

    private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);

    private FundDAO fundDAO;

    public CreateFundAction(Model model) {
        fundDAO = model.getFundDAO();
    }

    @Override
    public String getName() {
        return "e_create_fund.do";
    }

    /**
     * TODO I don't know whether the formbean need the isPresent.
     */
    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        // step1 check whether the user is login and the user has the right to
        // create fund
        if (!employeeLoginValidate(request)) {
            return "login.do";
        }

        try {
            // step2 fill the fund info
            CreateFundForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            if (!form.isPresent()) {
                return "/employee/e_create_fund.jsp";
            }
            errors.addAll(form.getValidationErrors());
            if (errors.size() > 0) {
                return "/employee/e_create_fund.jsp";
            }

            // check the name & ticker existed
            if (fundDAO.getByName(form.getFundName()) != null) {
                errors.add("The fund name already exists");
                return "/employee/e_create_fund.jsp";
            }
            if (fundDAO.getByTicker(form.getFundTicker()) != null) {
                errors.add("The fund ticker already exists");
                return "/employee/e_create_fund.jsp";
            }

            Fund fund = new Fund();
            fund.setFundName(form.getFundName());
            fund.setFundTicker(form.getFundTicker());

            // step3 create fund (create or createFund)
            fundDAO.createFund(fund);;

            // step4 return to success page
            request.setAttribute("message",
                    "New fund " + form.getFundName() + " (" + form.getFundTicker() + ") created successfully!");
            return "/employee/e_result.jsp";

        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "/employee/e_create_fund.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "/employee/e_create_fund.jsp";
        }

    }

}
