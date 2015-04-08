package br.com.chfmr.bragmobi.bragmobi;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;

/**
 * Created by carlosfm on 3/31/15.
 */
public class MessageDialogFragment extends DialogFragment {

    private Dialog mDialog;

    public MessageDialogFragment(){
        super();
        mDialog = null;
        setRetainInstance(true);
    }

    public void setDialog(Dialog dialog){
        mDialog = dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return mDialog;
    }
}
