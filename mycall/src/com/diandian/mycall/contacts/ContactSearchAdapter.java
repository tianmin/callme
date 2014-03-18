package com.diandian.mycall.contacts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.diandian.mycall.R;
import com.diandian.mycall.common.CommonOperator;
import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.coreui.ContactsActivity;
import com.diandian.mycall.view.BootstrapButton;

public class ContactSearchAdapter extends BaseExpandableListAdapter {

	private ContactsActivity context;

	private List<ContactEntity> sources;

	private int resId;

	private LayoutInflater inflater;

	public ContactSearchAdapter(ContactsActivity context,
			List<ContactEntity> contacts, int resId) {

		this.context = context;

		inflater = LayoutInflater.from(this.context);

		if (contacts == null) {

			sources = new ArrayList<ContactEntity>();

		} else {

			this.sources = contacts;

		}

		this.resId = resId;
	}

	public void onChange(List<ContactEntity> listSources) {

		this.sources = listSources;

		this.notifyDataSetChanged();

	}

	@Override
	public int getGroupCount() {

		return this.sources.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {

		return this.sources.get(groupPosition);

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return this.sources.get(groupPosition).hashCode();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;

	}

	@Override
	public boolean hasStableIds() {

		return true;

	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			convertView = inflater.inflate(resId, null);

			holder = new ViewHolder();

			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_search_name);

			holder.tv_number = (TextView) convertView
					.findViewById(R.id.tv_search_number);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		ContactEntity contact = this.sources.get(groupPosition);

		if (contact.getContactName() != null) {

			holder.tv_name.setText(contact.getContactName());
		}

		if (contact.getTelNumber() != null) {

			holder.tv_number.setText(contact.getTelNumber());

		}

		return convertView;

	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.contact_search_child_item,
					null);
		}

		BootstrapButton btnCall = (BootstrapButton) convertView
				.findViewById(R.id.search_call_btn);

		final ContactEntity entity = ContactSearchAdapter.this.sources
				.get(groupPosition);

		// call
		btnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				CommonOperator.makeCall(ContactSearchAdapter.this.context,
						entity.getTelNumber());

				ContactSearchAdapter.this.context.serachDialog.dismiss();

			}
		});

		BootstrapButton btnSms = (BootstrapButton) convertView
				.findViewById(R.id.search_sms_btn);

		btnSms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int thread_id = TApplication.getInstance().msgOperator
						.getThreadIdbyPhoneNumber(entity.getTelNumber());

				String name = "";

				if (entity.getContactName() != null) {

					name = entity.getContactName();
				}

				CommonOperator.sendSms(context, thread_id, name,
						entity.getTelNumber());

				ContactSearchAdapter.this.context.serachDialog.dismiss();

			}
		});

		BootstrapButton btnManager = (BootstrapButton) convertView
				.findViewById(R.id.search_manager_btn);

		btnManager.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ContactSearchAdapter.this.context.serachDialog.dismiss();

			}
		});

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;

	}

	class ViewHolder {

		public TextView tv_name;

		public TextView tv_number;

	}

}
