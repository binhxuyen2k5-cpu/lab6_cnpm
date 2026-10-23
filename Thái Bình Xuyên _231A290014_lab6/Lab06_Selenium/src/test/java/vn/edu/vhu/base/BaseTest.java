package vn.edu.vhu.base;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import vn.edu.vhu.data.StudentData;
import vn.edu.vhu.pages.PracticeFormPage;
import vn.edu.vhu.utils.ScreenshotUtil;

import java.lang.reflect.Method;

/** Khởi tạo / đóng trình duyệt, chụp màn hình khi test xong, và hàm kiểm tra modal dùng chung. */
public class BaseTest {

    protected WebDriver driver;
    protected PracticeFormPage form;

    @BeforeMethod
    public void setUp(Method method) {
        ChromeOptions options = new ChromeOptions();
        // Không chờ quảng cáo/ảnh tải xong, chỉ cần DOM sẵn sàng (nhanh và ổn định hơn)
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--start-maximized", "--disable-notifications",
                "--remote-allow-origins=*");
        // Chạy không giao diện: mvn test -Dheadless=true
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--window-size=1920,1080",
                    "--no-sandbox", "--disable-dev-shm-usage");
        }
        driver = new ChromeDriver(options);   // Selenium Manager tự tìm ChromeDriver
        form = new PracticeFormPage(driver);
        System.out.println("\n===== BẮT ĐẦU: " + method.getName() + " =====");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            String status = result.isSuccess() ? "PASS" : "FAIL";
            ScreenshotUtil.captureScreen(driver, status + "_" + result.getMethod().getMethodName());
            driver.quit();
        }
        System.out.println("===== KẾT THÚC: " + result.getMethod().getMethodName()
                + " -> " + (result.isSuccess() ? "PASS" : "FAIL") + " =====");
    }

    /** So sánh nội dung modal xác nhận với dữ liệu đã nhập, in kết quả từng dòng ra console. */
    protected void verifyConfirmation(StudentData d) {
        SoftAssert soft = new SoftAssert();

        check(soft, "Student Name", d.fullName(), form.getConfirmationValue("Student Name"));
        check(soft, "Student Email", d.email, form.getConfirmationValue("Student Email"));
        check(soft, "Gender", d.gender, form.getConfirmationValue("Gender"));
        check(soft, "Mobile", d.mobile, form.getConfirmationValue("Mobile"));

        // Modal hiển thị dạng "15 May,2003" -> bỏ dấu phẩy rồi so sánh từng phần
        String dob = form.getConfirmationValue("Date of Birth").replace(",", " ");
        boolean dobOk = Integer.parseInt(dob.trim().split("\\s+")[0]) == d.day
                && dob.contains(d.month.substring(0, 3))
                && dob.contains(String.valueOf(d.year));
        System.out.println("[Kiểm tra] Date of Birth: nhập '" + d.expectedDobInput()
                + "' | modal '" + dob + "' -> " + (dobOk ? "ĐÚNG" : "SAI"));
        soft.assertTrue(dobOk, "Date of Birth không khớp: " + dob);

        check(soft, "Subjects", d.subject, form.getConfirmationValue("Subjects"));
        check(soft, "Hobbies", d.hobby, form.getConfirmationValue("Hobbies"));
        check(soft, "Picture", d.fileName, form.getConfirmationValue("Picture"));
        check(soft, "Address", d.address, form.getConfirmationValue("Address"));
        check(soft, "State and City", d.state + " " + d.city,
                form.getConfirmationValue("State and City"));

        soft.assertAll();
    }

    private void check(SoftAssert soft, String label, String expected, String actual) {
        boolean ok = expected.equals(actual);
        System.out.println("[Kiểm tra] " + label + ": nhập '" + expected + "' | modal '" + actual
                + "' -> " + (ok ? "ĐÚNG" : "SAI"));
        soft.assertEquals(actual, expected, label + " không khớp");
    }
}
