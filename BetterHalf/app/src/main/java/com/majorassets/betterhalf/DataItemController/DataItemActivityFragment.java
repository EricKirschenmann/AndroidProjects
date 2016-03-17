package com.majorassets.betterhalf.DataItemController;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Testing.TestDataItemList;
import com.majorassets.betterhalf.R;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class DataItemActivityFragment extends Fragment
{
	private RecyclerView mRecyclerView;
	private TextView mTestTextView;
	private DataItemAdapter mAdapter;

	public static final String ARG_PAGE = "com.majorassets.betterhalf.page";

	//create a new instance of the fragment identifying it by an int argument
	public static DataItemActivityFragment newInstance(int page)
	{
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		DataItemActivityFragment fragment = new DataItemActivityFragment();
		fragment.setArguments(args);
		return fragment;
	}

	private class DataItemHolder extends RecyclerView.ViewHolder
	{
		public TextView mLabelTextView;
		public TextView mValueTextView;

		public DataItemHolder(View itemView){
			super(itemView);

			mLabelTextView = (TextView) itemView.findViewById(R.id.text_label);
			mValueTextView = (TextView) itemView.findViewById(R.id.text_value);
		}
	}

	private class DataItemAdapter extends RecyclerView.Adapter<DataItemHolder>
	{
		public List<BaseDataItem> mItemList;

		public DataItemAdapter(List<BaseDataItem> items){
			mItemList = items;
		}

		public DataItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
		{
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			View view = layoutInflater.inflate(R.layout.list_item_display, parent, false);
			return new DataItemHolder(view);
		}

		public void onBindViewHolder(DataItemHolder holder, int pos)
		{
			BaseDataItem item = mItemList.get(pos);
			holder.mLabelTextView.setText(item.getLabel());
			holder.mValueTextView.setText(item.getValue());
		}

		public int getItemCount() {
			return mItemList.size();
		}
	}

	public void updateUI()
	{
		TestDataItemList itemListObject = TestDataItemList.get(getActivity());
		List<BaseDataItem> itemList = itemListObject.getItemList();

		mAdapter = new DataItemAdapter(itemList);
		mRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_data_list, container, false);

		Window window = getActivity().getWindow();
		window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

		//mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
		//mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		//updateUI();

		Bundle args = getArguments();

		mTestTextView = (TextView) view.findViewById(android.R.id.text1);
		String text = Integer.toString(args.getInt(DataItemActivityFragment.ARG_PAGE));
		mTestTextView.setText(text);

		return view;
	}
}
