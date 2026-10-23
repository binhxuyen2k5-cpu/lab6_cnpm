package vn.edu.vhu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import vn.edu.vhu.base.BaseTest;
import vn.edu.vhu.pages.PracticeFormPage;

/** Bài 1: Mở trang và kiểm tra URL, tiêu đề. */
public class Bai1_OpenPageTest extends BaseTest {

    @Test
    public void moTrangVaKiemTraTieuDe() {
        form.open();

        String url = form.getCurrentUrl();
        String title = form.getTitle();
        System.out.println("URL hiện tại : " + url);
        System.out.println("Tiêu đề trang: " + title);

        Assert.assertTrue(url.startsWith(PracticeFormPage.URL),
                "URL không đúng với trang yêu cầu: " + url);
        Assert.assertFalse(title == null || title.trim().isEmpty(), "Tiêu đề trang bị rỗng");
    }
}
