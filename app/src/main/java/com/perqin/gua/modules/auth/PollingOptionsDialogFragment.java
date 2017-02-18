package com.perqin.gua.modules.auth;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Spinner;

import com.perqin.gua.R;

/**
 * Author   : perqin
 * Date     : 17-2-18
 */

public class PollingOptionsDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private PollingOptionsDialogListener mListener;

    public static PollingOptionsDialogFragment newInstance() {
        return new PollingOptionsDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.polling_options)
                .setView(R.layout.dialog_polling_options)
                .setPositiveButton(R.string.start_polling, this)
                .setNegativeButton(R.string.cancel, this);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PollingOptionsDialogListener) {
            mListener = (PollingOptionsDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (mListener != null) {
                Spinner spinner = (Spinner) getDialog().findViewById(R.id.spinner);
                String service = (String) spinner.getSelectedItem();
                PollingOptions options = new PollingOptions();
                options.setPushService(service);
                mListener.onStartClick(options);
            }
        }
    }

    public static class PollingOptions {
        private String pushService;

        public String getPushService() {
            return pushService;
        }

        public void setPushService(String pushService) {
            this.pushService = pushService;
        }
    }

    public interface PollingOptionsDialogListener {
        void onStartClick(PollingOptions options);
    }
}
