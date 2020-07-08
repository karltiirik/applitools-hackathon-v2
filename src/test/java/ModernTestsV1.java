import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.MainPageModern;

import static pageobjects.MainPageModern.Color;

public class ModernTestsV1 {

    static int appVersion = 1;
    static String url = String.format("https://demo.applitools.com/gridHackathonV%s.html", appVersion);

    static Eyes eyes;
    static WebDriver webDriver;
    static VisualGridRunner runner;
    private MainPageModern mainPage = new MainPageModern();

    @BeforeClass
    public static void setUp() {
        // Get WebDriver instance from Selenide
        webDriver = WebDriverRunner.getAndCheckWebDriver();

        // Create a runner with concurrency of 10
        runner = new VisualGridRunner(10);

        // Create Eyes object with the runner, meaning it'll be a Visual Grid eyes.
        eyes = new Eyes(runner);

        // Configure Eyes
        configureEyes();
    }

    public static void configureEyes() {
        // Initialize eyes Configuration
        Configuration config = new Configuration();

        // You can get your api key from the Applitools dashboard
        config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        // Create a new batch info instance and set it to the configuration
        config.setBatch(new BatchInfo("UFG Hackathon"));

        // Add browsers with different viewports
        config.addBrowser(1200, 700, BrowserType.CHROME);
        config.addBrowser(1200, 700, BrowserType.FIREFOX);
        config.addBrowser(1200, 700, BrowserType.EDGE);
        config.addBrowser(768, 700, BrowserType.CHROME);
        config.addBrowser(768, 700, BrowserType.FIREFOX);
        config.addBrowser(768, 700, BrowserType.EDGE);
        config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);

        // Set the configuration object to eyes
        eyes.setConfiguration(config);
    }

    @Before
    public void openUrl() {
        webDriver.get(url);
    }

    public void openEyes(String testName) {
        eyes.open(webDriver,
                "AppliFashion",
                testName,
                new RectangleSize(800, 600));
    }

    @Test
    public void testTask1() {
        openEyes("Task 1");
        eyes.checkWindow("Cross-Device Elements Test");
    }

    @Test
    public void testTask2() {
        openEyes("Task 2");
        mainPage.openFilters()
                .selectColor(Color.BLACK)
                .filter();
        eyes.checkRegion(By.id("product_grid"), "Filter Results");
    }

    @Test
    public void testTask3() {
        openEyes("Task 3");
        mainPage.openFilters()
                .selectColor(Color.BLACK)
                .filter()
                .openProductDetails(0);
        eyes.checkWindow("Product Details test");
    }

    @After
    public void afterEachTest() {
        eyes.closeAsync();
    }

    @AfterClass
    public static void tearDown() {
        // we pass false to this method to suppress the exception that is thrown if we
        // find visual differences
        TestResultsSummary allTestResults = runner.getAllTestResults(false);
        System.out.println(allTestResults);
    }
}