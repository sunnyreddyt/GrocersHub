package com.grocers.hub;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grocers.hub.adapters.SupportDetailsAdapter;
import com.grocers.hub.fragments.MainActivity;
import com.grocers.hub.models.ApplyCouponResponse;
import com.grocers.hub.models.FinalOrderResponse;
import com.grocers.hub.models.GeneralResponse;
import com.grocers.hub.models.SuggestionRequest;
import com.grocers.hub.models.SupportResponse;
import com.grocers.hub.network.APIInterface;
import com.grocers.hub.network.ApiClient;
import com.grocers.hub.utils.GHUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionsActivity extends AppCompatActivity {

    private ImageView backImageView;
    private EditText userNameEditText;
    private EditText phoneEditText;
    private EditText messageEditText;
    private TextView submitTextView;
    private TextView headingTextView;
    private GHUtil ghUtil;
    private Context context;
    private String type;
    private static final String SUGGESTION_TYPE = "suggestion";
    private static final String CONTACT_US_TYPE = "contactus";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        context = SuggestionsActivity.this;
        ghUtil = GHUtil.getInstance(context);
        headingTextView = (TextView) findViewById(R.id.headingTextView);
        backImageView = (ImageView) findViewById(R.id.backImageView);
        userNameEditText = (EditText) findViewById(R.id.nameEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        submitTextView = (TextView) findViewById(R.id.submitTextView);

        Intent intent = getIntent();
        if (intent.hasExtra("type")) {
            type = intent.getStringExtra("type");
            if (type.equalsIgnoreCase(SUGGESTION_TYPE)) {
                headingTextView.setText("Write us how can we improve");
            } else if (type.equalsIgnoreCase(CONTACT_US_TYPE)) {
                headingTextView.setText("Share details with us, we will contact you");
            }
        }

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validForm()) {
                    if (ghUtil.isConnectingToInternet()) {
                        SuggestionRequest suggestionRequest = new SuggestionRequest();
                        suggestionRequest.setName(userNameEditText.getText().toString());
                        suggestionRequest.setPhone(phoneEditText.getText().toString());
                        suggestionRequest.setMessage(messageEditText.getText().toString());
                        submit(suggestionRequest);
                    } else {
                        Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean validForm() {
        boolean isValid = true;
        if (!(userNameEditText.getText().toString().trim().length() > 0)) {
            isValid = false;
            userNameEditText.setError("Invalid name");
        }
        if (!ghUtil.isValidPhone(phoneEditText.getText().toString())) {
            isValid = false;
            phoneEditText.setError("Invalid phone");
        }
        if (!(messageEditText.getText().toString().trim().length() > 0)) {
            isValid = false;
            messageEditText.setError("Invalid address");
        }
        return isValid;
    }

    public void submit(SuggestionRequest suggestionRequest) {
        ghUtil.showDialog(SuggestionsActivity.this);
        APIInterface service = ApiClient.getClient().create(APIInterface.class);
        Call<GeneralResponse> generalResponseCall = null;
        if (type.equalsIgnoreCase(SUGGESTION_TYPE)) {
            generalResponseCall = service.submitSuggestion(suggestionRequest.getName(),
                    suggestionRequest.getPhone(), suggestionRequest.getMessage());
        } else if (type.equalsIgnoreCase(CONTACT_US_TYPE)) {
            generalResponseCall = service.submitContactUsRequest(suggestionRequest.getName(),
                    suggestionRequest.getPhone(), suggestionRequest.getMessage());
        }
        try {
            generalResponseCall.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    ghUtil.dismissDialog();
                    if (response.code() == 200) {
                        successDialog();
                    } else {
                        Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    ghUtil.dismissDialog();
                    Toast.makeText(context, "Something went wrong, please try after sometime", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void successDialog() {
        final Dialog successDialog = new Dialog(SuggestionsActivity.this);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setContentView(R.layout.suggestion_dialog_success);
        successDialog.setCanceledOnTouchOutside(false);
        successDialog.setCancelable(false);

        TextView titleTextView = (TextView) successDialog.findViewById(R.id.titleTextView);
        TextView okTextView = (TextView) successDialog.findViewById(R.id.okTextView);
        TextView messageTextView = (TextView) successDialog.findViewById(R.id.messageTextView);

        if (type.equalsIgnoreCase(SUGGESTION_TYPE)) {
            messageTextView.setText("Thank you for your valuable suggestions");
        } else if (type.equalsIgnoreCase(CONTACT_US_TYPE)) {
            messageTextView.setText("Thank you for your contacting us");
        }

        titleTextView.setText("Hi " + userNameEditText.getText().toString() + ", ");
        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successDialog.dismiss();
                finish();
            }
        });
        successDialog.show();
    }
}
