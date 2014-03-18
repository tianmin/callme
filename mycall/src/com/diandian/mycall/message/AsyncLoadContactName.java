package com.diandian.mycall.message;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Handler;
import android.os.Message;

import com.diandian.mycall.common.TApplication;
import com.diandian.mycall.util.L;

/**
 * @author lenovo
 */
public class AsyncLoadContactName {

	public static String tag = "ChatContentAdapter";

	private Handler handler;

	private HashMap<String, SoftReference<String>> caches;

	public boolean isLoop;

	private ArrayList<LoadContactNameTask> tasks;

	private Thread workThread;

	public AsyncLoadContactName(final Callback back) {
		// 控制器
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				LoadContactNameTask task = (LoadContactNameTask) msg.obj;
				back.loadContactName(task.number, task.contactName);
				L.i(tag, "code runing!");
			}
		};

		// 任务
		tasks = new ArrayList<LoadContactNameTask>();

		// 缓存
		caches = new HashMap<String, SoftReference<String>>();

		// 循环控制线程
		isLoop = true;

		// workThread
		this.workThread = new Thread() {

			@Override
			public void run() {

				while (isLoop) {

					while (!tasks.isEmpty()) {

						LoadContactNameTask task = tasks.remove(0);
						String contactName = TApplication.getInstance().msgOperator
								.getContactNameByPhoneNumber(TApplication
										.getInstance().getApplicationContext(),
										task.number);

						if (contactName == null) {
							task.contactName = task.number;
						} else {
							task.contactName = contactName;
						}

						Message msg = Message.obtain();

						msg.obj = task;

						handler.sendMessage(msg);

						// 将数据存储到缓存当中
						caches.put(task.number, new SoftReference<String>(
								task.contactName));
					}
				}
				synchronized (this) {
					try {
						L.i(tag, "睡啦!");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		this.workThread.start();
	}

	/**
	 * 加载联系人姓名
	 * 
	 * @param number
	 * @return
	 */
	public String loadContactName(String number) {

		if (caches.containsKey(number)) {
			String contactName = caches.get(number).get();
			if (contactName != null) {
				return contactName;
			} else {
				caches.remove(number);
			}
		}

		LoadContactNameTask task = new LoadContactNameTask();
		task.number = number;
		if (!this.tasks.contains(task)) {
			tasks.add(task);
			L.i(tag, "我要敲醒你啦!");

			synchronized (this) {

				this.notify();

			}
		}

		return null;
	}

	/**
	 * 加载短信名字的任务
	 * 
	 * @author lenovo
	 */
	class LoadContactNameTask {

		public String number;

		public String contactName;

		@Override
		public boolean equals(Object o) {

			LoadContactNameTask task = (LoadContactNameTask) o;

			return this.number.equals(task.number);
		}
	}
}