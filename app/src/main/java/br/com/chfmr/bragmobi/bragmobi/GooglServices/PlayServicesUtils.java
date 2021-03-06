package br.com.chfmr.bragmobi.bragmobi.GooglServices;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by carlosfm on 3/31/15.
 */
public class PlayServicesUtils {

    public final static int REQUEST_CODE_ERRO_PLAY_SERVICES = 9000;

    public static boolean googlePlayServicesDisponivel(FragmentActivity activity){

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);

        if(ConnectionResult.SUCCESS == resultCode){
            return true;
        } else {
            showMesageOfErro(activity, resultCode);
            return false;
        }
    }

    public static void showMesageOfErro(FragmentActivity activity, int codOfError){

            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(codOfError, activity, REQUEST_CODE_ERRO_PLAY_SERVICES);

            if(errorDialog != null){
                MessageDialogFragment errorFragment = new MessageDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(activity.getSupportFragmentManager(), "DIALOG_ERRO_PLAY_SERVICES");
            }
    }
}
