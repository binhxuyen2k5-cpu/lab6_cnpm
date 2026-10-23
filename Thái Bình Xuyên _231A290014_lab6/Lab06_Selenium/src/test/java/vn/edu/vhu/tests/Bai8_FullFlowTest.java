package vn.edu.vhu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import vn.edu.vhu.base.BaseTest;
import vn.edu.vhu.data.StudentData;
import vn.edu.vhu.data.TestData;
import vn.edu.vhu.pages.PracticeFormPage;
import vn.edu.vhu.utils.ScreenshotUtil;

/**
 * Bài 8: Quy trình hoàn chỉnh: mở trang -> nhập dữ liệu -> chọn tùy chọn -> upload
 * -> chụp ảnh trước submit -> submit -> chờ modal -> chụp ảnh sau submit -> kiểm tra modal.
 */
public class Bai8_FullFlowTest extends BaseTest {

    @Test
    public void quyTrinhHoanChinhCuaForm() {
        StudentData d = TestData.defaultStudent();

        System.out.println("Bước 1: Mở trang");
        form.open();
        Assert.assertTrue(form.getCurrentUrl().startsWith(PracticeFormPage.URL));

        System.out.println("Bước 2: Nhập thông tin, chọn giới tính/ngày sinh/môn học/sở thích,"
                + " upload file, chọn State & City");
        form.fillForm(d);

        System.out.println("Bước 3: Chụp màn hình TRƯỚC khi submit");
        ScreenshotUtil.captureScreen(driver, "TRUOC_submit");

        System.out.println("Bước 4: Submit và chờ modal xác nhận");
        form.submit();
        String title = form.waitForConfirmationModal();
        Assert.assertEquals(title, "Thanks for submitting the form");

        System.out.println("Bước 5: Chụp màn hình SAU khi submit");
        ScreenshotUtil.captureScreen(driver, "SAU_submit");

        System.out.println("Bước 6: Kiểm tra nội dung modal");
        System.out.println("Tiêu đề modal: " + title);
        verifyConfirmation(d);

        form.closeConfirmationModal();
        System.out.println("KẾT QUẢ: Quy trình hoàn chỉnh PASS");
    }
}
