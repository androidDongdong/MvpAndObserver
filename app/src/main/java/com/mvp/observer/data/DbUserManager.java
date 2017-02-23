package com.mvp.observer.data;

import android.content.Context;

import com.mvp.observer.DaoMaster;
import com.mvp.observer.DaoSession;
import com.mvp.observer.User;
import com.mvp.observer.UserDao;

import java.util.List;
import java.util.Random;

/**
 * Created by dd on 2016/10/12.
 */

public class DbUserManager {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    public static DbUserManager initDatabase(Context context){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), "mvp.db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
        return new DbUserManager();
    }
    public UserDao getUserDao(){
        return daoSession.getUserDao();
    }

    public void addUser(String name,String nick){
        User user = new User(null, name,nick);
        getUserDao().insert(user);
    }

    public long queryRecord(){
        return getUserDao().queryBuilder().count();
    }

    public List<User> queryUserListAll(){
        List<User> userList = getUserDao().loadAll();
        return userList;
    }

    public void deleteUser(int num){
        /**
         *  查询id小于几的数据集合，进行删除
         */
        List<User> userList = (List<User>) getUserDao().queryBuilder().where(UserDao.Properties.Id.le(num)).build().list();
        for (User user : userList) {
            getUserDao().delete(user);
        }
    }

    public void deleteUserById(int id){
        User user = getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(id)).build().unique();
        if (user == null) {
            //用户不存在
        }else{
            getUserDao().deleteByKey(user.getId());
        }
    }

    public void deleteAllUser(){
        getUserDao().deleteAll();
    }

    //一个是id要大于等于10，同是还要满足username like %90%，注意最后的unique表示只查询一条数据出来即
    public void updateUser(int id){
        User user = getUserDao().queryBuilder()
                .where(UserDao.Properties.Id.ge(10), UserDao.Properties.Username.like("%90%")).build().unique();
        if (user == null) {
            //用户不存在
        }else{
            user.setUsername("王五");
            getUserDao().update(user);
        }
    }

  //查询id介于2到13之间的数据，limit表示查询5条数据。
    public List<User> queryUserList(){
        List<User> list = getUserDao().queryBuilder()
                .where(UserDao.Properties.Id.between(2, 13)).limit(5).build().list();
        return  list;
    }
}
