package com.l.contants;

import java.util.ArrayList;
import java.util.HashMap;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView lvList;
	String pNum="";
	String pName="";
	private ArrayList<HashMap<String,String>> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		lvList = (ListView) findViewById(R.id.lv_list);
		contactList = readContact();
		lvList.setAdapter(new Myadapter());
		
	}

	/**
	 * 读取联系人的方法
	 * @return  保存有联系人信息的list
	 */
	private ArrayList<HashMap<String, String>> readContact() {
		// 从raw_contact表中读取联系人id，然后从data表中读出名称和电话号,同时读取menitype_id。
		// 跟据mimetype_id，确定类型。（是联系人还是电话号码）
		ArrayList<HashMap<String, String>>  cantactList =new ArrayList<HashMap<String,String>>();
		
		Uri rawContactsUri = Uri
				.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");

		Cursor rawContactCursor = getContentResolver().query(rawContactsUri,new String[] { "contact_id" }, null, null, null);
			
		if (rawContactCursor != null) {
			
			
			
			while (rawContactCursor.moveToNext()) {
				HashMap<String, String> contact=new HashMap<String, String>();
				String contactId = rawContactCursor.getString(0);
				
				Cursor dataCursor = getContentResolver().query(dataUri,new String[] { "data1", "mimetype" }, "contact_id=?",
																								new String[] { contactId }, null);
				
				while(dataCursor.moveToNext()){
					
					String data=dataCursor.getString(0);
					String type=dataCursor.getString(1);
					
					if(type.equals("vnd.android.cursor.item/phone_v2")){
						contact.put("phone", data);
					}else if(type.equals("vnd.android.cursor.item/name")){
						contact.put("name", data);
					}
				
				}
				
				cantactList.add(contact);
				dataCursor.close();	
			}
	
			rawContactCursor.close();
		}
		
		return cantactList;
	}

	
	
	public class Myadapter extends BaseAdapter {

		View view = null;

		@Override
		public int getCount() {

			return contactList.size();
		}

		@Override
		public Object getItem(int position) {

			return position;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		int x = 0;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				view = View.inflate(MainActivity.this, R.layout.contact_item, null);
				x = x + 1;
			} else {
				view = convertView;
			}
			TextView tv = (TextView) view.findViewById(R.id.t);
			tv.setText(contactList.get(position).get("name")+":"+contactList.get(position).get("phone"));

			return view;
		}

	}

}
