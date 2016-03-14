package databeans;

public class CustomerFundView {
    private String fundName;
    private long fundId;
    private String fundTicker;
    private String fundPrice;
    private String shares;
    private String total;

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public long getFundId() {
        return fundId;
    }

    public void setFundId(long fundId) {
        this.fundId = fundId;
    }

    public String getFundTicker() {
        return fundTicker;
    }

    public void setFundTicker(String fundTicker) {
        this.fundTicker = fundTicker;
    }

    public String getFundPrice() {
        return fundPrice;
    }

    public void setFundPrice(String fundPrice) {
        this.fundPrice = fundPrice;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
