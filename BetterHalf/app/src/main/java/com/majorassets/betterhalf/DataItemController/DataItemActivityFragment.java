package com.majorassets.betterhalf.DataItemController;


import android.content.Intent;
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
	private TextView mTestTextView;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view =  inflater.inflate(R.layout.fragment_data_list, container, false);

		//setting the tool bar to primary color
		Window window = getActivity().getWindow();
		window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));


		Bundle args = getArguments();

		mTestTextView = (TextView) view.findViewById(android.R.id.text1);
		String text = Integer.toString(args.getInt(DataItemActivityFragment.ARG_PAGE));
		mTestTextView.setText(text);

		return view;
	}
}
