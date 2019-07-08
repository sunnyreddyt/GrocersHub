package com.grocers.hub.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.grocers.hub.R;
import com.grocers.hub.constants.Shared;


/**
 * Created by ctel-cpu-84 on 2/8/2018.
 */

public class AccountFragment extends Fragment {

    LinearLayout signOutLayout, ordersLayout;
    Shared shared;
    TextView userNameTextView, userNumberTextView, userMailTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        signOutLayout = (LinearLayout) view.findViewById(R.id.signOutLayout);
        userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
        userNumberTextView = (TextView) view.findViewById(R.id.userNumberTextView);
        userMailTextView = (TextView) view.findViewById(R.id.userMailTextView);
        ordersLayout = (LinearLayout) view.findViewById(R.id.ordersLayout);
        shared = new Shared(getActivity());


       /* userNameTextView.setText(shared.getUserName());
        userNumberTextView.setText(shared.getUserMobile());
        userMailTextView.setText(shared.getUserEmail());*/
        signOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("")
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                shared.setUserMobile("");
                                shared.setUserUserEmail("");
                                shared.setUserName("");
                                shared.setUserLocation("");
                                shared.setUserLatitude("");
                                shared.setUserLongitude("");
                                Toast.makeText(getActivity(), "Signout Successful", Toast.LENGTH_SHORT).show();


                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        })
                        .show();


            }
        });

        ordersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);*/
            }
        });

        return view;
    }


}
