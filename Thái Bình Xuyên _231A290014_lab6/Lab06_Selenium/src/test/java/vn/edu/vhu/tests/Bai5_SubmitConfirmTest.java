package vn.edu.vhu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import vn.edu.vhu.base.BaseTest;
import vn.edu.vhu.data.StudentData;
import vn.edu.vhu.data.TestData;

/** Bài 5: Submit form và kiểm tra nội dung modal xác nhận. */
public class Bai5_SubmitConfirmTest extends BaseTest {

    @Test
    public void kiemTraModalSauKhiSubmit() {
        StudentData d = TestData.defaultStudent();
        form.open();
        form.fillForm(d);
        form.submit();

        String title = form.waitForConfirmationModal();
        System.out.println("Tiêu đề modal: " + title);
        Assert.assertEquals(title, "Thanks for submitting the form");

        verifyConfirmation(d);
        System.out.println("KẾT QUẢ: nội dung xác nhận khớp với dữ liệu đã nhập.");
    }
}
