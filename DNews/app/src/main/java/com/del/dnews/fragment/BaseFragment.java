package com.del.dnews.fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;

public abstract class BaseFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public abstract void findViews(View v);
	public abstract void initViews(View v);
	public abstract void initListeners(View v);
}