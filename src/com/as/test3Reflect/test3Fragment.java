package com.as.test3Reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import com.as.FragmentBase.MyFragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class test3Fragment extends MyFragment implements OnTouchListener{

	private int mIndex=0;
	private TextView mTextView=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Context context =container.getContext();
		mTextView=new TextView(context);
		mTextView.setBackgroundColor(Color.BLACK);
		mTextView.setOnTouchListener(this);
		return  mTextView;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Test3 Reflect";
	}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction()==MotionEvent.ACTION_UP) {
        	String log=null;
        	mIndex=(mIndex+1)%8;
        	if(mIndex==0)
        	{
        		log=Reflect.classForName();
        	}
        	else if(mIndex==1){
        		log=Reflect.showDeclaredMethods();
			}
        	else if(mIndex==2){
        		log=Reflect.showMethods();
			}
			else if(mIndex==3){
				log=Reflect.showDeclaredFields();
			}
			else if(mIndex==4){
				log=Reflect.showFields();
			}
			else if(mIndex==5){
				log=Reflect.showSuperMethods();
			}
			else if(mIndex==6){
				log=Reflect.showInterfaces();
			}
			
        	mTextView.setText("Index="+mIndex+"\n"+log);
        	
        }


        return true;
    }

}

class Reflect
{	
	//0
	public static String classForName() {
		StringBuilder sb=new StringBuilder();
		sb.append("classForName<--\n");
        try {
            // 获取 Class 对象
            Class<?> clz = Class.forName("com.as.test3Reflect.Student");
            // 通过 Class 对象获取 Constructor，Student 的构造函数有一个字符串参数
            // 因此这里需要传递参数的类型 ( Student 类见后面的代码 )
            Constructor<?> constructor = clz.getConstructor(String.class);
            // 通过 Constructor 来创建 Student 对象
            Object obj = constructor.newInstance("mr.simple");
            sb.append("\n obj :  " + obj.toString());
            
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
            	sb.append("\ndeclared method name : " + method.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("classForName-->\n");
        return sb.toString();
    }
	//1
	public static String showDeclaredMethods() {
		StringBuilder sb=new StringBuilder();
		sb.append("\nshowDeclaredMethods<--");
        Student student = new Student("mr.simple");
        
        try {
            Class<?> clz = Class.forName("com.as.test3Reflect.Student");
            // 通过 Class 对象获取 Constructor，Student 的构造函数有一个字符串参数
            // 因此这里需要传递参数的类型 ( Student 类见后面的代码 )
            Constructor<?> constructor = clz.getConstructor(String.class);
            // 通过 Constructor 来创建 Student 对象
            student = (Student)constructor.newInstance("mr.simple");
		} catch (Exception e) {
			// TODO: handle exception
		}

        
          Method[] methods = student.getClass().getDeclaredMethods();
          for (Method method : methods) {
              sb.append("\ndeclared method name : " + method.getName());
          }

          try {
              Method learnMethod = student.getClass().getDeclaredMethod("learn", String.class);
              // 获取方法的参数类型列表
              Class<?>[] paramClasses = learnMethod.getParameterTypes() ;
              for (Class<?> class1 : paramClasses) {
            	  sb.append("learn 方法的参数类型 : " + class1.getName());
              }
              // 是否是 private 函数，属性是否是 private 也可以使用这种方式判断
              sb.append(learnMethod.getName() + " is private "
                      + Modifier.isPrivate(learnMethod.getModifiers()));
              learnMethod.setAccessible(true);//取消访问权限检查，否则无法访问私有方法
              learnMethod.invoke(student, "java ---> ");
          } catch (Exception e) {
              e.printStackTrace();
          }
          sb.append("\nshowDeclaredMethods-->");
          return sb.toString();
      }
    //2
	public static String showMethods() {
		StringBuilder sb=new StringBuilder();
		sb.append("\nshowMethods<--");
        Student student = new Student("mr.simple");
        // 获取所有方法
        Method[] methods = student.getClass().getMethods();
        for (Method method : methods) {
        	sb.append("\nmethod name : " + method.getName());
        }

        try {
            // 通过 getMethod 只能获取公有方法，如果获取私有方法则会抛出异常，比如这里就会抛异常
            Method learnMethod = student.getClass().getMethod("learn", String.class);
            // 是否是 private 函数，属性是否是 private 也可以使用这种方式判断
            sb.append(learnMethod.getName() + " is private " + Modifier.isPrivate(learnMethod.getModifiers()));
            // 调用 learn 函数
            learnMethod.invoke(student, "java");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("\nshowMethods-->");
        return sb.toString();
    }
    
	//3
	public static String showDeclaredFields() {
		StringBuilder sb=new StringBuilder();
		sb.append("\nshowDeclaredFields<--");
        Student student = new Student("mr.simple");
        // 获取当前类和父类的所有公有属性
        Field[] publicFields = student.getClass().getDeclaredFields();
        for (Field field : publicFields) {
        	sb.append("\ndeclared field name : " + field.getName());
        }

        try {
            // 获取当前类和父类的某个公有属性
            Field gradeField = student.getClass().getDeclaredField("mGrade");
            // 获取属性值
            sb.append(" my grade is : " + gradeField.getInt(student));
            // 设置属性值
            gradeField.set(student, 10);
            sb.append(" my grade is : " + gradeField.getInt(student));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("\nshowDeclaredFields-->");
        return sb.toString();
    }
    
	//4
	public static String showFields() {
		StringBuilder sb=new StringBuilder();
		sb.append("\nshowFields<--");
        Student student = new Student("mr.simple");
        // 获取当前类和父类的所有公有属性
        Field[] publicFields = student.getClass().getFields();
        for (Field field : publicFields) {
        	sb.append("\nfield name : " + field.getName());
        }

        try {
            // 获取当前类和父类的某个公有属性
            Field ageField = student.getClass().getField("mAge");
            sb.append("\n age is : " + ageField.getInt(student));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("\nshowFields-->");
        return sb.toString();
    }
    
	//5
	public static String showSuperMethods()
    {
		StringBuilder sb=new StringBuilder();
		sb.append("showSuperMethods<--");
    	Student student = new Student("mr.simple");
        Class<?> superClass = student.getClass().getSuperclass();
        while (superClass != null) {
        	sb.append("\n Student's super class is : " + superClass.getName());
            // 再获取父类的上一层父类，直到最后的 Object 类，Object 的父类为 null
            superClass = superClass.getSuperclass();
        }
        sb.append("\nshowSuperMethods-->");
        return sb.toString();
    }
    
	//6
	public static String showInterfaces() {
		StringBuilder sb=new StringBuilder();
		sb.append("\nshowInterfaces<--");
        Student student = new Student("mr.simple");
        Class<?>[] interfaceses = student.getClass().getInterfaces();
        for (Class<?> class1 : interfaceses) {
        	sb.append("\n Student's interface is : " + class1.getName());
        }
        sb.append("\nshowInterfaces-->");
        return sb.toString();
    }
}
