package com.example.demo.service;

import com.example.demo.entity.RegularTestSet;
import com.example.demo.entity.SchoolRecord;
import com.example.demo.entity.SchoolRecordSet;
import com.example.demo.entity.Student;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TermAndYearService {
    public final LocalDate maxLocalDate = LocalDate.of(9999, 12, 31);
    public final LocalDate minLocalDate = LocalDate.of(1000, 1, 1);





    public List<String> getAvailableGrades() {
        String availableGrades = "小１,小２,小３,小４,小５,小６,中１,中２,中３,高１,高２,高３";
        return Arrays.asList(availableGrades.split(","));
    }
    //method to get today's date dynamically
    public LocalDate getToday(){
        return LocalDate.now();
    }



    public LocalDate getTodayAsLocalDate(){
        return LocalDate.now();
    }

    public LocalDate getThisYearApril1(){
        return LocalDate.now().withMonth(4).withDayOfMonth(1);
    }

    public int getCurrentYear(){
        return this.getToday().getYear();
    }

    //Studentのel1から学年(1から12)を取得する
    public Integer getSchoolGrade(Student student){

        LocalDate thisYearApril1 = this.getThisYearApril1();


        LocalDate today = this.getToday();

        int currentYear = this.getCurrentYear();

        int grade;

        if(today.isBefore(thisYearApril1)){
            grade = currentYear - student.getEl1();
        }else{
            grade = currentYear - student.getEl1() +1;
        }

        return grade;
    }

    //CSV読み込み時に
    public int getWhenEnteredElementarySchoolForCsvReader(String grade,LocalDate date){
        int thatYear = date.getYear();//実施年
        Integer absGrade=convertGrade(grade);
        LocalDate thatYearApril = LocalDate.of(thatYear, 4, 1);

        if(date.isBefore(thatYearApril)){
            return thatYear-absGrade;
        }else{
            return thatYear+1-absGrade;
        }

    }

    //小学一年生は1高校3年生は12の数値から小学校入学時(3/31)時点の西暦を算出する関数
    public int getWhenEnteredElementarySchool(int abGrade){

        // 今年の4月1日を取得
        LocalDate thisYearApril1 = this.getThisYearApril1();

        // 今日の日付を取得
        LocalDate today = this.getToday();

        //現在の年を取得
        int currentYear = this.getCurrentYear();

        if(today.isBefore(thisYearApril1)){
            return currentYear - abGrade;
        }else{
            return currentYear + 1 - abGrade;
        }
    }

    public int getWhenEnteredElementarySchoolForJuniorHighSchoolStudent(int jhGrade){
        return getWhenEnteredElementarySchool(jhGrade+6);
    }

    public Integer getEl1ForJhUsingTermAndGrade(Integer term,Integer jhGrade){
        return term-jhGrade-5;
    }

    //現在の年度を取得する関数
    public Integer getTerm(){
        // 今年の4月1日を取得
        LocalDate thisYearApril1 = this.getThisYearApril1();

        // 今日の日付を取得
        LocalDate today = this.getToday();

        int currentYear = this.getCurrentYear();

        if(today.isBefore(thisYearApril1)){
            return currentYear -1;
        }else{
            return currentYear;
        }

    }

    public static Integer getTerm(java.sql.Date sqlDate) {
        // Calendarインスタンスを作成
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sqlDate);

        // 年と月を取得
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // 0から11までの値

        // 4月より前なら前年の年度を返す
        if (month < Calendar.APRIL) {
            return year - 1;
        } else {
            return year; // それ以外は現在の年度を返す
        }
    }

    public Integer convertGrade(String grade) {
        switch (grade) {
            case "小１":
                return 1;
            case "小２":
                return 2;
            case "小３":
                return 3;
            case "小４":
                return 4;
            case "小５":
                return 5;
            case "小６":
                return 6;
            case "中１":
                return 7;
            case "中２":
                return 8;
            case "中３":
                return 9;
            case "高１":
                return 10;
            case "高２":
                return 11;
            case "高３":
                return 12;
            default:
                throw new IllegalArgumentException("不明な学年: " + grade);
        }
    }

    public String convertGradeBack(Integer grade) {
        switch (grade) {
            case 1:
                return "小１";
            case 2:
                return "小２";
            case 3:
                return "小３";
            case 4:
                return "小４";
            case 5:
                return "小５";
            case 6:
                return "小６";
            case 7:
                return "中１";
            case 8:
                return "中２";
            case 9:
                return "中３";
            case 10:
                return "高１";
            case 11:
                return "高２";
            case 12:
                return "高３";
            default:
                return "未定義";
        }
    }



    public LocalDate getSemesterDate(int term, int semester, Integer isMid) {
        if (isMid == null || (isMid != 0 && isMid != 1)) {
            throw new IllegalArgumentException("isMidは0（期末）または1（中間）である必要があります");
        }

        LocalDate date;
        int year = term; // termを年度として利用

        switch (semester) {
            case 1: // 第1学期
                if (isMid == 1) {
                    date = LocalDate.of(year, 5, 31); // 5月末を中間とする
                } else {
                    date = LocalDate.of(year, 7, 31); // 7月末を期末とする
                }
                break;

            case 2: // 第2学期
                if (isMid == 1) {
                    date = LocalDate.of(year, 10, 31); // 10月末を中間とする
                } else {
                    date = LocalDate.of(year, 12, 31); // 12月末を期末とする
                }
                break;

            case 3: // 第3学期
                if (isMid == 1) {
                    date = LocalDate.of(year + 1, 2, 15); // 翌年2月中旬を中間とする
                } else {
                    date = LocalDate.of(year + 1, 3, 31); // 翌年3月末を期末とする
                }
                break;

            default:
                throw new IllegalArgumentException("無効な学期番号: " + semester);
        }

        return date;
    }

    public LocalDate getSemesterEndDate(int term, int semester) {
        LocalDate endDate;

        // 年度を指定する。例: 2023年度
        int year = term; // termを年度として利用

        switch (semester) {
            case 1: // 第1学期
                endDate = LocalDate.of(year, 7, 31); // 7月31日
                break;
            case 2: // 第2学期
                endDate = LocalDate.of(year, 12, 31); // 12月31日
                break;
            case 3: // 第3学期
                endDate = LocalDate.of(year + 1, 3, 31); // 翌年の3月31日
                break;
            default:
                throw new IllegalArgumentException("無効な学期番号: " + semester);
        }

        // LocalDateをjava.sql.Dateに変換
        return endDate;
    }

    //todo:現学年をel1から計算する
    public String getCurrentGradeFromEl1(Integer el1) {
        Integer currentTerm = this.getTerm();
        log.info("currentTerm:{}",currentTerm);
        Integer absGrade = currentTerm - el1 +1;
        log.info("absGradeは:{}",absGrade);
        return this.convertGradeBack(absGrade);
    }

    public Integer getWhenEnteredElementarySchoolForJuniorHighSchoolStudentForPast(Integer term, Integer jhGrade) {
        return term-jhGrade-5;
    }

    public Integer getWhenEnteredElementarySchoolForJh(LocalDate date,Integer jhGrade){
        Integer term = getTerm(java.sql.Date.valueOf(date));
        return getWhenEnteredElementarySchoolForJuniorHighSchoolStudentForPast(term,jhGrade);
    }

    public LocalDate getDateForSchoolRecordSet(SchoolRecordSet schoolRecordSet) {
        return getSemesterDate(
                schoolRecordSet.getTerm(),
                schoolRecordSet.getSemester(),
                0
        );
    }

    public LocalDate getDateForSchoolRecord(SchoolRecord schoolRecord) {
        return getDateForSchoolRecordSet(schoolRecord.getSchoolRecordSet());
    }

    public LocalDate getDateForRegularTestSet(RegularTestSet regularTestSet) {
        return getSemesterDate(
                regularTestSet.getTerm(),
                regularTestSet.getSemester(),
                regularTestSet.getIsMid()
        );
    }

    public Integer getGrade(Student student, LocalDate date) {
        LocalDate targetYearApril1 = LocalDate.of(date.getYear(), 4, 1);

        int grade;

        if (date.isBefore(targetYearApril1)) {
            grade = date.getYear() - student.getEl1();
        } else {
            grade = date.getYear() - student.getEl1() + 1;
        }

        return grade;
    }
}
