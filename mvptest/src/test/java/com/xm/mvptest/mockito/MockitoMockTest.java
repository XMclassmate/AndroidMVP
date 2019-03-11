package com.xm.mvptest.mockito;


import com.xm.mvptest.bean.School;
import com.xm.mvptest.bean.Student;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.List;

import lib.xm.mvp.util.StringUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by XMclassmate on 2018/8/10.
 */
public class MockitoMockTest {
    private String tag = MockitoMockTest.class.getSimpleName();


    int a = 2;
    int b = 3;
    @Mock
    Student student;

    @Mock
    School school;

    @Mock
    List<Student> list;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @BeforeClass
    public static void before() {
        System.out.println("before");
    }

    @Test
    public void login() throws Exception {
        assertNotNull(student);
    }

    @Test
    public void add() throws Exception {
        Mockito.when(student.getId()).thenReturn(a + "");
        System.out.println(student.getId());
        assertEquals(2 + "", student.getId());
    }

    @Test
    public void ccc() throws Exception {
        System.out.println(StringUtil.getUUID());
    }

    @Test
    public void testThenAnswer() throws Exception {
        Mockito.when(student.eat(Mockito.anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] objs = invocation.getArguments();
                return objs[0].toString() + "真好吃";
            }
        });
        System.out.println(student.eat("banana"));
    }

    @Test
    public void testVerifyAfter() {
        Mockito.when(student.getAge()).thenReturn(18);
        System.out.println(student.getAge());
        System.out.println(System.currentTimeMillis());
        Mockito.verify(student, Mockito.after(1000)).getAge();
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testAtleast() throws Exception {
        student.getAge();
        student.getAge();
        //至少验证两次
        Mockito.verify(student, Mockito.atLeast(2)).getAge();
    }

    @Test
    public void testAtMost() throws Exception {
        student.getAge();
        //至多验证两次
        Mockito.verify(student, Mockito.atMost(2)).getAge();
    }

    @Test
    public void testTimes() throws Exception {
        student.getAge();
        student.getAge();
        //验证方法调用两次
        Mockito.verify(student, Mockito.times(2)).getAge();
    }

    @Test
    public void testTimeOutTimes() throws Exception {
        student.getAge();
        Thread.sleep(1000);
        student.getAge();
        //验证方法在100ms内调用两次
        Mockito.verify(student, Mockito.timeout(100).times(2)).getAge();
    }

    @Test
    public void testAny() throws Exception {
        Mockito.when(student.eat(Mockito.any(String.class))).thenReturn("垃圾");
        System.out.println(student.eat("奶茶"));
    }

    @Test
    public void testContains() throws Exception {
        Mockito.when(student.eat(Mockito.contains("面"))).thenReturn("下面");
        System.out.println(student.eat("方便面"));
    }

    @Test
    public void testArgThat() throws Exception {
        Mockito.when(student.eat(Mockito.argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.length() % 2 == 0;
            }
        }))).thenReturn("快来吃屎");
        System.out.println(student.eat("快来吃面"));
    }

    @AfterClass
    public static void after() {
        System.out.println("after");
    }

}