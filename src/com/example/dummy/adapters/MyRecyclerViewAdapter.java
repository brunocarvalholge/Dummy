package com.example.dummy.adapters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import com.example.dummy.R;
import com.exemplo.dummy.provider.CachedFileProvider;
import com.exemplo.dummy.views.CircleView;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyRecyclerViewAdapter extends
		Adapter<MyRecyclerViewAdapter.ViewHolder> {
	private String[] mDataset;
	private Context mContext;

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public CircleView mCircle;
		public TextView mText;
		public Button mButton;

		public ViewHolder(View v) {
			super(v);
			mButton = (Button) v.findViewById(R.id.button1);
			mText = (TextView) v.findViewById(R.id.text1);
			mCircle = (CircleView) v.findViewById(R.id.button);
		}
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public MyRecyclerViewAdapter(String[] myDataset, Context context) {
		mDataset = myDataset;
		mContext = context;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(
			ViewGroup parent, int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.buttons, parent, false);
		// set the view's size, margins, paddings and layout parameters
		// ...
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@SuppressLint("DefaultLocale")
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		switch (position % 4) {
		case 0:
			holder.mCircle.setColor(mContext.getResources().getColor(R.color.accent));
			break;
		case 1:
			holder.mCircle.setColor(mContext.getResources().getColor(R.color.bar));
			break;
		case 2:
			holder.mCircle.setColor(mContext.getResources().getColor(R.color.primary));
			break;
		default:
			holder.mCircle.setColor(mContext.getResources().getColor(R.color.primary_dark));
			break;
		}

		holder.mText.setText(mDataset[position]);
		holder.mCircle.setText(mDataset[position].substring(0, 1).toUpperCase());
		holder.mButton.setText(String.valueOf(position));
		holder.mButton.setId(position);
		final int button = position;
		holder.mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				PackageManager pm = mContext.getPackageManager();
				ComponentName cName;
				int state;
				switch (button) {
				case 0:
					if (pm == null) {
						Toast.makeText(mContext, "pm == null",
								Toast.LENGTH_LONG).show();
						return;
					}

					cName = new ComponentName("com.example.dummy",
							"com.example.dummy.MainActivity");

					state = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;

					try {
						pm.setComponentEnabledSetting(cName, state,
								PackageManager.DONT_KILL_APP);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

					Toast.makeText(mContext, "App launcher removed",
							Toast.LENGTH_LONG).show();

					cName = new ComponentName("com.example.dummy",
							"com.example.dummy.MainActivity");

					state = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;

					try {
						pm.setComponentEnabledSetting(cName, state,
								PackageManager.DONT_KILL_APP);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

					Toast.makeText(mContext, "App launcher added",
							Toast.LENGTH_LONG).show();
					break;
				case 2:
					final Intent emailIntent = new Intent(
							android.content.Intent.ACTION_SEND);

					try {
						createCachedFile(mContext, "teste.gsp",
								"This is a test");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						throw new RuntimeException(e);
					}

					// Explicitly only use Gmail to send
					emailIntent.setClassName("com.google.android.gm",
							"com.google.android.gm.ComposeActivityGmail");

					emailIntent.setType("plain/text");

					// Add the recipients
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
							new String[] { "bhhc@cin.ufpe.br" });

					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							"teste");

					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							"teste");

					// Add the attachment by specifying a reference to our
					// custom
					// ContentProvider
					// and the specific file of interest
					emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					emailIntent.putExtra(
							Intent.EXTRA_STREAM,
							Uri.parse("content://"
									+ CachedFileProvider.AUTHORITY + "/"
									+ "teste.gsp"));
					mContext.startActivity(emailIntent);
					break;

				default:
					break;
				}
			}
		});
	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.length;
	}

	public static void createCachedFile(Context context, String fileName,
			String content) throws IOException {

		File cacheFile = new File(context.getFilesDir(), fileName);
		if (!cacheFile.exists()) {
			cacheFile.mkdir();
		}
		Log.d("TAG", cacheFile.getAbsolutePath());
		cacheFile.createNewFile();

		FileOutputStream fos = new FileOutputStream(cacheFile);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
		PrintWriter pw = new PrintWriter(osw);

		pw.println(content);

		pw.flush();
		pw.close();
	}

}
