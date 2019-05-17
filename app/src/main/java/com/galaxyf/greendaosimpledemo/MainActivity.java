package com.galaxyf.greendaosimpledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.galaxyf.greendaosimpledemo.db.DBManager;
import com.galaxyf.greendaosimpledemo.db.DBUtils;
import com.galaxyf.greendaosimpledemo.entity.User;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.Random;

import static com.galaxyf.greendaosimpledemo.db.DBUtils.PASSWORD;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBManager manager = DBManager.getSingleInstance().init(MainActivity.this,PASSWORD);
        tvContent = findViewById(R.id.tvContent);
        Button operate = findViewById(R.id.insertData);
        operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("098","124","###",null, "zhangsan" + new Random().nextInt(9999),"张三");

                //插入
                manager.getDaoSession(MainActivity.this,PASSWORD).getUserDao().insert(user);

                QueryBuilder<User> queryBuilder =
                        manager.getDaoSession(MainActivity.this, PASSWORD).getUserDao().queryBuilder();
                List<User> list = queryBuilder.build().list();
                Log.i(TAG, "onClick: user:"+user.toString());
                Log.i(TAG, "onClick: list:"+list.toString());

            }
        });
        findViewById(R.id.deleteByKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils.deleteByKey(MainActivity.this, 9);
                Log.i(TAG, "onClick: delete 9:");
            }
        });
        findViewById(R.id.deleteData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("12423","12","###",null, "zhangsan","张三");
                DBUtils.delete(MainActivity.this, user);
                tvContent.setText(user.toString());
                Log.i(TAG, "onClick:delete user:"+user.toString());
            }
        });
        findViewById(R.id.queryData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询
                List<User> users = DBUtils.queryAll(MainActivity.this);
                tvContent.setText(users.toString());
                Log.i(TAG, "onClick: users:"+users.toString());
            }
        });
        findViewById(R.id.queryId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> users = DBUtils.queryById(MainActivity.this, 2);
                tvContent.setText(users.toString());
                Log.i(TAG, "onClick: users:9="+users.toString());
            }
        });
        findViewById(R.id.updateId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("324","534","###",0L, "zhangsan","张三");
                User newUser = new User("1345","09","###",3L, "zhangsan","张五");
                User updateData = DBUtils.updateData(MainActivity.this, user, newUser);
                Log.i(TAG, "onClick: updateData:"+updateData);
            }
        });

        findViewById(R.id.queryByPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> users = DBUtils.queryByPage(MainActivity.this, 1, 2);
                tvContent.setText(users.toString());

                Log.i(TAG, "onClick:queryByPage users:"+users.toString());
            }
        });

        /**
        findViewById(R.id.insertStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("324","534","###",3L, "zhangsan","张三");
                Student student = new Student(89L, "倒计时",90, 3L);
                DBUtils.insertStudent(MainActivity.this,user, student );
            }
        });
        findViewById(R.id.queryStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("324","534","###",3L, "zhangsan","张三");
                List<Student> studentList = DBUtils.queryStudentByUser(MainActivity.this, user);
                Log.i(TAG, "onClick: queryStudent:"+studentList.toString());
            }
        });
         */
    }


}

