package pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MainPageTraditionalV1 {

    // COMMENT: I'd use use more stable locator strategies (like class names or data attributes),
    // but the Hackathon rules limited to only use Id-s
    public SelenideElement logo = $("#logo");
    public SelenideElement mainMenu = $("#DIV__mainmenu__15");

    public SelenideElement searchField = $("#DIV__customsear__41");
    public SelenideElement submitSearchBtn = $("#BUTTONsubmit____43");
    public SelenideElement openSearchButton = $("#A__btnsearchm__59");

    public SelenideElement profileBtn = $("#A__accesslink__56");
    public SelenideElement wishlistBtn = $("#A__wishlist__52");
    public SelenideElement cartBtn = $("#A__cartbt__49");
    public SelenideElement itemsInCartCount = $("#STRONG____50");

    public SelenideElement filtersSidebar = $("#sidebar_filters");

    public SelenideElement filtersBtn = $("#ti-filter");
    public SelenideElement viewGridIcon = $("#I__tiviewgrid__202");
    public SelenideElement viewListIcon = $("#I__tiviewlist__204");

    public SelenideElement productGrid = $("#product_grid");

    public SelenideElement addToFavoriteBtn = $("#I__tiheart__225");
    public SelenideElement addToCompareBtn = $("#I__ticontrols__229");
    public SelenideElement addToCartBtn = $("#I__tishopping__233");

    public SelenideElement quickLinksFooterMenuItem = $("#H3____421");
    public SelenideElement contactsFooterMenuItem = $("#H3____437");
    public SelenideElement keepInTouchMenuItem = $("#H3____447");

    public MainPageTraditionalV1 openFiltersIfNotVisible() {
        if (filtersSidebar.is(Condition.not(Condition.visible))) {
            filtersBtn.click();
        }
        return this;
    }

    public MainPageTraditionalV1 selectColor(Color color) {
        $(color.getLocator()).click();
        return this;
    }

    public MainPageTraditionalV1 filter() {
        $("#filterBtn").click();
        return this;
    }

    public DetailsPageTraditionalV1 openProductDetails(int productIndex) {
        getGridItems().get(productIndex).click();
        return new DetailsPageTraditionalV1();
    }

    public ElementsCollection getGridItems() {
        return productGrid.$$(".grid_item");
    }

    public SelenideElement getGridItemElement(int index) {
        return $("#" + getGridItems().get(index).getAttribute("id"));
    }

    public enum Color {
        BLACK("#colors__Black");

        private final String locator;

        Color(String locator) {
            this.locator = locator;
        }

        public String getLocator() {
            return this.locator;
        }
    }
}
