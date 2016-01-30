package com.majorassets.betterhalf;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.TestDataItemList;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class DataListActivityFragment extends Fragment {

	private RecyclerView mRecyclerView;
	private DataItemAdapter mAdapter;

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

		mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		updateUI();

		return view;
	}
}
