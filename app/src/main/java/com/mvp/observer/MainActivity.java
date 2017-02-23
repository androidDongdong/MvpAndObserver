package com.mvp.observer;

import android.os.Bundle;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.observer.data.DbUserManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BtnUserClickListener{

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    UserAdapter historyRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit)
    public void onClick() {
        String name = username.getText().toString().trim();
        String nick = nickname.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"姓名",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(nick)){
            Toast.makeText(this,"匿名",Toast.LENGTH_SHORT).show();
        }else{
            DbUserManager.initDatabase(this).addUser(name,nick);
            username.setText(DbUserManager.initDatabase(this).queryRecord()+"");
            List<User> userList = DbUserManager.initDatabase(this).queryUserListAll();
            initRecyclerView(userList);
        }
    }

    public void initRecyclerView(List<User> list) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 为RecyclerView指定布局管理对象
        recyclerView.setLayoutManager(layoutManager);
        // 创建Adapter
        historyRecordAdapter = new UserAdapter(this, list);
        historyRecordAdapter.setBtnUserClickListener(this);
        recyclerView.setAdapter(historyRecordAdapter);
    }

    @Override
    public void delete(User user) {
        DbUserManager.initDatabase(this).deleteUserById(Integer.parseInt(user.getId()+""));
        List<User> userList = DbUserManager.initDatabase(this).queryUserListAll();
        historyRecordAdapter.setData(userList);
    }

    @Override
    public void update(User user) {

    }
}
