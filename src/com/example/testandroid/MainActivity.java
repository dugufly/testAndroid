package com.example.testandroid;

import java.nio.ByteBuffer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

	RelativeLayout rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView=(RelativeLayout)LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(rootView);
        
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver,filter);
//        native_Init();
        //testInflact();
    }


    
   void testInflact()
   {
	   LayoutInflater factory = LayoutInflater.from(this);
        //factory.inflate(R.layout.test1, root);
        
       factory.inflate(R.layout.linerar, rootView);
        
//        View v1 = factory.inflate(R.layout.test1, null);  
//        View v3 = factory.inflate(R.layout.test1, ll, false);  
//                  
//        View v2 = factory.inflate(R.layout.test1, ll); 
//        
//        View v4 = factory.inflate(R.layout.test1, ll, true);  
//        ll.addView(v1);
//        ll.removeView(v4);
//        v1.setBackgroundColor(Color.CYAN);
       // ll.addView(v2);
       // ll.addView(v3);
       // ll.addView(v4);
       // rootView.addView(ll);
        
   }
   

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
          @Override
           public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                 if (action.equals(Intent.ACTION_TIME_TICK)) {
 
                    //do what you want to do ...13      
                	 Log.d("", "receive ACTION_TIME_TICK");
            }
      }
    };
    
//    static private native void native_Init();
//    static private native void native_Uninit();
//    static{
//    	System.loadLibrary("MyCapture");
//    
//    }
}
