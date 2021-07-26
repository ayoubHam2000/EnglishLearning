package com.example.englishlearning.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.example.englishlearning.R;

public class TextErrors {

    private Context context = null;

    public TextErrors(Context context){
        this.context = context;
    }

    public void creat(final String text){
        final AlertDialog.Builder newBuilder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.text_error_view, null, false);

        newBuilder.setView(view);
        AlertDialog alertDialog = newBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                TextView errorText = view.findViewById(R.id.errorText);

                errorText.setText(Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY));
            }
        });

        alertDialog.show();

    }

}
