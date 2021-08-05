package eu.senla.shabalin;

import com.codeborne.selenide.ElementsCollection;
import eu.senla.shabalin.pageobjects.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AllTest extends DataFixture{
    private String myAccountPageUrl = "index.php?controller=my-account";
    private TopMenuBar topMenuBar = new TopMenuBar();;

    @Test
    public void loginWithCorrectEmailAndCorrectPasswordTest() {
        MyAccountPage myAccountPage = (MyAccountPage) topMenuBar.clickInSingInButton().authenticationWithCredetials(correctEmail, correctPassword);
        assertEquals(baseUrl+myAccountPageUrl, myAccountPage.getPageUrl());
    }

    @Test
    public void loginWithCorrectEmailAndIncorrectPasswordTest() {
        AuthenticationPage authenticationPage = (AuthenticationPage) topMenuBar.clickInSingInButton().authenticationWithCredetials(correctEmail, incorrectPassword);
        assertTrue(authenticationPage.isAuthenticationFailedAlertPresent());
    }

    @Test
    public void isWomenCategorySubmenuDisplayedTest() {
        assertTrue(topMenuBar.isWomenCategorySubmenuDisplayed());
    }

    @Test
    public void isDressesCategorySubmenuDisplayedTest() {
        assertTrue(topMenuBar.isDressesCategorySubmenuDisplayed());
    }

    @Test
    public void searchProductPositiveTest() {
        ElementsCollection collection = topMenuBar.searchForProductsByRequest("T-shirt").getAllProductsInPage();
        assertTrue(collection.size()>0);
    }

    @Test
    public void searchProductNegativeTest() {
        ElementsCollection collection = topMenuBar.searchForProductsByRequest("pants").getAllProductsInPage();
        assertFalse(collection.size()>0);
    }

    @Test
    public void itemsWereAddedToTheCartTest() {
        int allProductsInPage = topMenuBar.chooseSummerDressesMenuCategoryAcrossDressesButton()
                .addAllProductsInCart()
                .getAllProductsInPage()
                .size();
        int allProductsInCart = topMenuBar.clickToCartButton().getCartItemsCount();
        assertEquals(allProductsInPage, allProductsInCart);
    }

    @Test
    public void productsTotalSumAndTotalPriceCheckTest() {
        topMenuBar.chooseSummerDressesMenuCategoryAcrossWomenButton().addAllProductsInCart();
        topMenuBar.clickToCartButton();
        CartPage cartPage = new CartPage();
        cartPage.setFirstCartItemQuantity(5);
        int sum = cartPage.getAllProductsSumPrice();
        int total = cartPage.getAllProductsFinalPrice();
        assertEquals(sum, total);
    }

    @Test
    public void deleteAllProductsFromCart() {
        topMenuBar.chooseSummerDressesMenuCategoryAcrossWomenButton().addAllProductsInCart();
        CartPage cartPage = topMenuBar.clickToCartButton().deleteAllProducts();
        cartPage.deleteAllProducts();
        assertEquals(0, cartPage.getCartItemsCount());
    }

    @Test
    public void createOrderAndCheckOrderCode() {
        topMenuBar.chooseSummerDressesMenuCategoryAcrossWomenButton().addAllProductsInCart();
        CreateOrderPage createOrderPage = (CreateOrderPage) topMenuBar.clickToCartButton()
                .clickProceedToCheckoutButton()
                .authenticationWithCredetials(correctEmail, correctPassword);
        String orderCode = createOrderPage.createOrder();
        assertTrue(topMenuBar.clickToMyAccountPageButton().isOrderPresent(orderCode));
    }
}
