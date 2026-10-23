package vn.edu.vhu.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vhu.base.BaseTest;
import vn.edu.vhu.data.StudentData;
import vn.edu.vhu.data.TestData;

/** Bài 7: Một test case chạy với 3 bộ dữ liệu khác nhau (@DataProvider). */
public class Bai7_DataDrivenTest extends BaseTest {

    @DataProvider(name = "students")
    public Object[][] students() {
        StudentData[] all = TestData.allStudents();
        Object[][] data = new Object[all.length][1];
        for (int i = 0; i < all.length; i++) {
            data[i][0] = all[i];
        }
        return data;
    }

    @Test(dataProvider = "students")
    public void submitFormVoiNhieuBoDuLieu(StudentData d) {
        System.out.println(">>> Bộ dữ liệu: " + d);

        form.open();
        form.fillForm(d);
        form.submit();

        Assert.assertEquals(form.waitForConfirmationModal(), "Thanks for submitting the form");
        verifyConfirmation(d);
        System.out.println(">>> KẾT QUẢ bộ dữ liệu [" + d.fullName() + "]: PASS");
    }
}
