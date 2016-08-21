package com.example.testandroid;


import com.as.FragmentBase.MyFragment;
import com.as.test2MatrixView.test2Fragment;
import com.as.test3Reflect.test3Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

	private RelativeLayout rootView;
	private ListView mMainListView=null;
	private MyFragment[] aryFragments={
			new test2Fragment(),
			new test3Fragment()
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        rootView=(RelativeLayout)LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(rootView);
        

        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver,filter);
        addFragment(new ListFragment(), "MainFragment",false);
    }


    private void addFragment(Fragment fragment, String tag,boolean bAddToBackStack) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment, tag);
        if(bAddToBackStack)
        	transaction.addToBackStack(tag);
        transaction.commit();
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
    
    class ListFragment extends Fragment
    {
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    		String aryFragmentNames[]=new String[aryFragments.length];
    		for (int i=0;i<aryFragments.length;i++) {
    			aryFragmentNames[i]=aryFragments[i].getDescription();
			}
    		final String []aryFragmentNames2=aryFragmentNames;
    		
    		Context context=container.getContext();
    		ArrayAdapter<String> adapter =
    				new ArrayAdapter<String>(context, R.layout.mainlistitem, R.id.mainlistitemname, aryFragmentNames);
            mMainListView=new ListView(context) ;//(ListView)findViewById(R.id.mainlist);
            mMainListView.setAdapter(adapter);
            mMainListView.setOnItemClickListener(new OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> parent, View view,
    					int position, long id) {
    				// TODO Auto-generated method stub
    				addFragment(aryFragments[position], aryFragmentNames2[position], true);
    				
    			}
            	
    		});
            
    		return mMainListView;
    	}
    }
    
    
    
//    public interface IListItemName
//    {
//    	public String  getDescription();
//    }
//    static private native void native_Init();
//    static private native void native_Uninit();
//    static{
//    	System.loadLibrary("MyCapture");
//    
//    }
}
