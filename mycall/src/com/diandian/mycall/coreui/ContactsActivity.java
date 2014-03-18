package com.diandian.mycall.coreui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.diandian.mycall.R;
import com.diandian.mycall.common.Constant;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.contacts.AddContactsActivity;
import com.diandian.mycall.contacts.ContactEntity;
import com.diandian.mycall.contacts.ContactSearchAdapter;
import com.diandian.mycall.contacts.GroupEntity;
import com.diandian.mycall.contacts.MyExAdapter;
import com.diandian.mycall.contacts.MyListView;
import com.diandian.mycall.search.SearchUtils;
import com.diandian.mycall.util.L;
import com.diandian.mycall.util.T;
import com.diandian.mycall.view.BootstrapButton;
import com.diandian.mycall.view.BootstrapEditText;
import com.diandian.mycall.view.MyDialog;

/**
 * 联系人的Activity
 * 
 * @author lenovo
 */
public class ContactsActivity extends BaseActivity implements OnClickListener {

	// 管理按钮
	BootstrapButton btn_title_left;

	// 添加按钮
	BootstrapButton btn_title_right;

	// MyListView
	private MyListView listView;

	// 添加的 ListView, PopuWindow
	private ListView lvPopupList;

	// 添加操作产生的PopWindow
	private PopupWindow addPopWindow;

	private int NUM_OF_VISIBLE_LIST_ROWS = 2;

	// 所有的组数据
	private List<GroupEntity> listGroups = new ArrayList<GroupEntity>();

	// 组对应的联系人 HashMap<Integer,List<ContactEntity>>
	// Integer 组的ID List<ContactEntity> 对应的联系人
	private HashMap<Integer, List<ContactEntity>> source = new HashMap<Integer, List<ContactEntity>>();

	// 承载联系人数据的Adapter
	MyExAdapter adapter;

	TApplication application = TApplication.getInstance();

