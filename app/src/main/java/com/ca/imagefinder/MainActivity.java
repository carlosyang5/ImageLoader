package com.ca.imagefinder;

import android.content.Context;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageRecyclerViewAdapter mResultAdapter;
    private View mSearchBtn;
    private EditText mEditText;
    private ProgressBar mProgress;
    private TextView mHintView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.result_items_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mResultAdapter = new ImageRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mResultAdapter);
        mEditText = (EditText) findViewById(R.id.input_edit_text);
        mSearchBtn = findViewById(R.id.search_btn);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mHintView = (TextView) findViewById(R.id.hint_text);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (" ".equalsIgnoreCase(s.toString())) {
                    Toast.makeText(MainActivity.this, "Don't input text start with blank", Toast.LENGTH_SHORT).show();
                    mEditText.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchBtn.setEnabled(!TextUtils.isEmpty(s.toString()));
            }
        });
        mEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(mEditText.getText().toString())) {
                    mSearchBtn.performClick();
                    return true;
                }
                return false;
            }
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultAdapter.setImageResultList(null);
                updateUiForSearching();
                PixabayApiHelper.queryPixabaySearchApi(mEditText.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<PixabayResponse>() {
                            @Override
                            public void call(PixabayResponse pixabayResponse) {
                                if (pixabayResponse != null
                                        && pixabayResponse.getHits() != null
                                        && pixabayResponse.getHits().size() > 0) {
                                    mResultAdapter.setImageResultList(pixabayResponse);
                                    updateUiForSearchSuccess();
                                } else {
                                    updateUiForSearchError(R.string.hint_no_result);
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                if (Logger.ENABLE_LOG) {
                                    Logger.d("call: ex: " + throwable.toString());
                                }
                                updateUiForSearchError(R.string.hint_error);
                            }
                        });
            }
        });

    }


    private void updateUiForSearching() {
        mEditText.setEnabled(false);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        mSearchBtn.setEnabled(false);
        mHintView.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void updateUiForSearchSuccess() {
        mEditText.setEnabled(true);
        mSearchBtn.setEnabled(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        mHintView.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);
    }

    private void updateUiForSearchError(int hintStringRes) {
        mEditText.setEnabled(true);
        mSearchBtn.setEnabled(true);
        mRecyclerView.setVisibility(View.GONE);
        mHintView.setText(hintStringRes);
        mHintView.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }
}
