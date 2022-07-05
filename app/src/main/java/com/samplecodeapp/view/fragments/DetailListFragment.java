package com.samplecodeapp.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.samplecodeapp.R;
import com.samplecodeapp.Utils.Const;
import com.samplecodeapp.Utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailListFragment extends Fragment {

    public static Typeface face;
    public Typeface faceKrutiDev, faceDevlysDefault;
    @BindView(R.id.text_heading)
    TextView textHeading;
    @BindView(R.id.text_upvach)
    TextView textUpvach;
    @BindView(R.id.text_shalok_no)
    TextView textShalokNo;
    @BindView(R.id.text_shalok)
    TextView textShalok;
    @BindView(R.id.btnLike)
    ImageView btnLike;
    @BindView(R.id.text_like)
    TextView textLike;
    @BindView(R.id.linearLayoutLike)
    LinearLayout linearLayoutLike;
    @BindView(R.id.btnRate)
    ImageView btnRate;
    @BindView(R.id.text_rate)
    TextView textRate;
    @BindView(R.id.linearLayoutRate)
    LinearLayout linearLayoutRate;
    @BindView(R.id.btnShare)
    ImageView btnShare;
    @BindView(R.id.text_share)
    TextView textShare;
    @BindView(R.id.linearLayoutShare)
    LinearLayout linearLayoutShare;
    @BindView(R.id.btnBookmark)
    ImageView btnBookmark;
    @BindView(R.id.text_bookmark)
    TextView textBookmark;
    @BindView(R.id.linearLayoutBookmark)
    LinearLayout linearLayoutBookmark;
    @BindView(R.id.text_description)
    TextView textDescription;
    @BindView(R.id.linearLayoutMain)
    LinearLayout linearLayoutMain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_list, container, false);
        ButterKnife.bind(this, rootView);
//        HashMap<String, String> hashMap = ShalokActivity.mShalokMap.get(0);
        face = Typeface.createFromAsset(getActivity().getAssets(), Const.helveticaLight);
        faceKrutiDev = Typeface.createFromAsset(getActivity().getAssets(), Const.helveticaRounded);
        faceDevlysDefault = Typeface.createFromAsset(getActivity().getAssets(), Const.helveticaNarrow);
        textDescription.setTypeface(faceDevlysDefault);
        textUpvach.setTypeface(faceDevlysDefault);
        textHeading.setTypeface(faceDevlysDefault);
        textShalok.setTypeface(faceKrutiDev);
        textShalokNo.setTypeface(faceDevlysDefault);

        Bundle bundle = getArguments();
        if (!bundle.getString("sh_upvach").equalsIgnoreCase("")) {
            textUpvach.setVisibility(View.VISIBLE);
            textUpvach.setText(bundle.getString("sh_upvach") + ":");
        } else {
            textUpvach.setVisibility(View.INVISIBLE);
        }

        textShalokNo.setText(bundle.getString("sh_sno"));
        textHeading.setText(bundle.getString("sh_heading"));
        textShalok.setText(bundle.getString("sh_title"));
        textDescription.setText(bundle.getString("sh_description") + "\n");

//        textDescription.setMovementMethod(new ScrollingMovementMethod());

        return rootView;
    }


    @OnClick({R.id.btnLike, R.id.text_like, R.id.btnRate, R.id.text_rate, R.id.btnShare,
            R.id.text_share, R.id.btnBookmark, R.id.text_bookmark})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLike:
            case R.id.text_like:
//                break;
            case R.id.btnRate:
            case R.id.text_rate:
                Util.playStoreCall(getActivity(), getActivity().getPackageName());
                break;
            case R.id.btnShare:
            case R.id.text_share:
                String shareShalok = "";
                shareShalok = getString(R.string.share_text, getString(R.string.adhyay) + " " +
                        getArguments().getInt("adhyay_no") + "  -  " + getArguments().getString("sh_sno") + "\n\n" +
                        getArguments().getString("sh_title").replace("\n ", "\n") + "\n\n" + getString(R.string.arthath) + ":\n" +
                        getArguments().getString("sh_description").replace("\n", " ") + "\n\n" +
                        "\uD83D\uDE4F Download App:\nhttps://play.google.com/store/apps/details?id=" + getActivity().getPackageName());

                List<Intent> targetedShareIntents = new ArrayList<>();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(shareIntent, 0);
                if (!resInfo.isEmpty()) {
                    for (ResolveInfo resolveInfo : resInfo) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
                        targetedShareIntent.setType("text/plain");
                        targetedShareIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name));
                        targetedShareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name)); // In gmail subject
                        if (TextUtils.equals(packageName, "com.facebook.katana")) {
                            targetedShareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                        } else {
                            targetedShareIntent.putExtra(Intent.EXTRA_TEXT, shareShalok);
                        }
                        targetedShareIntent.setPackage(packageName);
                        targetedShareIntents.add(targetedShareIntent);
                    }
                    Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[targetedShareIntents.size()]));
                    startActivity(chooserIntent);
                }
                break;
            case R.id.btnBookmark:
            case R.id.text_bookmark:
                SharedPreferences.Editor edit = getActivity().getSharedPreferences(Const.sharedPref, MODE_PRIVATE).edit();
                edit.putInt("shalokPosition", getArguments().getInt("shalok_position", 0));
                edit.putInt("adhyayNo", getArguments().getInt("adhyay_no") - 1).apply();
                edit.commit();
                Toast.makeText(getActivity(), getString(R.string.bookmark_saved), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
