package com.as.testReflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

public class myTextView extends TextView{

	
	
	 private static final String TAG = "TestButton";
	 private int mIndex=0;
	    public myTextView(Context context) {
	        this(context, null);
	    }

	    public myTextView(Context context, AttributeSet attrs) {
	        super(context, attrs);

	    }

	    public myTextView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	 
	    }

	    
	    
	    
	    
	    

	    @SuppressLint("ClickableViewAccessibility")
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {

	        if (event.getAction()==MotionEvent.ACTION_UP) {
	        	
	        	mIndex=(mIndex+1)%8;
	        	if(mIndex==0)
	        	{
	        		Reflect.classForName();
	        	}
	        	else if(mIndex==1){
					Reflect.showDeclaredMethods();
				}
	        	else if(mIndex==2){
	        		Reflect.showMethods();
				}
				else if(mIndex==3){
					Reflect.showDeclaredFields();
				}
				else if(mIndex==4){
					Reflect.showFields();
				}
				else if(mIndex==5){
					Reflect.showSuperMethods();
				}
				else if(mIndex==6){
					Reflect.showInterfaces();
				}
				
	
	        	
	        }


	        return true;
	    }

}

class Reflect
{
	static String TAG="Reflectttt";
	
	//0
	public static void classForName() {
		Log.d(TAG,"classForName<--");
        try {
            // 获取 Class 对象
            Class<?> clz = Class.forName("com.yxb.testReflect.Student");
            // 通过 Class 对象获取 Constructor，Student 的构造函数有一个字符串参数
            // 因此这里需要传递参数的类型 ( Student 类见后面的代码 )
            Constructor<?> constructor = clz.getConstructor(String.class);
            // 通过 Constructor 来创建 Student 对象
            Object obj = constructor.newInstance("mr.simple");
            Log.d(TAG," obj :  " + obj.toString());
            
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                Log.d(TAG,"declared method name : " + method.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG,"classForName-->");
    }
	//1
	public static void showDeclaredMethods() {
		Log.d(TAG,"showDeclaredMethods<--");
        Student student = new Student("mr.simple");
        
        try {
            Class<?> clz = Class.forName("com.yxb.testReflect.Student");
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
              Log.d(TAG,"declared method name : " + method.getName());
          }

          try {
              Method learnMethod = student.getClass().getDeclaredMethod("learn", String.class);
              // 获取方法的参数类型列表
              Class<?>[] paramClasses = learnMethod.getParameterTypes() ;
              for (Class<?> class1 : paramClasses) {
            	  Log.d(TAG,"learn 方法的参数类型 : " + class1.getName());
              }
              // 是否是 private 函数，属性是否是 private 也可以使用这种方式判断
              Log.d(TAG,learnMethod.getName() + " is private "
                      + Modifier.isPrivate(learnMethod.getModifiers()));
              learnMethod.setAccessible(true);//取消访问权限检查，否则无法访问私有方法
              learnMethod.invoke(student, "java ---> ");
          } catch (Exception e) {
              e.printStackTrace();
          }
          Log.d(TAG,"showDeclaredMethods-->");
      }
    //2
	public static void showMethods() {
		Log.d(TAG,"showMethods<--");
        Student student = new Student("mr.simple");
        // 获取所有方法
        Method[] methods = student.getClass().getMethods();
        for (Method method : methods) {
        	Log.d(TAG,"method name : " + method.getName());
        }

        try {
            // 通过 getMethod 只能获取公有方法，如果获取私有方法则会抛出异常，比如这里就会抛异常
            Method learnMethod = student.getClass().getMethod("learn", String.class);
            // 是否是 private 函数，属性是否是 private 也可以使用这种方式判断
            Log.d(TAG,learnMethod.getName() + " is private " + Modifier.isPrivate(learnMethod.getModifiers()));
            // 调用 learn 函数
            learnMethod.invoke(student, "java");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG,"showMethods-->");
    }
    
	//3
	public static void showDeclaredFields() {
		Log.d(TAG,"showDeclaredFields<--");
        Student student = new Student("mr.simple");
        // 获取当前类和父类的所有公有属性
        Field[] publicFields = student.getClass().getDeclaredFields();
        for (Field field : publicFields) {
        	Log.d(TAG,"declared field name : " + field.getName());
        }

        try {
            // 获取当前类和父类的某个公有属性
            Field gradeField = student.getClass().getDeclaredField("mGrade");
            // 获取属性值
            Log.d(TAG," my grade is : " + gradeField.getInt(student));
            // 设置属性值
            gradeField.set(student, 10);
            Log.d(TAG," my grade is : " + gradeField.getInt(student));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG,"showDeclaredFields-->");
    }
    
	//4
	public static void showFields() {
		Log.d(TAG,"showFields<--");
        Student student = new Student("mr.simple");
        // 获取当前类和父类的所有公有属性
        Field[] publicFields = student.getClass().getFields();
        for (Field field : publicFields) {
        	Log.d(TAG,"field name : " + field.getName());
        }

        try {
            // 获取当前类和父类的某个公有属性
            Field ageField = student.getClass().getField("mAge");
            Log.d(TAG," age is : " + ageField.getInt(student));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG,"showFields-->");
    }
    
	//5
	public static void showSuperMethods()
    {
		Log.d(TAG,"showSuperMethods<--");
    	Student student = new Student("mr.simple");
        Class<?> superClass = student.getClass().getSuperclass();
        while (superClass != null) {
        	Log.d(TAG,"Student's super class is : " + superClass.getName());
            // 再获取父类的上一层父类，直到最后的 Object 类，Object 的父类为 null
            superClass = superClass.getSuperclass();
        }
        Log.d(TAG,"showSuperMethods-->");
    }
    
	//6
	public static void showInterfaces() {
		Log.d(TAG,"showInterfaces<--");
        Student student = new Student("mr.simple");
        Class<?>[] interfaceses = student.getClass().getInterfaces();
        for (Class<?> class1 : interfaceses) {
        	Log.d(TAG,"Student's interface is : " + class1.getName());
        }
        Log.d(TAG,"showInterfaces-->");
    }
}
