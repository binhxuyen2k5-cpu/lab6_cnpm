package vn.edu.vhu.data;

/** Các bộ dữ liệu kiểm thử (bài 7 dùng cả 3 bộ, các bài khác dùng bộ đầu tiên). */
public class TestData {

    public static StudentData defaultStudent() {
        return new StudentData("Xuyen", "Thai Binh", "xuyen.thai@example.com", "Male",
                "0901234567", 15, "May", 2003, "Maths", "Sports", "avatar.png",
                "123 Nguyen Van Linh, Quan 7, TP.HCM", "NCR", "Delhi");
    }

    public static StudentData[] allStudents() {
        return new StudentData[]{
                defaultStudent(),
                new StudentData("An", "Nguyen Van", "an.nguyen@example.com", "Female",
                        "0912345678", 3, "January", 2002, "English", "Reading", "sample.txt",
                        "45 Le Loi, Quan 1, TP.HCM", "Uttar Pradesh", "Agra"),
                new StudentData("Binh", "Tran Thi", "binh.tran@example.com", "Other",
                        "0987654321", 28, "December", 2001, "Physics", "Music", "avatar.png",
                        "78 Hai Ba Trung, Quan 3, TP.HCM", "Haryana", "Karnal")
        };
    }
}
