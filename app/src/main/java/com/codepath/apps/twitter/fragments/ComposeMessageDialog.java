package com.codepath.apps.twitter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.network.TwitterClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shyam Rokde on 2/18/16.
 */
public class ComposeMessageDialog extends DialogFragment {

  @Bind(R.id.etName) EditText etName;
  @Bind(R.id.etMessage) EditText etMessage;
  @Bind(R.id.ibCancel) ImageButton ibCancel;
  @Bind(R.id.btnSend) Button btnSend;

  private TwitterClient twitterClient;
  private Context mContext;

  public ComposeMessageDialog(){}


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_message, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    mContext = getContext();

    ButterKnife.bind(this, view);

    // Get client
    twitterClient = TwitterApplication.getRestClient();

    return view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public static ComposeMessageDialog newInstance(){
    ComposeMessageDialog dialog = new ComposeMessageDialog();
    Bundle args = new Bundle();
    dialog.setArguments(args);
    return dialog;
  }

  @OnClick(R.id.ibCancel)
  public void onCancelButtonClick(){
    dismiss();
  }

  @OnClick(R.id.btnSend)
  public void onSendMessageButtonClick(){
    // Post Tweet
    twitterClient.postMessage(mPostMessageResponseHandler, etName.getText().toString(), etMessage.getText().toString());
  }

  private final TextHttpResponseHandler mPostMessageResponseHandler = new TextHttpResponseHandler(){
    @Override
    public void onStart() {
      Log.d("DEBUG", "POST Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
      Log.d("DEBUG", "POST Resposne: " + responseString);
      Toast.makeText(getContext(), "Message posted Successfully!!!", Toast.LENGTH_SHORT).show();

      dismiss();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
      Log.d("ERROR", "Unable to Post Message - " + responseString);
      Toast.makeText(getContext(), "Unable to connect to twitter.com", Toast.LENGTH_SHORT).show();
    }
  };

}
