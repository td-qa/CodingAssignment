package codingassignment.tests.ui;

import static org.junit.jupiter.api.Assertions.*;

import codingassignment.uiElements.components.ProductCard;

import codingassignment.uiElements.pages.HomePageUs;
import codingassignment.uiElements.pages.ProductPage;
import codingassignment.uiElements.pages.ProductsCatalogPage;
import codingassignment.uiElements.pages.ShoppingBagPage;

import codingassignment.tests.base.BaseUiTest;
import codingassignment.tests.util.Rand;
import org.junit.jupiter.api.Test;

public class PurchaseFlowTests extends BaseUiTest {

    @Test
    void shouldAddTwoItemsViaSearchAndShowInvalidCouponMessage() {
        HomePageUs homePage = new HomePageUs(driver).open(baseUrl);
        homePage.ensureCookieConsentHandled();
        homePage.header().searchBar.search("sofa");

        ProductsCatalogPage catalogPage = new ProductsCatalogPage(driver).waitUntilReady();
        ProductCard firstProductCard = catalogPage.productCards().getFirst();
        firstProductCard.openProductPage();

        ProductPage productPage = new ProductPage(driver);
        productPage.addToCart();
        productPage.recommendations().continueShopping();
        productPage.header().searchBar.search("table");

        catalogPage = new ProductsCatalogPage(driver).waitUntilReady();
        ProductCard thirdProductCard = catalogPage.productCards().get(2);
        thirdProductCard.openProductPage();

        productPage = new ProductPage(driver);
        productPage.addToCart();
        productPage.recommendations().goToShoppingBag();

        ShoppingBagPage bagPage = new ShoppingBagPage(driver).waitUntilReady(false);
        int actual = bagPage.waitUntilItemCountIs(2);
        assertEquals(
                2, actual, () -> "Expected 2 items in cart, but found " + actual
        );

        bagPage.applyDiscount(Rand.randomString(15));
        bagPage.waitUntilVisible(bagPage.discountError());
        assertEquals(bagPage.expectedDiscountError(), bagPage.discountErrorText());
    }
}
