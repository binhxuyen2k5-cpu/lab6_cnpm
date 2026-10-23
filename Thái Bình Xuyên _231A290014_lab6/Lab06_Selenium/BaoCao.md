# BÁO CÁO THỰC HÀNH LAB 06 – SELENIUM WEBDRIVER

- **Sinh viên:** Thái Bình Xuyên – **MSSV:** 231A290014
- **Website kiểm thử:** DemoQA Practice Form – https://demoqa.com/automation-practice-form
- **Công nghệ:** Java 11+, Maven, Selenium WebDriver 4.25, TestNG 7.10, Google Chrome

## 1. Cấu trúc project

```
Lab06_Selenium
├── pom.xml                      (Selenium, TestNG, commons-io)
├── testng.xml                   (danh sách lớp test)
├── screenshots/                 (ảnh chụp màn hình sinh ra khi chạy)
└── src/test
    ├── java/vn/edu/vhu
    │   ├── pages/PracticeFormPage.java   (Page Object: locator + hành động)
    │   ├── base/BaseTest.java            (mở/đóng Chrome, chụp ảnh, kiểm tra modal)
    │   ├── data/StudentData.java, TestData.java  (3 bộ dữ liệu)
    │   ├── utils/ScreenshotUtil.java     (mẫu chụp màn hình của đề)
    │   └── tests/Bai1 ... Bai8
    └── resources/upload/avatar.png, sample.txt
```

## 2. Cách chạy

```
mvn clean test                     (mở Chrome có giao diện)
mvn clean test -Dheadless=true     (chạy ẩn)
```
Ảnh chụp được lưu trong thư mục `screenshots/`.

## 3. Mô tả các bài

| Bài | Lớp test | Nội dung kiểm thử |
|-----|----------|-------------------|
| 1 | Bai1_OpenPageTest | Mở Chrome, vào trang form, kiểm tra URL đúng và tiêu đề không rỗng |
| 2 | Bai2_PersonalInfoTest | Điền Họ, Tên, Email, SĐT; in giá trị ra console và so sánh |
| 3 | Bai3_DatePickerTest | Chọn ngày sinh bằng date picker (tháng/năm/ngày), kiểm tra định dạng `dd MMM yyyy` |
| 4 | Bai4_UploadFileTest | Gửi đường dẫn file vào ô upload, kiểm tra tên file hiển thị |
| 5 | Bai5_SubmitConfirmTest | Điền đủ form, Submit, chờ modal, so sánh từng dòng với dữ liệu nhập, in kết quả |
| 6 | PracticeFormPage + BaseTest | Page Object Model: lớp Page giữ locator và thao tác, lớp test chỉ giữ logic kiểm thử |
| 7 | Bai7_DataDrivenTest | `@DataProvider` với 3 bộ dữ liệu, mỗi bộ có kết quả và log riêng |
| 8 | Bai8_FullFlowTest | Quy trình hoàn chỉnh, chụp ảnh trước và sau submit |

## 4. Explicit wait đã dùng (trong PracticeFormPage)

1. `visibilityOfElementLocated` – chờ form/ô nhập hiển thị trước khi thao tác (`open()`, `type()`).
2. `elementToBeClickable` – chờ ngày trong date picker, tùy chọn State/City có thể bấm.
3. `visibilityOfElementLocated(modalTitle)` – chờ modal xác nhận xuất hiện sau Submit.
4. `invisibilityOfElementLocated` – chờ modal đóng.
5. `presenceOfElementLocated` – chờ ô upload, nhãn radio/checkbox xuất hiện trong DOM.

## 5. Luồng kiểm thử hoàn chỉnh (Bài 8)

1. Mở trang, kiểm tra URL.
2. Nhập Họ, Tên, Email, SĐT, chọn giới tính, ngày sinh (date picker), môn học, sở thích, upload ảnh, nhập địa chỉ, chọn State/City.
3. Chụp ảnh `TRUOC_submit_*.png`.
4. Nhấn Submit, chờ modal "Thanks for submitting the form".
5. Chụp ảnh `SAU_submit_*.png`.
6. So sánh các dòng trong modal (Student Name, Email, Gender, Mobile, Date of Birth, Subjects, Hobbies, Picture, Address, State and City) với dữ liệu đã nhập, in ĐÚNG/SAI từng dòng ra console.
7. Đóng modal, kết thúc.

Sau mỗi test, `BaseTest` tự chụp ảnh `PASS_*` hoặc `FAIL_*` rồi đóng trình duyệt.

## 6. Bộ dữ liệu cho Bài 7

| # | Họ tên | Giới tính | Ngày sinh | Môn học | Sở thích | File | State / City |
|---|--------|-----------|-----------|---------|----------|------|--------------|
| 1 | Xuyen Thai Binh | Male | 15 May 2003 | Maths | Sports | avatar.png | NCR / Delhi |
| 2 | An Nguyen Van | Female | 03 Jan 2002 | English | Reading | sample.txt | Uttar Pradesh / Agra |
| 3 | Binh Tran Thi | Other | 28 Dec 2001 | Physics | Music | avatar.png | Haryana / Karnal |


