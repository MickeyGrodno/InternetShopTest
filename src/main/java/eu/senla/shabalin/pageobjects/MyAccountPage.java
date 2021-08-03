package eu.senla.shabalin.pageobjects;

import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MyAccountPage implements PageObject {
    private SelenideElement orderHistoryButton = $(byTitle("Orders"));
    private List<String> allOrdersCode = $$("a.color-myaccount").texts();

    public boolean isOrderPresent(String orderCode) {
        orderHistoryButton.click();
        return allOrdersCode.contains(orderCode);
    }
}
