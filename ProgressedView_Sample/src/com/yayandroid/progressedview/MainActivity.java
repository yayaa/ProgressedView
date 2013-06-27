package com.yayandroid.progressedview;

import com.yayandroid.progressedview.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	String[] strings = new String[] { "Properties", "Layout Properties", "Seek Update", "Custom Seek", "Custom Progress Layout" };
	String[] classes = new String[] { "PropertiesActivity", "LayoutPropertiesActivity", "SeekUpdateActivity", "CustomSeekActivity", "CustomLayoutActivity"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings));
		lv.setOnItemClickListener(this);
	} 

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		try {
			Intent push = new Intent(this, Class.forName(getPackageName() + "." + classes[position]));
			startActivity(push);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
