package com.example.panzq.xutilsapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.panzq.xutilsapp.db.bean.Employee;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.List;

@ContentView(R.layout.activity_activity_db)
public class ActivityDB extends AppCompatActivity {

    static DbManager.DaoConfig daoConfig;
    private DbManager dbManager ;
    @ViewInject(R.id.btn_insert)
    private Button btn_insert;
    @ViewInject(R.id.btn_query)
    private Button btn_query;
    @ViewInject(R.id.btn_update)
    private Button btn_update;
    @ViewInject(R.id.btn_delete)
    private Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_db);
        x.view().inject(this);
        daoConfig = getDaoConfig();
        dbManager = x.getDb(daoConfig);
    }

    public static DbManager.DaoConfig getDaoConfig(){
        File file=new File(Environment.getExternalStorageDirectory().getPath());
        if(daoConfig==null){
            daoConfig=new DbManager.DaoConfig()
                    .setDbName("employee1.db")
                    .setDbDir(file)
                    .setDbVersion(1)
                    .setAllowTransaction(true)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }

    @Event(value = {R.id.btn_insert,R.id.btn_query,R.id.btn_update,R.id.btn_delete})
    private void dbOperate(View v)
    {
        switch(v.getId())
        {
            case R.id.btn_insert:
                Log.d("panzq","插入数据");
                insertDate(0,"李四",34,"女",5000);
                insertDate(0,"王二丫",32,"女",3000);
                insertDate(0,"朱六",30,"男",3500);
                break;
            case R.id.btn_query:
                Log.d("panzq","查询数据");
                //findDateById(2);
                //findFirstData();
                findAllDatas();

                /*  WhereBuilder builder_find = WhereBuilder.b();
                    builder_find.and("salary","<=",4000);
                    builder_find.and("age","<=",30);
                    findDatasByWhere(builder_find);*/
                break;
            case R.id.btn_update:
                Log.d("panzq","修改数据");
                //updateById(3,"刘能",27,"男",3600);
                WhereBuilder builder = WhereBuilder.b();
                builder.and("age","<=","30");
                builder.and("salary","<","4000");
                updateByWhere(4,null,0,null,3800,builder);
                break;
            case R.id.btn_delete:
                Log.d("panzq","删除数据");
                //deleteById(2);
               /* WhereBuilder builder_del = WhereBuilder.b();
                builder_del.and("salary","<=",4000);
                builder_del.and("age","<=",30);
                deleteDataByWhere(builder_del);*/
                //deleteAll();
                dropTable();
                break;
        }
    }

    private void insertDate(int id ,String name,int age,String sex,double salary) {
        try {
            Employee employee = null;
            if (id != 0)
                employee = new Employee(id,name,age,sex,salary);
            else
                employee = new Employee(name,age,sex,salary);
            dbManager.save(employee);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    public Employee findDateById(int id)
    {
        Employee employee = null;
        try {
            employee =  dbManager.findById(Employee.class,id);
            Log.e("panzq","findDateById id =  "+id+":"+employee.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public Employee findFirstData()
    {
        Employee employee = null;
        try {
            employee =  dbManager.findFirst(Employee.class);
            Log.e("panzq","findFirstData id = "+(employee.getId())+":"+employee.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
        return employee;
    }
    public List<Employee> findAllDatas()
    {
        List<Employee> allData = null;
        try {
            allData = dbManager.findAll(Employee.class);
            if (allData !=null && allData.size()>0)
                Log.e("panzq"," findAllDatas size = "+allData.size()+" : "+allData.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
        return allData;
    }

    public List<Employee> findDatasByWhere(WhereBuilder builder)
    {
        List<Employee> datas = null;
        try {
            datas = dbManager.selector(Employee.class)
                    .where(builder)
                    .findAll();
            Log.e("panzq"," findDatasByWhere size = "+datas.size()+" : "+datas.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public void updateById(int id,String name,int age,String sex,double salary)
    {
        try {
            Employee employee = dbManager.findById(Employee.class , id);
            if (age != 0)
                employee.setAge(age);
            if (!TextUtils.isEmpty(name))
                employee.setName(name);
            if (salary!=0)
                employee.setSalary(salary);
            if (!TextUtils.isEmpty(sex))
                employee.setSex(sex);
            dbManager.saveOrUpdate(employee);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateByWhere(int id,String name,int age,String sex,double salary,WhereBuilder builder)
    {
        try {
            List<Employee> employees = dbManager.selector(Employee.class).where(builder).findAll();
            for (Employee e:employees) {
                KeyValue kname = new KeyValue("name",e.getName());
                KeyValue kage = new KeyValue("age",e.getAge());
                KeyValue ksex = new KeyValue("sex",e.getSex());
                KeyValue ksalary = new KeyValue("age",e.getSalary());
                if (!TextUtils.isEmpty(name))
                    kname =new KeyValue("name",name);
                if(age != 0)
                    kage = new KeyValue("age",age);
                if (!TextUtils.isEmpty(sex))
                    ksex = new KeyValue("sex",sex);
                if (salary!=0)
                    ksalary = new KeyValue("salary",salary);
                dbManager.update(Employee.class,builder,kage,kname,ksex,ksalary);

            }
        } catch (DbException e1) {
            e1.printStackTrace();
        }

    }

    public void deleteById(int id)
    {
        try {
            dbManager.deleteById(Employee.class,id);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    public void deleteDataByWhere(WhereBuilder builder)
    {
        try {
            List<Employee> employees = dbManager.selector(Employee.class)
                    .where(builder).findAll();
            dbManager.delete(employees);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    public void dropTable()
    {
        try {
            dbManager.dropTable(Employee.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    public void deleteAll()
    {
        try {
            dbManager.delete(Employee.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