	// Dialog // 需要更换模式 TODO
	public MyDialog serachDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_contacts);

		// 初始化界面 监听
		initViews();

		// 添加监听
		initListener();

		// 获取所有组数据的异步操作
		new AsyncTask<String, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(String... params) {

				// 查询系统中所有的组数据
				application.allGroupInfo = application.contactOperator
						.getAllGroupInfo();

				// 初始化数据
				initData();

				return true;
			}

			@Override
			protected void onPostExecute(Boolean result) {

				if (result) {

					adapter.updata(listGroups, source);

				}

			}

		}.execute("");

	}

	public void initViews() {

		View view = findViewById(R.id.contacts_header);

		TextView tv_title = (TextView) view.findViewById(R.id.title_bar_text);

		tv_title.setText(Constant.Contacts.TITLENAME);

		btn_title_left = (BootstrapButton) view
				.findViewById(R.id.title_bar_left);

		btn_title_right = (BootstrapButton) view
				.findViewById(R.id.title_bar_right);

		btn_title_left.setText(Constant.Contacts.CONTACT_LEFT);

		btn_title_right.setText(Constant.Contacts.CONTACT_RIGHT);

		listView = (MyListView) findViewById(R.id.contacts_listview);

		adapter = new MyExAdapter(ContactsActivity.this, null, null);

		listView.setAdapter(adapter);

		iniPopupWindow();
	}

	public void initListener() {

		btn_title_left.setOnClickListener(this);

		btn_title_right.setOnClickListener(this);

		// ListView Listener
		listView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				adapter.doClick(v, groupPosition, childPosition);

				return true;
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 搜索操作
		case R.id.title_bar_left:

			addSearchDialog();

			break;

		// 添加操作
		case R.id.title_bar_right:

			// 进行添加操作
			if (addPopWindow.isShowing()) {

				addPopWindow.dismiss();// 关闭
			} else {

				addPopWindow.showAsDropDown(btn_title_right, 12, 12);
			}
			break;
		}

	}

	/**
	 * 添加搜索的Dialog
	 */
	public void addSearchDialog() {

		LayoutInflater inflater = LayoutInflater.from(ContactsActivity.this);
		View view = inflater.inflate(R.layout.contact_search, null);
		serachDialog = new MyDialog(ContactsActivity.this, 0, 0, view,
				R.style.dialog);
		Window window = serachDialog.getWindow();

		window.setLayout(
				(int) getResources().getDimension(R.dimen.search_dialog_width),
				(int) getResources().getDimension(R.dimen.search_dialog_height));
		serachDialog.show();

		ExpandableListView searchContent = (ExpandableListView) view
				.findViewById(R.id.search_content);

		// final SearchContentAdapter searchAdapter = new SearchContentAdapter(
		// ContactsActivity.this, null, R.layout.contact_search_item);

		final ContactSearchAdapter searchAdapter = new ContactSearchAdapter(
				ContactsActivity.this, null, R.layout.contact_search_item);

		searchContent.setAdapter(searchAdapter);

		final BootstrapEditText etSearch = (BootstrapEditText) view
				.findViewById(R.id.et_search);

		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// 得到用户数据的数据
				String str = etSearch.getText().toString();

				List<ContactEntity> searchResult = null;
				if (application.allContacts == null) {

					searchResult = SearchUtils.search(str,
							application.contactOperator.getAllContacts());

				} else {

					searchResult = SearchUtils.search(str,
							application.allContacts);

				}

				searchAdapter.onChange(searchResult);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				L.i("TIANMIN", "beforeTextChanged");
			}

			@Override
			public void afterTextChanged(Editable s) {

				L.i("text", "afterTextChanged");

			}
		});

		BootstrapButton btnSearch = (BootstrapButton) view
				.findViewById(R.id.search_btn);

		BootstrapButton btnCancel = (BootstrapButton) view
				.findViewById(R.id.search_cancel);

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				serachDialog.dismiss();
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				serachDialog.dismiss();

			}
		});

	}

	/**
	 * 添加联系人
	 */
	public void addContact() {
		Intent intent = new Intent(ContactsActivity.this,
				AddContactsActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 添加组的Dialog
	 */
	public void addGroupDialog() {
		LayoutInflater inflater = LayoutInflater.from(ContactsActivity.this);
		View view = inflater.inflate(R.layout.add_contacts_group, null);

		final MyDialog dialog = new MyDialog(ContactsActivity.this, 0, 0, view,
				R.style.dialog);

		Window window = dialog.getWindow();

		window.setLayout((int) getResources()
				.getDimension(R.dimen.dialog_width), (int) getResources()
				.getDimension(R.dimen.dialog_height));

		dialog.show();

		final EditText edGroupName = (EditText) view
				.findViewById(R.id.et_group_name);
		BootstrapButton butOk = (BootstrapButton) view
				.findViewById(R.id.group_ok);
		butOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String groupName = edGroupName.getText().toString().trim();

				if (groupName.equals("")) {

					T.showShort(ContactsActivity.this, "组名不为空!");

				} else {

					boolean flag = false;
					for (GroupEntity entity : application.allGroupInfo) {

						if (entity.getGroupName().trim().equals(groupName)) {
							flag = true;
							T.showShort(ContactsActivity.this, "改组已经存在啦哈!");

						}
					}

					if (!flag) {
						// 新建组
						application.contactOperator.addGroup(groupName);
						dialog.dismiss();

					}
				}
			}
		});

		BootstrapButton butCancel = (BootstrapButton) view
				.findViewById(R.id.group_cancel);

		butCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 初始化所欲的组数据
	 */
	public void initData() {
		// step1:初始化所有的联系人组
		GroupEntity allContactsGroup = new GroupEntity();

		allContactsGroup.setGroupId(-1);

		allContactsGroup.setGroupName("全部联系人");

		listGroups.add(allContactsGroup);

	}

	/**
	 * init PopupWindow
	 */
	private void iniPopupWindow() {

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(R.layout.contacts_add_popuwindow, null);

		lvPopupList = (ListView) layout.findViewById(R.id.lv_popup_list);

		addPopWindow = new PopupWindow(layout);

		addPopWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件

		String[] moreList = new String[] { "添加组", "添加联系人" };

		lvPopupList.setAdapter(new ArrayAdapter<String>(ContactsActivity.this,
				R.layout.contacts_add_item, R.id.tv_list_item, moreList));

		lvPopupList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					// 添加组
					addPopWindow.dismiss();

					addGroupDialog();

					break;

				case 1:
					// 添加联系人
					addPopWindow.dismiss();

					addContact();

					break;

				}
			}
		});

		// 控制popupwindow的宽度和高度自适应
		lvPopupList.measure(View.MeasureSpec.UNSPECIFIED,
				View.MeasureSpec.UNSPECIFIED);
		addPopWindow.setWidth(lvPopupList.getMeasuredWidth());
		addPopWindow.setHeight((lvPopupList.getMeasuredHeight() + 28)
				* NUM_OF_VISIBLE_LIST_ROWS);

		// 控制popupwindow点击屏幕其他地方消失
		addPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.bg_popupwindow));// 设置背景图片，不能在布局中设置，要通过代码来设置

		addPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
