package com.example.panzq.xutilsapp.db.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by panzq on 2017/6/12.
 */

/**
 通过上方的实体bean,我们需要知道一个表对应的实体bean需要注意以下几点:
 1.在类名上面加入@Table标签，标签里面的属性name的值就是以后生成的数据库的表的名字
 2.实体bean里面的属性需要加上@Column标签，这样这个标签的name属性的值会对应数据库里面的表的字段。
 3.实体bean里面的普通属性，如果没有加上@Column标签就不会在生成表的时候在表里面加入字段。
 4.实体bean中必须有一个主键，如果没有主键，表以后不会创建成功，
 @Column(name=”id”,isId=true,autoGen=true)这个属性：
                    name的值代表的是表的主键的标识，
                    isId这个属性代表的是该属性是不是表的主键，
                    autoGen代表的是主键是否是自增长，如果不写autoGen这个属性，默认是自增长的属性。
 */
@Table(name = "person2")
public class Employee {
    @Column(name="id",isId = true,autoGen = true)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private int age;

    @Column(name="sex")
    private String sex;

    @Column(name="salary")
    private double salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }

    public Employee(String name, int age, String sex, double salary) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
    }

    public Employee(int id, String name, int age, String sex, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
    }

    public Employee() {
    }
}
