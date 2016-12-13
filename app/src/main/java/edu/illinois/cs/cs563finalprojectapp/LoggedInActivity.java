package edu.illinois.cs.cs563finalprojectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by winglam on 12/12/16.
 */
public class LoggedInActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_main);


        TextView back = (TextView) findViewById(R.id.back);
        View.OnClickListener listener = new BackOnClickListener(this);
        back.setOnClickListener(listener);

    }
}

class BackOnClickListener implements View.OnClickListener {
    Activity myLovelyVariable;

    public BackOnClickListener(Activity myLovelyVariable) {
        this.myLovelyVariable = myLovelyVariable;
    }

    @Override public void onClick(View v) {
        Intent intent = new Intent(myLovelyVariable, MainActivity.class);
        myLovelyVariable.startActivity(intent);
    }
}