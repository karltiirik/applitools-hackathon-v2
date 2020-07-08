import com.codeborne.selenide.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import pageobjects.DetailsPageTraditionalV2;
import pageobjects.MainPageTraditionalV2;
import util.HackathonReporter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static pageobjects.MainPageTraditionalV2.Color;

@RunWith(JUnitParamsRunner.class)
public class TraditionalTestsV2 {

    static int appVersion = 2;
    static String url = String.format("https://demo.applitools.com/gridHackathonV%s.html", appVersion);

    private MainPageTraditionalV2 mainPage = new MainPageTraditionalV2();
    private SoftAssertions softAssertions;
    private HackathonReporter reporter;

    private Object[] browsersAndViewports() {
        return new Object[]{
                new Object[]{Browsers.CHROME, "1200", "700", "Laptop"},
                new Object[]{Browsers.FIREFOX, "1200", "700", "Laptop"},
                new Object[]{Browsers.EDGE, "1200", "700", "Laptop"},
                new Object[]{Browsers.CHROME, "768", "700", "Tablet"},
                new Object[]{Browsers.FIREFOX, "768", "700", "Tablet"},
                new Object[]{Browsers.EDGE, "768", "700", "Tablet"},
                new Object[]{Browsers.CHROME, "500", "700", "Mobile"}
        };
    }

    public void setUp(String browser, String viewPortWidth, String viewPortHeight) {
        Configuration.browser = browser;
        Configuration.browserSize = String.format("%sx%s", viewPortWidth, viewPortHeight);
        Configuration.savePageSource = false;
        Configuration.screenshots = false;
        softAssertions = new SoftAssertions();
    }

    @Test
    @Parameters(method = "browsersAndViewports")
    public void testTask1(String browser, String viewPortWidth, String viewPortHeight, String device) {
        setUp(browser, viewPortWidth, viewPortHeight);
        open(url);
        reporter = new HackathonReporter(appVersion, 1, browser, String.format("%s x %s", viewPortWidth, viewPortHeight), device);

        // Should be present on all device viewports
        softAssert("Logo is displayed", mainPage.logo, visible);
        softAssert("Profile button is displayed", mainPage.profileBtn, visible);
        softAssert("Cart button is displayed", mainPage.cartBtn, visible);

        // Specific to Laptops viewport
        if (device == "Laptop") {
            softAssert("Main menu is displayed", mainPage.mainMenu, visible);
            softAssert("Filters sidebar is displayed", mainPage.filtersSidebar, visible);

            softAssert("Filters button is not displayed", mainPage.filtersBtn, hidden);
            softAssert("View grid icon is displayed", mainPage.viewGridIcon, visible);
            softAssert("View list icon is displayed", mainPage.viewListIcon, visible);

            softAssert("Product add to favorite button is not displayed", mainPage.addToFavoriteBtn, hidden);
            softAssert("Product add to compare button is not displayed", mainPage.addToCompareBtn, hidden);
            softAssert("Product add to cart button is not displayed", mainPage.addToCartBtn, hidden);

            softAssert("Wishlist button is displayed", mainPage.wishlistBtn, visible);
        } else {
            softAssert("Main menu is not displayed", mainPage.mainMenu, hidden);
            softAssert("Filters sidebar is not displayed", mainPage.filtersSidebar, hidden);

            softAssert("Filters button is displayed", mainPage.filtersBtn, visible);
            softAssert("View grid icon is not displayed", mainPage.viewGridIcon, hidden);
            softAssert("View list icon is not displayed", mainPage.viewListIcon, hidden);

            softAssert("Product add to favorite button is displayed", mainPage.addToFavoriteBtn, visible);
            softAssert("Product add to compare button is displayed", mainPage.addToCompareBtn, visible);
            softAssert("Product add to cart button is displayed", mainPage.addToCartBtn, visible);

            softAssert("Wishlist button is not displayed", mainPage.wishlistBtn, hidden);
        }

        // Specific to Mobile viewport
        if (device == "Mobile") {
            softAssert("Search field is not displayed", mainPage.searchField, hidden);
            softAssert("Submit search button is not displayed", mainPage.submitSearchBtn, hidden);
            softAssert("Open search button is displayed", mainPage.openSearchButton, visible);

            softAssert("Items in cart count is not displayed", mainPage.itemsInCartCount, hidden);

            // Footer
            Condition collapsed = not(attribute("class", "opened"));
            softAssert("Quick links footer menu item is collapsed", mainPage.quickLinksFooterMenuItem, collapsed);
            softAssert("Contacts footer menu item is collapsed", mainPage.contactsFooterMenuItem, collapsed);
            softAssert("Keep in touch footer menu item is collapsed", mainPage.keepInTouchMenuItem, collapsed);
        } else {
            softAssert("Search field is displayed", mainPage.searchField, visible);
            softAssert("Submit search button is displayed", mainPage.submitSearchBtn, visible);

            softAssert("Items in cart count is displayed", mainPage.itemsInCartCount, visible);
        }
    }

