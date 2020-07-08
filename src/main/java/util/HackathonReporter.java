package util;

import com.codeborne.selenide.SelenideElement;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class HackathonReporter {

    int version;
    int taskNr;
    String browser;
    String viewport;
    String device;

    public HackathonReporter(int version, int taskNr, String browser, String viewport, String device) {
        this.version = version;
        this.taskNr = taskNr;
        this.browser = browser.substring(0, 1).toUpperCase() + browser.substring(1);
        this.viewport = viewport;
        this.device = device;
    }

    /**
     * A Helper to print the test result in the following format:
     * Task: <Task Number>, Test Name: <Test Name>, DOM Id:: <id>, Browser: <Browser>, Viewport: <Width x Height>, Device<Device type>, Status: <Pass | Fail>
     * <p>
     * Example: Task: 1, Test Name: Search field is displayed, DOM Id: DIV__customsear__41, Browser: Chrome, Viewport: 1200 x 700, Device: Laptop, Status: Pass
     *
     * @param testName         string - Something meaningful. E.g. 1.1 Search field is displayed
     * @param element          string - DOM ID of the element
     * @param comparisonResult boolean - The result of comparing the "Expected" value and the "Actual" value.
     * @return boolean - returns the same comparison result back so that it can be used for further Assertions in the test code.
     */
    public boolean log(String testName, SelenideElement element, boolean comparisonResult) {
        // get the element id
        String elemId = element.getSearchCriteria().substring(1);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                "Traditional-V" + this.version + "-TestResults.txt", true))) {
            writer.write("Task: " + this.taskNr + ", Test Name: " + testName + ", DOM Id: " + elemId + ", Browser: "
                    + this.browser + ", Viewport: " + this.viewport + ", Device: " + this.device
                    + ", Status: " + (comparisonResult ? "Pass" : "Fail"));
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error writing to log file");
            e.printStackTrace();
        }
        //returns the result so that it can be used for further Assertions in the test code.
        return comparisonResult;
    }
}
