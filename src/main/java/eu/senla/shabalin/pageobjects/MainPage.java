package eu.senla.shabalin.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage implements PageObject{
    private ElementsCollection allProducts = $$(By.className("ajax_block_product"));
    private By addToCartButtonSelector = byText("Add to cart");
    private SelenideElement continueShoppingButton = $(By.className("continue"));
    public ElementsCollection getAllProductsInPage() {
        return allProducts;
    }

    public MainPage addAllProductsInCart() {
        ElementsCollection collection = getAllProductsInPage();
        collection.forEach(a -> {
            a.hover().$(addToCartButtonSelector).click();
            continueShoppingButton.click();
        });
        return this;
    }
}
