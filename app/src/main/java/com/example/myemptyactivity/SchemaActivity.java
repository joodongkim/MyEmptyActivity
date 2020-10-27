package com.example.myemptyactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Set;

public class SchemaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schema);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SchemaHelper sh = new SchemaHelper(this);

        //학생을 추가하고 그 ID를 반환한다.
        long sid1 = sh.addStudent("Jason wei", "IL", 12);
        long sid2 = sh.addStudent("Du Chung", "AR", 12);
        long sid3 = sh.addStudent("George Tang", "CA", 11);
        long sid4 = sh.addStudent("Mark Bocanegra", "CA", 11);
        long sid5 = sh.addStudent("Bobby Wei", "IL", 12);

        //과정을 추가하고 그 ID를 반환한다.
        long cid1 = sh.addCourse("Math51");
        long cid2 = sh.addCourse("CS106A");
        long cid3 = sh.addCourse("Econ1A");

        //수업에 학생을 등록한다.
        sh.enrollStudentClass((int)sid1, (int)cid1);
        sh.enrollStudentClass((int)sid1, (int)cid2);
        sh.enrollStudentClass((int)sid2, (int)cid2);
        sh.enrollStudentClass((int)sid3, (int)cid1);
        sh.enrollStudentClass((int)sid3, (int)cid2);
        sh.enrollStudentClass((int)sid4, (int)cid3);
        sh.enrollStudentClass((int)sid5, (int)cid2);

        //과정에 대한 학생을 얻어온다.
        Cursor c =sh.getStudentsForCourse((int)cid2);
        while(c.moveToNext()) {
            int sid = c.getInt(c.getColumnIndex(ClassTable.STUDENT_ID));
            Log.d("SCHEMA", "STUDENT " + sid + " IS ENROLLED IN COURSE " + cid2);
        }

        //과정에 대한 학생을 얻어와 학년으로 필터링한다.
        Set<Integer> sids = sh.getStudentsByGradeForCourse((int) cid2, 11);
        for (Integer sid : sids) {
            Log.d("SCHEMA", "STUDENT " + sid + " OF GRADE 11 IS ENROLLED IN COURSE " + cid2);
        }

        //원하는 클래스를 얻어온다.
        c = sh.getCoursesForStudent((int)sid1);
        while(c.moveToNext()) {
            int cid = c.getInt(c.getColumnIndex(ClassTable.COURSE_ID));
            Log.d("SCHEMA", "STUDENT " + sid1 + " IS ENROLLED IN COURSE " + cid);
        }

        //과정 삭제를 시도한다.
        sh.removeCourse((int)cid1);
        Log.d("SCHEMA", "------------------------------");

        //삭제 내용이 스키마에 유지되는지 확인한다.
        c = sh.getCoursesForStudent((int)sid1);
        while(c.moveToNext()) {
            int cid = c.getInt(c.getColumnIndex(ClassTable.COURSE_ID));
            Log.d("SCHEMA", "STUDENT " + sid1 + " IS ENROLLED IN COURSE " + cid);
        }
    }
}