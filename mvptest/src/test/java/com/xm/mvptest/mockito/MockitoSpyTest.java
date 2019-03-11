package com.xm.mvptest.mockito;

import com.xm.mvptest.bean.Student;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Created by XMclassmate on 2018/8/15.
 */

public class MockitoSpyTest {

    @Spy
    Student student, student1;

    @InjectMocks
    Group group;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testNotNull() {
        Assert.assertNotNull(student);
    }

    @Test
    public void testStudentSpy() {
        System.out.println(student.getAge());
    }

    @Test
    public void testInOrder() {
        student.setName("xiaoming");
        student.setAge(21);
        student1.setName("yaya");
        student1.setAge(18);

        InOrder inOrder = Mockito.inOrder(student, student1);

        //校验方法执行顺序
        inOrder.verify(student).setName("xiaoming");
        inOrder.verify(student).setAge(21);
        inOrder.verify(student1).setName("yaya");
        inOrder.verify(student1).setAge(18);
    }

    @Test
    public void testGroupInjectMocks() {
        Mockito.when(student.getName()).thenReturn("xiaoming");
        System.out.println(group.getStuName());
    }

    /**
     * 测试类
     */
    static class Group {
        Student student;

        public Group(Student student) {
            this.student = student;
        }

        public String getStuName() {
            return student.getName();
        }
    }


}
