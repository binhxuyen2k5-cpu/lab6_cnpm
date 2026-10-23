package vn.edu.vhu.data;

import java.nio.file.Path;
import java.nio.file.Paths;

/** Một bộ dữ liệu đầu vào cho form đăng ký sinh viên. */
public class StudentData {
    public final String firstName;
    public final String lastName;
    public final String email;
    public final String gender;      // Male / Female / Other
    public final String mobile;      // 10 chữ số
    public final int day;
    public final String month;       // tên tháng tiếng Anh đầy đủ: May, June...
    public final int year;
    public final String subject;
    public final String hobby;       // Sports / Reading / Music
    public final String fileName;    // tên file trong src/test/resources/upload
    public final String address;
    public final String state;
    public final String city;

    public StudentData(String firstName, String lastName, String email, String gender,
                       String mobile, int day, String month, int year, String subject,
                       String hobby, String fileName, String address, String state, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
        this.day = day;
        this.month = month;
        this.year = year;
        this.subject = subject;
        this.hobby = hobby;
        this.fileName = fileName;
        this.address = address;
        this.state = state;
        this.city = city;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    /** Đường dẫn tuyệt đối của file cần upload. */
    public String uploadPath() {
        Path p = Paths.get("src", "test", "resources", "upload", fileName);
        return p.toAbsolutePath().toString();
    }

    /** Định dạng hiển thị trong ô date picker: dd MMM yyyy (vd: 15 May 1999). */
    public String expectedDobInput() {
        return String.format("%02d %s %d", day, month.substring(0, 3), year);
    }

    @Override
    public String toString() {
        return fullName() + " | " + email + " | " + gender + " | " + mobile + " | "
                + expectedDobInput() + " | " + subject + " | " + hobby + " | " + fileName
                + " | " + address + " | " + state + " " + city;
    }
}
