package com.xm.mvptest.modules.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.xm.mvptest.app.BaseActivity;
import com.xm.mvptest.app.MyApplication;
import com.xm.mvptest.bean.GetUserResult;
import com.xm.mvptest.serverApi.UserApi;
import com.xm.mvptest.service.SiginService;
import com.xm.mvptest.utils.HttpUtil;
import com.xm.mvptest.utils.ShellUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lib.xm.mvp.base.AbstractPresenter;
import lib.xm.mvp.util.PermissionUtil;
import lib.xm.mvp.util.RxBus;
import lib.xm.mvp.util.ThreadUtil;
import lib.xm.mvp.util.ToastUtil;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2018/5/29.
 */

public class TestPresenter extends AbstractPresenter<TestContract.IView> implements TestContract.IPresenter {
    @Inject
    UserApi userApi;

    public TestPresenter(TestContract.IView iView) {
        super(iView);
    }

    @Override
    public void start() {
        MyApplication.getAppComponent().inject(this);
    }

    @Override
    public void loadData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("lastTime", "");
        HttpUtil.execute(userApi.getUser(HttpUtil.getPostParams(map)), new Observer<GetUserResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(GetUserResult getUserResult) {
                ToastUtil.toastLong("请求成功了：" + getUserResult.getErrorCode());
                iView.setText(getUserResult.toString());
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.toastLong(e.getMessage());
            }

            @Override
            public void onComplete() {
                ToastUtil.toastLong("请求完成");
            }
        });
    }

    @Override
    public void testLog() {
        initData();
        ma();
        iView.setText("日志打印啦");
    }

    @Override
    public void testPermission(Activity context) {
        if (!PermissionUtil.checkPermissionGroup(PermissionUtil.GROUP_SMS)) {
            PermissionUtil.requestPermission(context, PermissionUtil.GROUP_SMS, PermissionUtil.permissionRequestCode);
        }
        iView.setText("请求权限");
    }

    @Override
    public void sigin() {
        RxBus.getIntance().post(SiginService.class.getSimpleName(), "sigin");
    }

    @Override
    public void icona() {
        ComponentName def = new ComponentName((TestActivity)iView, "com.xm.mvptest.modules.launch.WelcomeActivity");
        ComponentName icona = new ComponentName((TestActivity)iView, "com.xm.mvptest.modules.alias.LaunchActivityA");
        ComponentName iconb = new ComponentName((TestActivity)iView, "com.xm.mvptest.modules.alias.LaunchActivityB");
        disEnableAlias(((TestActivity)iView).getComponentName());
        enableAlisa(icona);
    }

    @Override
    public void iconb() {
        ComponentName def = new ComponentName((TestActivity)iView, "com.xm.mvptest.modules.launch.WelcomeActivity");
        ComponentName icona = new ComponentName((TestActivity)iView, "com.xm.mvptest.modules.alias.LaunchActivityA");
        ComponentName iconb = new ComponentName((TestActivity)iView, "com.xm.mvptest.modules.alias.LaunchActivityB");
        disEnableAlias(((TestActivity)iView).getComponentName());
        enableAlisa(iconb);
       restartLauncher();
    }

    private void restartLauncher(){
        //Intent 重启 Launcher 应用
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = MyApplication.getAppContext().getPackageManager().queryIntentActivities(intent,0);
        for (ResolveInfo res :
                resolveInfos) {
            if (res.activityInfo != null) {
                ActivityManager am = (ActivityManager) MyApplication.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
    }

    private void disEnableAlias(ComponentName componentName){
        PackageManager pm = ((TestActivity)iView).getPackageManager();
//        int state = pm.getComponentEnabledSetting(componentName);
//        if (PackageManager.COMPONENT_ENABLED_STATE_ENABLED == state){
            pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
//        }
        restartLauncher();
    }

    private void enableAlisa(ComponentName componentName){
        PackageManager pm = ((TestActivity)iView).getPackageManager();
        int state = pm.getComponentEnabledSetting(componentName);
        if (PackageManager.COMPONENT_ENABLED_STATE_ENABLED != state){
            pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
        }
    }

    School school;
    Student student;
    List<School> schoolList;
    List<Student> studentList;
    Map<String, String> params;

    private void initData() {
        params = new HashMap<>();
        params.put("id", "20090234");
        params.put("userName", "KIMI");
        student = new Student("http://sdfajfdsaf.jjjjkj.dfaafds/jdafa.png", "张三", 22, "男", new Date(System.currentTimeMillis()), 60.15f);
        studentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            studentList.add(student);
        }
        school = new School("2", "社会大学", "湖南", "长沙", studentList);
        schoolList = new ArrayList<>();
        schoolList.add(school);
        schoolList.add(school);
    }

    private void ma() {
        mb();
        md();
    }

    private void mb() {
        mc();
    }

    private void mc() {
        LogUtils.e("我是谁，我在哪，我要干啥");
        //不打印线程信息，打印方法数5
        LogUtils.methodCount(5).e("我是超级明");
        LogUtils.printStack(false).e("不打印调用方法");
        //在子线程打印
        ThreadUtil.start(new Runnable() {
            @Override
            public void run() {
                LogUtils.printThread(true).e("我在那个线程1111");
            }
        });
        LogUtils.e("hello world");
        ThreadUtil.start(new Runnable() {
            @Override
            public void run() {
                LogUtils.printThread(true).e("我在那个线程222");
            }
        });
        //设置tag为‘xxx’
        LogUtils.tag("xxx").e("hahaha");
        ThreadUtil.start(new Runnable() {
            @Override
            public void run() {
                LogUtils.printThread(true).e("我在那个线程333");
            }
        });
        Gson gson = new Gson();
        //打印请求接口返回数据
        LogUtils.printThread(true).printHttpResponseData("https://blog.csdn.net/kjunchen/article/details/50384523", params, "请求成功了", gson.toJson(schoolList));
        //打印json
        LogUtils.json(Log.ERROR, gson.toJson(studentList));
        LogUtils.printThread(true).methodCount(3).json(Log.ERROR, gson.toJson(school));
    }

    private void md() {
        LogUtils.d("debug");
        LogUtils.e("error");
        LogUtils.i("info");
        LogUtils.w("warn");
        LogUtils.v("verbase");
        //保存各类型日志到sd卡
        LogUtils.saveDebugLogToSD("debug to sd");
        LogUtils.saveErrorLogToSD("error to sd");
        LogUtils.saveWarnLogToSD("warn to sd");
        LogUtils.saveThrowableToSD(new RuntimeException());
        LogUtils.saveLogToSD("test", "log to sd");
    }


    /**
     * Created by XMclassmate on 2018/4/11.
     */

    public static class Student {

        private String id;
        private String name;
        private int age;
        private String sex;
        private Date birthday;
        private float weight;

        public Student() {
        }

        public Student(String id, String name, int age, String sex, Date birthday, float weight) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.birthday = birthday;
            this.weight = weight;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }
    }

    /**
     * Created by XMclassmate on 2018/4/11.
     */

    public static class School {

        private String id;
        private String name;
        private String address;
        private String city;
        private List<Student> studentList;

        public School() {
        }

        public School(String id, String name, String address, String city, List<Student> studentList) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.city = city;
            this.studentList = studentList;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Student> getStudentList() {
            return studentList;
        }

        public void setStudentList(List<Student> studentList) {
            this.studentList = studentList;
        }
    }

}
