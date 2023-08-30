package com.example.g_exam_portal;

public class record_class {
    String test_name,test_type,total_marks,Total_question,marks_obtain;
    String id,user_id;

    public record_class(String test_name, String test_type, String total_marks, String total_question, String marks_obtain, String id, String user_id) {
        this.test_name = test_name;
        this.test_type = test_type;
        this.total_marks = total_marks;
        this.Total_question = total_question;
        this.marks_obtain = marks_obtain;
        this.id = id;
        this.user_id = user_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public String getTest_type() {
        return test_type;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public String getTotal_question() {
        return Total_question;
    }

    public String getMarks_obtain() {
        return marks_obtain;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }
}
