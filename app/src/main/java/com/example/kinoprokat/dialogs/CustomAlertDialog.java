package com.example.kinoprokat.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.example.kinoprokat.R;

public class CustomAlertDialog extends AppCompatDialogFragment {

    private Context context;
    private boolean isAction;
    private String title;
    private String message;

    private static String MODE_KEY = "mode";
    private static String TITLE_KEY = "title";
    private static String MESSAGE_KEY = "message";

    private CustomDialogListener listener;

    public static CustomAlertDialog newInstance(boolean isAction, String title, String message) {
        CustomAlertDialog alertDialog = new CustomAlertDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(MODE_KEY, isAction);
        bundle.putString(TITLE_KEY, title);
        bundle.putString(MESSAGE_KEY, message);
        alertDialog.setArguments(bundle);
        return alertDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAction = getArguments().getBoolean(MODE_KEY);
        title = getArguments().getString(TITLE_KEY);
        message = getArguments().getString(MESSAGE_KEY);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!isAction) {
                    dialogInterface.cancel();
                } else {
                    listener.customDialogConfirm();
                }
            }
        });
        if (isAction) {
            builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
        }

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        listener = (CustomDialogListener) context;
    }

    public interface CustomDialogListener {
        void customDialogConfirm();
    }
}
