package com.xm.mvptest.modules.test;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;

import lib.xm.mvp.util.log.LogUtils;

import static org.junit.Assert.*;

/**
 * Created by XMclassmate on 2018/10/24
 */
@RunWith(PowerMockRunner.class)
public class TestPresenterTest {

    static TestPresenter presenter;

    static TestActivity  iView;

    @Mock
    TestPresenter.Student student;

    @BeforeClass
    public static void init(){
        iView = PowerMockito.mock(TestActivity.class);
        presenter = new TestPresenter(iView);
    }
    @Test
    public void start() {
        presenter.start();
        assertNotNull(presenter.userApi);
        student.setAge(12);
        LogUtils.e(student.getAge()+"");
        assertNotNull(iView);
    }

    @Test
    public void loadData() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LogUtils.tag("aaa").e(invocation.getArgument(0).toString());

                return invocation.getArgument(0).toString();
            }
        }).when(iView).setText(Mockito.anyString());
        presenter.loadData();
        Mockito.verify(iView).setText(Mockito.anyString());
    }

    @Test
    public void testLog() {
        Mockito.doNothing().when(iView).setText(Mockito.anyString());
        presenter.testLog();
        Mockito.verify(iView).setText("日志打印啦");
    }

    @Test
    public void testPermission() {

    }
}