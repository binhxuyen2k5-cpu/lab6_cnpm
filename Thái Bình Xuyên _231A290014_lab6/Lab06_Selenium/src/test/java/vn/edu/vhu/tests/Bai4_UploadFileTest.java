package vn.edu.vhu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import vn.edu.vhu.base.BaseTest;
import vn.edu.vhu.data.StudentData;
import vn.edu.vhu.data.TestData;

/** Bài 4: Tải tệp lên form (ô "Select picture") và kiểm tra tên file. */
public class Bai4_UploadFileTest extends BaseTest {

    @Test
    public void taiTepLenForm() {
        StudentData d = TestData.defaultStudent();
        form.open();

        form.uploadFile(d.uploadPath());

        String value = form.getUploadedFileValue();   // dạng C:\fakepath\avatar.png
        System.out.println("Đường dẫn đã gửi : " + d.uploadPath());
        System.out.println("Giá trị ô upload : " + value);

        Assert.assertTrue(value.endsWith(d.fileName),
                "Tên file upload không đúng: " + value);
    }
}
