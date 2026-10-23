package vn.edu.vhu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import vn.edu.vhu.base.BaseTest;
import vn.edu.vhu.data.StudentData;
import vn.edu.vhu.data.TestData;

/** Bài 3: Chọn ngày sinh bằng date picker và kiểm tra định dạng hiển thị. */
public class Bai3_DatePickerTest extends BaseTest {

    @Test
    public void chonNgaySinhBangDatePicker() {
        StudentData d = TestData.defaultStudent();
        form.open();

        form.selectDateOfBirth(d.day, d.month, d.year);

        String actual = form.getDateOfBirthValue();
        System.out.println("Ngày sinh đã chọn : " + d.day + " " + d.month + " " + d.year);
        System.out.println("Giá trị hiển thị  : " + actual);

        Assert.assertTrue(actual.matches("\\d{2} [A-Za-z]{3} \\d{4}"),
                "Sai định dạng dd MMM yyyy: " + actual);
        Assert.assertEquals(actual, d.expectedDobInput(), "Ngày sinh hiển thị không đúng");
    }
}
