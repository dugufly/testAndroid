package com.example.testandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MatrixView extends ImageView{


	
	int mIndex=0;
	int mImgW,mImgH;  
    private Bitmap bitmap;  
    private Matrix matrix;  
    public MatrixView(Context context)  
    {  
        super(context);  
        init();
    }  
    public MatrixView(Context context, AttributeSet attrs)
    {
    	 super(context,attrs);  
    	 init();
    	 
    }
    private void init()
    {
    	bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_03mp);  
        mImgW=bitmap.getWidth();
        mImgH=bitmap.getHeight();
        matrix = new Matrix(); 
    }
    @Override  
    protected void onDraw(Canvas canvas)  
    {  
        // 画出原图像  
    	canvas.drawBitmap(bitmap, 0, 0, null);  
    	// 画出变换后的图像  
        canvas.drawBitmap(bitmap, matrix, null);  
        super.onDraw(canvas);  
    }  
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
    {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) 
    {
    	super.onLayout(changed, left, top, right, bottom);
    	if(changed)
    	{
    	int width=Math.max(mIndex*100, 100);
    	layout(left,top,left+width,top+width);
    	}
    }
  
    @Override  
    public void setImageMatrix(Matrix matrix)  
    {  
        this.matrix.set(matrix);  
        super.setImageMatrix(matrix);  
    }  
      
  
    
    public Matrix getMat()
    {
    	int iOrientation=90;
 	   int scalX =  1;
        int scalY = 1;
        Matrix mat1=new Matrix();
        Matrix mat2=new Matrix();
        Matrix mat3=new Matrix();
        Matrix mat4=new Matrix();
        Matrix matrix=new Matrix();
    	matrix.setScale(scalX, scalY);
        matrix.postRotate(iOrientation);
        matrix.invert(mat1);
        matrix.postScale(mImgW / 2000f, mImgH / 2000f);
        matrix.invert(mat2);
        matrix.postTranslate(mImgW / 2f, mImgH / 2f);
        matrix.invert(mat3);
        
        mat4.postRotate(-iOrientation);
        mat4.postScale(2000f/mImgH,2000f/mImgW);
        mat4.postTranslate(-1000,-1000);
        return mat3;
    }
    public Matrix getMatrix(int index)
    {
    	  Matrix matrix = new Matrix();  
          // 输出图像的宽度和高度(162 x 251)  
          Log.e("TestTransformMatrixActivity", "image size: width x height = " +  mImgW + " x " + mImgH);  
          
          if(index==0)
          {
	          // 1. 平移  
	          matrix.postTranslate(mImgW, mImgH);  
	          // 在x方向平移view.getImageBitmap().getWidth()，在y轴方向view.getImageBitmap().getHeight()  
	          
          }
          else if(index==1) {
              // 2. 旋转(围绕图像的中心点)  
              matrix.setRotate(45f, mImgW / 2f, mImgH / 2f);  
                
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠  
              matrix.postTranslate(mImgW * 1.5f, 0f);  
             
          }
          else if(index==2) {
              
              // 3. 旋转(围绕坐标原点) + 平移(效果同2)  
              matrix.setRotate(45f);  
              matrix.preTranslate(-1f * mImgW / 2f, -1f * mImgH / 2f);  
              matrix.postTranslate((float)mImgW / 2f, (float)mImgH / 2f);  
                
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠  
              matrix.postTranslate((float)mImgW * 1.5f, 0f);  
 
          }
          else if(index==3) {
              // 4. 缩放  
              matrix.setScale(2f, 2f);  
    
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠  
              matrix.postTranslate(mImgW,mImgH);  
          }
          else if(index==4) {
              // 5. 错切 - 水平  
              matrix.setSkew(0.5f, 0f);  

                
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠           
              matrix.postTranslate(mImgW, 0f);  

          }
          else if(index==5) {
              // 6. 错切 - 垂直  
              matrix.setSkew(0f, 0.5f);  

                
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠               
              matrix.postTranslate(0f, mImgH);  
              
          }
          else if(index==6) {
             // 7. 错切 - 水平 + 垂直  
              matrix.setSkew(0.5f, 0.5f);  
                
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠               
              matrix.postTranslate(0f, mImgH);  

          }
          else if(index==7) {
              // 8. 对称 (水平对称)  
              float matrix_values[] = {1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};  
              matrix.setValues(matrix_values);  
   
                
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠   
              matrix.postTranslate(0f, mImgH * 2f);  
   
          }
          else if(index==8) {
              // 9. 对称 - 垂直  
              float matrix_values[] = {-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};  
              matrix.setValues(matrix_values);  
               
                
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠   
              matrix.postTranslate(mImgW * 2f, 0f);  
    
                
              
          }
          else if(index==9) {
              // 10. 对称(对称轴为直线y = x)  
              float matrix_values[] = {0f, -1f, 0f, -1f, 0f, 0f, 0f, 0f, 1f};  
              matrix.setValues(matrix_values);  
   
                
              // 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠               
              matrix.postTranslate(mImgH + mImgW,   
            		  mImgH + mImgW);  
              
          }
          
            
          setImageMatrix(matrix);  
          
          // 下面的代码是为了查看matrix中的元素  
          float[] matrixValues = new float[9];  
          matrix.getValues(matrixValues);  
          for(int i = 0; i < 3; ++i)  
          {  
              String temp = new String();  
              for(int j = 0; j < 3; ++j)  
              {  
                  temp += matrixValues[3 * i + j ] + "\t";  
              }  
              Log.e("TestTransformMatrixActivity", temp);  
          }  
            
            
            
          return matrix;

                   
 
    }
	    
	  @Override
	    public boolean onTouchEvent(MotionEvent e)  
	    {  
	        if(e.getAction() == MotionEvent.ACTION_UP)  
	        {  
//	        	Matrix mat=getMat();
//	        	setImageMatrix(mat); 
	        	
	        	
	        	
	        	getMatrix(mIndex);
	        	mIndex=(++mIndex)%10;
	            //invalidate();  
	            
	           // ViewGroup.LayoutParams params=getLayoutParams();
	           // params.width=Math.max(mIndex*100, 100);
	            requestLayout();
	        }  
	        return true;  
	    }  
	
}
