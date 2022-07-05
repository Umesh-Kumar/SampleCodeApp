package com.samplecodeapp.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.samplecodeapp.R;

public class Util {

    private static Dialog dialogNetwork;
    private static long mLastClickTime = 0;

    /* network check */
    public static boolean isNetworkAvailable(Activity context) {
        try {
            if (context == null) return false;

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            return true;
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return true;
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                            return true;
                        }
                    }
                } else {
                    try {
                        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            return true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /* network alert */
    public static void internetAlert(Activity activity) {
        showDialogue(activity, activity.getString(R.string.check_internet_connection), R.drawable.app_logo);
    }

    /* show message */
    private static void showDialogue(Activity act, String strMessage, int drawable) {

        if (dialogNetwork != null && dialogNetwork.isShowing()) {
            return;
        }
        dialogNetwork = new Dialog(act);
        dialogNetwork.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNetwork.setCancelable(false);
        dialogNetwork.setContentView(R.layout.dialogue_internet_alert);
        dialogNetwork.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView dialogue_image = dialogNetwork.findViewById(R.id.dialogue_image);
        TextView title = dialogNetwork.findViewById(R.id.title);
        TextView message = dialogNetwork.findViewById(R.id.message);
        dialogue_image.setImageResource(drawable);
//        title.setText(strTitle);
        message.setText(strMessage);

        Button read_btn = dialogNetwork.findViewById(R.id.read_btn);
        Button btn_no = dialogNetwork.findViewById(R.id.btn_no);

        read_btn.setText(act.getString(R.string.btn_ok));
        btn_no.setVisibility(View.GONE);

        read_btn.setOnClickListener(v -> {
            if (Util.isNetworkAvailable(act)) {
                dialogNetwork.dismiss();
            } else {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                act.startActivity(intent);
            }

        });
        btn_no.setOnClickListener(v -> dialogNetwork.dismiss());

        dialogNetwork.show();
    }

    /* snackbar alert */
    public static void showSnackBar(View viewAct, String strMessage) {
        Snackbar snackbar = Snackbar.make(viewAct, strMessage, Snackbar.LENGTH_LONG).setAction("Action", null);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.RED);
        TextView mainTextView = (view).findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);
        mainTextView.setGravity(Gravity.CENTER);
        snackbar.show();
    }

    /* click controler */
    public static boolean btnClickTime(double pauseTime) {
        // mis-clicking prevention, using threshold of 1000 ms
        pauseTime = pauseTime * 1000;
        if (SystemClock.elapsedRealtime() - mLastClickTime < pauseTime) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }

    /* rating + review */
    public static void playStoreCall(Activity act, String strPackage) {
        try {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(act.getString(R.string.play_store_market_url, strPackage))));
        } catch (ActivityNotFoundException a) {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(act.getString(R.string.play_store_url, strPackage))));
        }
    }

    /* common toast alert */
    public static void showToast(Activity activity, String strMessage) {
        Toast.makeText(activity, strMessage, Toast.LENGTH_LONG).show();
    }

    /* log details */
    public static void logVE(String Tag, String message) {
        // TODO: comment it before go live
        Log.v(Tag, message);
        Log.e(Tag, message);
    }

}
