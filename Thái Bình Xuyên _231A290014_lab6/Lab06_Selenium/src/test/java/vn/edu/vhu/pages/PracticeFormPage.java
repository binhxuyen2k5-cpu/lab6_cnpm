package vn.edu.vhu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import vn.edu.vhu.data.StudentData;

import java.time.Duration;

/**
 * Page Object cho trang https://demoqa.com/automation-practice-form
 * Chứa toàn bộ locator và các hành động thao tác giao diện (có explicit wait).
 */
public class PracticeFormPage {

    public static final String URL = "https://demoqa.com/automation-practice-form";

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ---------- Locators ----------
    private final By firstName = By.id("firstName");
    private final By lastName = By.id("lastName");
    private final By email = By.id("userEmail");
    private final By mobile = By.id("userNumber");
    private final By dobInput = By.id("dateOfBirthInput");
    private final By monthSelect = By.cssSelector(".react-datepicker__month-select");
    private final By yearSelect = By.cssSelector(".react-datepicker__year-select");
    private final By subjectsInput = By.id("subjectsInput");
    private final By subjectOption = By.cssSelector(".subjects-auto-complete__option");
    private final By uploadPicture = By.id("uploadPicture");
    private final By currentAddress = By.id("currentAddress");
    private final By stateDropdown = By.id("state");
    private final By cityDropdown = By.id("city");
    private final By submitButton = By.id("submit");
    private final By modalTitle = By.id("example-modal-sizes-title-lg");
    private final By closeModalButton = By.id("closeLargeModal");

    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ---------- Helpers nội bộ ----------
    private WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", element);
    }

    private void type(By locator, String text) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(text);
    }

    /** Xóa quảng cáo / footer hay che mất các nút ở cuối form. */
    private void removeAds() {
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('#fixedban, footer, iframe').forEach(e => e.remove());");
    }

    // ---------- Mở trang / thông tin trang ----------
    public void open() {
        driver.get(URL);
        try {
            visible(firstName);      // explicit wait: form đã hiển thị
        } catch (TimeoutException e) {
            driver.navigate().refresh();   // lần đầu mở Chrome có thể chậm -> tải lại 1 lần
            visible(firstName);
        }
        removeAds();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    // ---------- Thông tin cá nhân ----------
    public void enterFirstName(String value) { type(firstName, value); }
    public void enterLastName(String value) { type(lastName, value); }
    public void enterEmail(String value) { type(email, value); }
    public void enterMobile(String value) { type(mobile, value); }

    public String getFirstName() { return visible(firstName).getAttribute("value"); }
    public String getLastName() { return visible(lastName).getAttribute("value"); }
    public String getEmail() { return visible(email).getAttribute("value"); }
    public String getMobile() { return visible(mobile).getAttribute("value"); }

    public void selectGender(String gender) {
        By label = By.xpath("//label[normalize-space()='" + gender + "' and contains(@for,'gender-radio')]");
        jsClick(wait.until(ExpectedConditions.presenceOfElementLocated(label)));
    }

    public void enterAddress(String value) { type(currentAddress, value); }

    // ---------- Date picker ----------
    public void selectDateOfBirth(int day, String month, int year) {
        jsClick(visible(dobInput));
        new Select(visible(monthSelect)).selectByVisibleText(month);
        new Select(visible(yearSelect)).selectByValue(String.valueOf(year));
        By dayLocator = By.cssSelector(".react-datepicker__day--" + String.format("%03d", day)
                + ":not(.react-datepicker__day--outside-month)");
        wait.until(ExpectedConditions.elementToBeClickable(dayLocator)).click();
    }

    public String getDateOfBirthValue() {
        return visible(dobInput).getAttribute("value");
    }

    // ---------- Môn học, sở thích ----------
    public void selectSubject(String subject) {
        visible(subjectsInput).sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOfElementLocated(subjectOption));
        visible(subjectsInput).sendKeys(Keys.ENTER);
    }

    public void selectHobby(String hobby) {
        By label = By.xpath("//label[normalize-space()='" + hobby + "' and contains(@for,'hobbies-checkbox')]");
        jsClick(wait.until(ExpectedConditions.presenceOfElementLocated(label)));
    }

    // ---------- Upload ----------
    public void uploadFile(String absolutePath) {
        wait.until(ExpectedConditions.presenceOfElementLocated(uploadPicture)).sendKeys(absolutePath);
    }

    /** Trình duyệt trả về dạng C:\fakepath\ten_file. */
    public String getUploadedFileValue() {
        return driver.findElement(uploadPicture).getAttribute("value");
    }

    // ---------- State / City ----------
    /**
     * react-select chỉ mở menu khi nhận sự kiện mousedown của chuột thật,
     * nên phải cuộn tới ô rồi dùng click() của Selenium (không dùng JS click).
     */
    private void openDropdown(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void chooseDropdownOption(String text) {
        By option = By.xpath("//div[contains(@id,'-option-') and normalize-space()='" + text + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void selectState(String state) {
        openDropdown(stateDropdown);
        chooseDropdownOption(state);
    }

    public void selectCity(String city) {
        openDropdown(cityDropdown);      // ô City chỉ bấm được sau khi đã chọn State
        chooseDropdownOption(city);
    }

    // ---------- Điền toàn bộ form ----------
    public void fillForm(StudentData d) {
        enterFirstName(d.firstName);
        enterLastName(d.lastName);
        enterEmail(d.email);
        selectGender(d.gender);
        enterMobile(d.mobile);
        selectDateOfBirth(d.day, d.month, d.year);
        selectSubject(d.subject);
        selectHobby(d.hobby);
        uploadFile(d.uploadPath());
        enterAddress(d.address);
        selectState(d.state);
        selectCity(d.city);
    }

    // ---------- Submit & modal xác nhận ----------
    public void submit() {
        jsClick(visible(submitButton));
    }

    /** Chờ modal xuất hiện và trả về tiêu đề của modal. */
    public String waitForConfirmationModal() {
        return visible(modalTitle).getText();
    }

    /** Lấy giá trị của một dòng trong bảng xác nhận, vd label = "Student Name". */
    public String getConfirmationValue(String label) {
        By cell = By.xpath("//div[contains(@class,'modal-content')]//td[normalize-space()='" + label
                + "']/following-sibling::td");
        return visible(cell).getText().trim();
    }

    public void closeConfirmationModal() {
        try {
            WebElement btn = visible(closeModalButton);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", btn);
            wait.until(ExpectedConditions.elementToBeClickable(closeModalButton)).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTitle));
        } catch (TimeoutException e) {
            // Dự phòng: đóng modal bằng phím ESC
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTitle));
        }
    }
}
