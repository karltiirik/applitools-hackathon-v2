package pageobjects;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DetailsPageTraditionalV2 {

    // COMMENT: I'd use use more stable locator strategies (like class names or data attributes),
    // but the Hackathon rules limited to only use Id-s
    public SelenideElement shoeName = $("#shoe_name");
    public SelenideElement shoeImage = $("#shoe_img");
    public SelenideElement itemCode = $("#SMALL____84");
    public SelenideElement rating = $("#SPAN__rating__76");

    public SelenideElement selectedSize = $("#DIV__colxlcollg__91");
    public SelenideElement selectedQuantity = $("#quantity_1");

    public SelenideElement newPrice = $("#new_price");
    public SelenideElement oldPrice = $("#old_price");
    public SelenideElement discount = $("#discount");

    public SelenideElement addToCartBtn = $("#DIV__btnaddtoca__113");

}
