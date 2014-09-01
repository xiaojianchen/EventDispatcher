package com.example.detailanimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ActionBarActivity {

	ListView listView1;

	MyScrollView sv;

	private List<Map<String, Object>> list = null;

	private boolean isTop = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.t1);

		listView1 = (ListView) findViewById(R.id.lv);

		sv = (MyScrollView) findViewById(R.id.sv);

		list = initList();

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.simpleitem, new String[] { "id", "name", "age" },
				new int[] { R.id.id, R.id.name, R.id.age });
		System.out.println("all height "
				+ DeviceUtil.getScreenRect(this).bottom + " actionBar "
				+ (int) DeviceUtil.getActionBarHeight(this));
		
		listView1.getLayoutParams().height = DeviceUtil.getScreenRect(this).bottom
				- DeviceUtil.getStatusBarHeight(this)
				- (int) DeviceUtil.getActionBarHeight(this)
				- DeviceUtil.convertDipToPx(this, 30);

		listView1.setAdapter(adapter);

		sv.smoothScrollTo(0, 0);
		sv.setMaxScrollOffset(DeviceUtil.convertDipToPx(this, 200));

		listView1.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (firstVisibleItem == 0 && listView1.getChildAt(0) != null
						&& listView1.getChildAt(0).getTop() >= 0) {
					sv.setReachTop(true);
				} else {
					sv.setReachTop(false);
				}

			}
		});
		
		/**---------------------------------方案二-----------------------------------------*/
		
		

	}

	private List<Map<String, Object>> initList() {
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (int i = 0; i < 200; i++) {
			map = new HashMap<String, Object>();
			map.put("id", i);
			map.put("name", "张三" + i + "号");
			map.put("age", 28);
			map.put("email", "zhangsan@163.com");
			temp.add(map);
		}
		return temp;
	}

}
