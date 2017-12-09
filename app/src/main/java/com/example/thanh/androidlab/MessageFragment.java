package com.example.thanh.androidlab;


import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.Fragment;



public class MessageFragment extends Fragment {

    private View view;
    private TextView tvMessage;
    private TextView tvID;
    private Button btnDelete;
    private ChatDatabaseHelper chatDatabase;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvMessage = view.findViewById(R.id.tvMessage);
        tvID = view.findViewById(R.id.tvID);
        btnDelete = view.findViewById(R.id.btnDelete);

        tvMessage.setText(getArguments().getString("Message"));
        tvID.setText(getArguments().getString("MessageID"));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments().getBoolean("isTablet")) {
                    chatDatabase = new ChatDatabaseHelper(getActivity());
                    chatDatabase.deleteItem(getArguments().getString("MessageID"));
                    ((ChatWindow)getActivity()).notifyChange();
                    ((ChatWindow)getActivity()).displaySQL();
                    getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("MessageID", getArguments().getString("MessageID"));
                    getActivity().setResult(1, resultIntent);
                    getActivity().finish();
                }
            }
        });
    }
}
