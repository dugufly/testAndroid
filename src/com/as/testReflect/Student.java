package com.as.testReflect;

import android.util.Log;

public class Student extends Person implements Examination {
    // 年级
    int mGrade;

    public Student(String aName) {
        super(aName);
    }

    public Student(int grade, String aName) {
        super(aName);
        mGrade = grade;
    }

    private void learn(String course) {
    	Log.d("Reflectttt Student",mName + " learn " + course);
    }

    public void takeAnExamination() {
        System.out.println(" takeAnExamination ");
    }

    public String toString() {
        return " Student :  " + mName;
    }
}

//考试接口
 interface Examination {
 public void takeAnExamination();
}
 
 
 
 