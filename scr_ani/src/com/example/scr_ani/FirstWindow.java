package com.example.scr_ani;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class FirstWindow extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//FULL SCREEN 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_first_window);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_window, menu);
		return true;
	}
	
	public void nowaGra(View view){
		Intent intent = new Intent(this, MainActivity.class); 
		startActivity(intent);
	}
	
	public void opcje(View view){
	}
	
	public void wyjscie(View view){	
		 finish();
         System.exit(0);
	}
}