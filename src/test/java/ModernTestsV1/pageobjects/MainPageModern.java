package ModernTestsV1.pageobjects;

import static com.codeborne.selenide.Selenide.$;

public class MainPageModern {

    public MainPageModern openFilters() {
        $("#ti-filter").click();
        return this;
    }

    public MainPageModern selectColor(Color color) {
        $(color.getLocator()).click();
        return this;
    }

    public MainPageModern filter() {
        $("#filterBtn").click();
        return this;
    }

    public MainPageModern openProductDetails(int productIndex) {
        $("#product_grid").$$(".grid_item").get(productIndex).click();
        return this;
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
