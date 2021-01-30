package com.del.dnews.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public abstract void initViews();
	public abstract void initLogic();
	public abstract void initListeners();
}