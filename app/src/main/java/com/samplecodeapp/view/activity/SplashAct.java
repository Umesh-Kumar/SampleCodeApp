package com.samplecodeapp.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PackageInfoCompat;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.samplecodeapp.R;
import com.samplecodeapp.Utils.Const;
import com.samplecodeapp.Utils.Util;
import com.samplecodeapp.roomdb.dao.BhagwatGeetaDao;
import com.samplecodeapp.roomdb.model.BhagavadGita;
import com.samplecodeapp.roomdb.db.RoomDataBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SplashAct extends AppCompatActivity {

    private long SPLASH_TIME_OUT = 2000;
    private SharedPreferences sharedPreferences;
    private long longVersionCode;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private Intent mainActIntent;
    private Dialog alertUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);

        sharedPreferences = getSharedPreferences(Const.strPreferences, MODE_PRIVATE);
        mainActIntent = new Intent(SplashAct.this, HomeActivity.class);

        try {
            longVersionCode = PackageInfoCompat.getLongVersionCode(getPackageManager()
                    .getPackageInfo(getPackageName(), 0));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            longVersionCode = 0;
        }

        // Get Remote Config instance.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // Create a Remote Config Setting to enable developer mode, which you can use to increase
        // the number of fetches available per hour during development. Also use Remote Config
        // Setting to set the minimum fetch interval.
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(100).build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        // Set default Remote Config parameter values. An app uses the in-app default values, and
        // when you need to adjust those defaults, you set an updated value for only the values you
        // want to change in the Firebase console. See Best Practices in the README for more
        // information.
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, task -> {
                    checkForUpdate();
                });
    }

    private void checkForUpdate() {
        long mVideoVersion = mFirebaseRemoteConfig.getLong(Const.M_BHAGWAT_GEETA_VERSION);
        if (mVideoVersion > sharedPreferences.getLong(Const.M_BHAGWAT_GEETA_VERSION, 0)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(Const.M_BHAGWAT_GEETA_VERSION, mFirebaseRemoteConfig.getLong(Const.M_BHAGWAT_GEETA_VERSION));
            editor.putString(Const.M_BHAGWAT_GEETA_JSON, mFirebaseRemoteConfig.getString(Const.M_BHAGWAT_GEETA_JSON));
            editor.apply();
            editor.commit();
            BhagwatGeetaDao bhagwatGeetaDao;
            RoomDataBase roomDataBase = RoomDataBase.getInstance(this);
            bhagwatGeetaDao = roomDataBase.bhagwatGeetaDao();
            List<BhagavadGita> allNotes = bhagwatGeetaDao.getAllNotesCount();
            try {
                JSONObject response = new JSONObject(mFirebaseRemoteConfig.getString(Const.M_BHAGWAT_GEETA_JSON));
                for (int i = 1; i <= response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject("chapter_" + i);
                    if (allNotes.size() == 0) {
                        bhagwatGeetaDao.insert(new BhagavadGita(
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                jsonObject.getString("chapter"),
                                jsonObject.getString("heading"),
                                jsonObject.getJSONArray("hindi").toString(),
                                i, 0));
                    } else {
                        BhagavadGita bhagavadGita = new BhagavadGita(
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                jsonObject.getString("chapter"),
                                jsonObject.getString("heading"),
                                jsonObject.getJSONArray("hindi").toString(),
                                i, 0);
                        bhagavadGita.setId(allNotes.get(0).getId());
                        bhagwatGeetaDao.update(bhagavadGita);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        long latestAppVersion = mFirebaseRemoteConfig.getLong(Const.VERSION_CODE);
        if (alertUpdate != null) alertUpdate.dismiss();

        if (latestAppVersion > longVersionCode) {
            String appUpdate = mFirebaseRemoteConfig.getString(Const.UPDATE_MESSAGE);
            boolean mIsForceUpdate = mFirebaseRemoteConfig.getBoolean(Const.FORCE_UPDATE);
            if (!appUpdate.equalsIgnoreCase(""))
                if (mIsForceUpdate)
                    updateAlertDialogue(1, appUpdate);
                else updateAlertDialogue(2, appUpdate);
            else callMasterApi();
        } else {
            callMasterApi();
        }
    }

    public void updateAlertDialogue(int position, String strMessage) {
        if (alertUpdate != null) alertUpdate.dismiss();
        alertUpdate = new Dialog(this);
        alertUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertUpdate.setCancelable(false);
        alertUpdate.setContentView(R.layout.dialogue_common);
        alertUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView dialogue_image = alertUpdate.findViewById(R.id.dialogue_image);
        TextView title = alertUpdate.findViewById(R.id.title);
        TextView message = alertUpdate.findViewById(R.id.message);
        Button read_btn = alertUpdate.findViewById(R.id.read_btn);
        Button btn_no = alertUpdate.findViewById(R.id.btn_no);
        dialogue_image.setImageResource(R.drawable.app_logo);
        title.setText(getString(R.string.alert_update_App));
        message.setText(strMessage);
        read_btn.setText(getString(R.string.btn_update));
        if (position == 2) {
            btn_no.setText(getString(R.string.btn_cancel));
            btn_no.setVisibility(View.VISIBLE);
        } else {
            btn_no.setVisibility(View.GONE);
        }

        read_btn.setOnClickListener(v -> {
            Util.playStoreCall(this, getPackageName());
        });
        btn_no.setOnClickListener(v -> {
            alertUpdate.dismiss();
            callMasterApi();
        });
        alertUpdate.show();
    }

    private void callMasterApi() {

        if (!Util.isNetworkAvailable(this)) {
            showDialogue(this);
            return;
        }

        new Handler().postDelayed(() -> {
            startActivity(mainActIntent);
            finish();
        }, SPLASH_TIME_OUT);
    }

    private void showDialogue(Activity act) {
        final Dialog dialog = new Dialog(act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialogue_internet_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView dialogue_image = dialog.findViewById(R.id.dialogue_image);
        TextView title = dialog.findViewById(R.id.title);
        TextView message = dialog.findViewById(R.id.message);
        title.setText(R.string.alert_internet_alert);
        message.setText(R.string.check_internet_connection);

        Button read_btn = dialog.findViewById(R.id.read_btn);
        Button read_continue = dialog.findViewById(R.id.read_cancel);

        read_btn.setText(act.getString(R.string.btn_ok));
        read_continue.setText(act.getString(R.string.btn_continue));

        read_btn.setOnClickListener(v -> {
            dialog.dismiss();
            act.runOnUiThread(() -> {
                callMasterApi();
            });
        });
        read_continue.setOnClickListener(v -> {
            dialog.dismiss();
            act.runOnUiThread(() -> {
                startActivity(mainActIntent);
                finish();
            });
        });
        dialog.setCancelable(false);
        dialog.show();
    }

}