    @Test
    @Parameters(method = "browsersAndViewports")
    public void testTask2(String browser, String viewPortWidth, String viewPortHeight, String device) {
        setUp(browser, viewPortWidth, viewPortHeight);
        open(url);
        reporter = new HackathonReporter(appVersion, 2, browser, String.format("%s x %s", viewPortWidth, viewPortHeight), device);

        mainPage.openFiltersIfNotVisible()
                .selectColor(Color.BLACK)
                .filter();
        // COMMENT: Can verify that there are 2 results displayed but not that they're black without visual diffing
        // (no corresponding metadata in the DOM).
        // Would use mainPage.getGridItems().shouldHaveSize(2) but for the Hackathon required id reporter will loop
        // through all of the displayed results
        for (int i = 0; i < mainPage.getGridItems().size(); i++) {
            if (i < 2) {
                softAssert(i + 1 + ". result should be shown", mainPage.getGridItemElement(i), visible);
            } else {
                softAssert(i + 1 + ". result should not be shown", mainPage.getGridItemElement(i), hidden);
            }
        }

        // Specific to Laptops viewport
        if (device == "Laptop") {
            softAssert("Product add to favorite button is not displayed", mainPage.addToFavoriteBtn, hidden);
            softAssert("Product add to compare button is not displayed", mainPage.addToCompareBtn, hidden);
            softAssert("Product add to cart button is not displayed", mainPage.addToCartBtn, hidden);
        } else {
            softAssert("Product add to favorite button is displayed", mainPage.addToFavoriteBtn, visible);
            softAssert("Product add to compare button is displayed", mainPage.addToCompareBtn, visible);
            softAssert("Product add to cart button is displayed", mainPage.addToCartBtn, visible);
        }
    }

    @Test
    @Parameters(method = "browsersAndViewports")
    public void testTask3(String browser, String viewPortWidth, String viewPortHeight, String device) {
        setUp(browser, viewPortWidth, viewPortHeight);
        open(url);
        reporter = new HackathonReporter(appVersion, 3, browser, String.format("%s x %s", viewPortWidth, viewPortHeight), device);

        DetailsPageTraditionalV2 detailsPage = mainPage.openFiltersIfNotVisible()
                .selectColor(Color.BLACK)
                .filter()
                .openProductDetails(0);

        // COMMENT: Traditional tests don't catch if elements that are overlaid (aka visible, but on top of each other)
        // App header
        softAssert("Profile button is displayed", mainPage.profileBtn, visible);
        softAssert("Wishlist button is displayed", mainPage.wishlistBtn, visible);
        softAssert("Cart button is displayed", mainPage.cartBtn, visible);
        softAssert("Items in cart count is displayed", mainPage.itemsInCartCount, visible);

        softAssert("Shoe name is Appli Air x Night", detailsPage.shoeName, text("Appli Air x Night"));
        softAssert("Shoe image is displayed", detailsPage.shoeImage, visible);
        softAssert("Shoe rating is displayed", detailsPage.rating, visible);
        softAssert("Item code is displayed", detailsPage.itemCode, visible);
        softAssert("Selected size is Small (S)", detailsPage.selectedSize, text("Small (S)"));
        softAssert("Selected quantity is 1", detailsPage.selectedQuantity, value("1"));
        softAssert("New price is $33.00", detailsPage.newPrice, text("$33.00"));
        softAssert("Old price is $48.00", detailsPage.oldPrice, text("$48.00"));
        softAssert("Discount is -30% discount", detailsPage.discount, text("-30% discount"));

        // Hack-y way to check text design
        String expectedCssValue = browser == Browsers.FIREFOX ?
                "line-through rgb(153, 153, 153)" : "line-through solid rgb(153, 153, 153)";
        softAssert("Old price should be gray and strikethrough", detailsPage.oldPrice,
                cssValue("text-decoration", expectedCssValue));
        softAssert("Add to cart button is displayed", detailsPage.addToCartBtn, visible);
    }

    private void softAssert(String testName, SelenideElement element, Condition condition) {
        softAssertions.assertThat(reporter.log(testName, element, element.is(condition)))
                .as(testName)
                .isTrue();
    }

    @After
    public void tearDown() {
        softAssertions.assertAll();
        Selenide.closeWindow();
    }
}
