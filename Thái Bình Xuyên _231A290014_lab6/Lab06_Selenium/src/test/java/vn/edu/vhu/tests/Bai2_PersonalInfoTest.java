package vn.edu.vhu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import vn.edu.vhu.base.BaseTest;
import vn.edu.vhu.data.StudentData;
import vn.edu.vhu.data.TestData;

/** Bài 2: Điền thông tin cá nhân (Họ, Tên, Email, Số điện thoại) và in ra console. */
public class Bai2_PersonalInfoTest extends BaseTest {

    @Test
    public void dienThongTinCaNhan() {
        StudentData d = TestData.defaultStudent();
        form.open();

        form.enterFirstName(d.firstName);
        form.enterLastName(d.lastName);
        form.enterEmail(d.email);
        form.enterMobile(d.mobile);

        System.out.println("Tên (First Name): " + form.getFirstName());
        System.out.println("Họ (Last Name)  : " + form.getLastName());
        System.out.println("Email           : " + form.getEmail());
        System.out.println("Số điện thoại   : " + form.getMobile());

        Assert.assertEquals(form.getFirstName(), d.firstName);
        Assert.assertEquals(form.getLastName(), d.lastName);
        Assert.assertEquals(form.getEmail(), d.email);
        Assert.assertEquals(form.getMobile(), d.mobile);
    }
}
