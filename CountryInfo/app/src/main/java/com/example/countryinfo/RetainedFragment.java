package com.example.countryinfo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class RetainedFragment extends Fragment {
    static final String TAG = "RetainedFragment";
    private OnFragmentInteractionListener mListener;

    public RetainedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

//    void onButtonPressed() {
//        new HttpGetTask(this).execute();
//    }

    private void onDownloadFinished(String result) {
        if (null != mListener) {
            mListener.onDownloadfinished(result);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onDownloadfinished(String result);
    }
}
