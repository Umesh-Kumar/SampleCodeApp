package com.samplecodeapp.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.samplecodeapp.BuildConfig;
import com.samplecodeapp.R;
import com.samplecodeapp.Utils.Const;
import com.samplecodeapp.Utils.Util;
import com.samplecodeapp.interfaces.DetailEnum;
import com.samplecodeapp.interfaces.InterfaceMainListCallBack;
import com.samplecodeapp.roomdb.dao.BhagwatGeetaDao;
import com.samplecodeapp.roomdb.db.RoomDataBase;
import com.samplecodeapp.roomdb.model.BhagavadGita;
import com.samplecodeapp.view.adapter.BhagwatGeetaAdapter;
import com.samplecodeapp.viewmodel.BhagwatGeetaViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements InterfaceMainListCallBack {

    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private BhagwatGeetaViewModel bhagwatGeetaViewModel;
    private BhagwatGeetaDao bhagwatGeetaDao;
    private ProgressDialog progressDialog;
    private List<BhagavadGita> localBhagavadGita = new ArrayList<>();

    private AlertDialog dialog = null;
    private int selectedItem = 0;
    private boolean doubleBackToExitPressedOnce = false;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // initial toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        ImageView icMenu = toolbar.findViewById(R.id.icMenu);
        setSupportActionBar(toolbar);

        // navigation bar setup
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle(getString(R.string.short_name));
        ab.setDisplayHomeAsUpEnabled(false);

        icMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(navView)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        if (navView != null) {
            setupDrawerContent(navView);
            View headerView = navView.getHeaderView(0);
            ImageView imageView = headerView.findViewById(R.id.imageView);
            ImageView imageView_edit = headerView.findViewById(R.id.imageView_edit);
            imageView_edit.setOnClickListener(view -> {

            });
            TextView textView_name = headerView.findViewById(R.id.textView_name);
            TextView textView_sub_name = headerView.findViewById(R.id.textView_sub_name);
            Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), Const.helveticaNarrow);
            Typeface helvetica_light = Typeface.createFromAsset(getApplicationContext().getAssets(), Const.helveticaRounded);
            textView_name.setTypeface(helvetica_light);
            textView_sub_name.setTypeface(typeface);
            textView_name.setText(R.string.title_jai_shri_krishana);
            textView_sub_name.setText(R.string.title_radhe_krishana);
        }

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.short_name));

        // listing scrolling setup
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        // listing setup
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        final BhagwatGeetaAdapter adapter = new BhagwatGeetaAdapter(this, this);
        recyclerView.setAdapter(adapter);

        // live data from DB using AndroidViewModel
        bhagwatGeetaViewModel = new ViewModelProvider(this).get(BhagwatGeetaViewModel.class);
        Observer<List<BhagavadGita>> observer = bhagwatGeeta -> {
            localBhagavadGita = bhagwatGeeta;
            if (localBhagavadGita != null && localBhagavadGita.size() > 0) {
                adapter.setBhagavadGitas(localBhagavadGita);
            } else {
                if (Util.isNetworkAvailable(HomeActivity.this)) {
                    showProgressDialogue();
                    apiToServer();
                } else {
                    Util.showToast(HomeActivity.this, getString(R.string.check_internet_alert));
                }
            }
        };
        bhagwatGeetaViewModel.getAllNotes().observe(this, observer);
    }

    /* change banner images randomly */
    private void loadBackdrop() {
        Glide.with(this).load(DetailEnum.getRandomCheeseDrawable())
                .apply(RequestOptions.centerCropTransform())
                .into(backdrop);
    }

    @OnClick({R.id.fab})
    public void onViewClicked(View v) {
        if (Util.btnClickTime(0.8)) {
            SharedPreferences sharedPreferences = getSharedPreferences(Const.sharedPref, MODE_PRIVATE);
            if (sharedPreferences.getInt("adhyayNo", -1) == -1) {
                Util.showSnackBar(findViewById(android.R.id.content), getString(R.string.no_bookmark_available));
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.bookmark_restored), Toast.LENGTH_LONG).show();
                callDetailedAct(true, sharedPreferences.getInt("adhyayNo", 0), sharedPreferences.getInt("shalokPosition", 0));
            }
        }
    }

    /* data fetching from server */
    public void apiToServer() {
        final String url = BuildConfig.setUp + BuildConfig.key1 + Const.initCall;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Util.logVE("MySyncStatus", response.toString());
                    if (response != null && response.length() > 0) {
                        new PopulateDbAsyncTask(response).execute();
                    }

                }, error -> {
            if (progressDialog.isShowing()) progressDialog.dismiss();
            if (!Util.isNetworkAvailable(HomeActivity.this)) {
                Toast.makeText(getApplicationContext(), R.string.please_check_internet, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.server_error_occured, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Const.headParam1, Const.paramVal1);
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES + 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    /* JSON data to UI setup */
    private class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private JSONObject response;

        private PopulateDbAsyncTask(JSONObject response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog.isShowing()) progressDialog.dismiss();
            showProgressDialogue();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 1; i <= response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject("chapter_" + i);
                    // "श्रीमद भागवत गीता"
                    /*"श्रीमद भागवत गीता"*/
                    RoomDataBase roomDataBase = RoomDataBase.getInstance(HomeActivity.this);
                    bhagwatGeetaDao = roomDataBase.bhagwatGeetaDao();
                    bhagwatGeetaDao.insert(new BhagavadGita(
                            jsonObject.getString("title"),
                            jsonObject.getString("description"),
                            jsonObject.getString("chapter"),
                            jsonObject.getString("heading"),
                            jsonObject.getJSONArray("hindi").toString(),
                            i, 0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) progressDialog.dismiss();
        }
    }

    /* show data load progress */
    private void showProgressDialogue() {
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage(getString(R.string.syncing_with_server_pls_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /* navigation drawer setup */
    private void setupDrawerContent(NavigationView navView) {
        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    switch (menuItem.getItemId()) {
                        case android.R.id.home:
                            drawerLayout.openDrawer(GravityCompat.START);
                            return true;
                        case R.id.nav_home:

                            break;
                        case R.id.adhyay_1:
                            return callDetailedAct(true, 0, 0);
                        case R.id.adhyay_2:
                            return callDetailedAct(true, 1, 0);
                        case R.id.adhyay_3:
                            return callDetailedAct(true, 2, 0);
                        case R.id.adhyay_4:
                            return callDetailedAct(true, 3, 0);
                        case R.id.adhyay_5:
                            return callDetailedAct(true, 4, 0);
                        case R.id.adhyay_6:
                            return callDetailedAct(true, 5, 0);
                        case R.id.adhyay_7:
                            return callDetailedAct(true, 6, 0);
                        case R.id.adhyay_8:
                            return callDetailedAct(true, 7, 0);
                        case R.id.adhyay_9:
                            return callDetailedAct(true, 8, 0);
                        case R.id.adhyay_10:
                            return callDetailedAct(true, 9, 0);
                        case R.id.adhyay_11:
                            return callDetailedAct(true, 10, 0);
                        case R.id.adhyay_12:
                            return callDetailedAct(true, 11, 0);
                        case R.id.adhyay_13:
                            return callDetailedAct(true, 12, 0);
                        case R.id.adhyay_14:
                            return callDetailedAct(true, 13, 0);
                        case R.id.adhyay_15:
                            return callDetailedAct(true, 14, 0);
                        case R.id.adhyay_16:
                            return callDetailedAct(true, 15, 0);
                        case R.id.adhyay_17:
                            return callDetailedAct(true, 16, 0);
                        case R.id.adhyay_18:
                            return callDetailedAct(true, 17, 0);
                        case R.id.nav_more:
                            Util.showToast(this, getString(R.string.coming_soon));
                            break;
                        case R.id.nav_select_language:
                            menuItem.setChecked(false);
                            drawerLayout.closeDrawers();

                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                            builder.setTitle(R.string.select_language);
                            String[] languages = {getString(R.string.hindi)};
                            SharedPreferences sharedPreferences = getSharedPreferences(Const.sharedPref, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            selectedItem = sharedPreferences.getInt("selectedItem", 0);
                            builder.setSingleChoiceItems(languages, selectedItem, (dialogInterface, i) -> {
                                selectedItem = i;
                            });
                            builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                                if (dialog != null) dialog.cancel();
                            });
                            builder.setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                                editor.putInt("selectedItem", selectedItem);
                                editor.commit();
                                if (dialog != null) dialog.cancel();
                            });
                            dialog = builder.create();
                            dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialog;
                            dialog.setCancelable(false);
                            dialog.show();
                            return true;
                        case R.id.nav_exit:
                            finish();
                            break;
                    }

                    if (drawerLayout != null) drawerLayout.closeDrawers();

                    return true;
                });
    }

    /* navigate to content from drawer */
    private boolean callDetailedAct(boolean isAdhyayCalled, int position, int shalok_position) {
        if (isAdhyayCalled) {
            BhagavadGita currentBhagavadGita = localBhagavadGita.get(position);
            Intent detailActivity = new Intent(HomeActivity.this, ShalokActivity.class);
            detailActivity.putExtra("json_data", currentBhagavadGita.getHindiJson());
            detailActivity.putExtra("shalok_position", shalok_position);
            detailActivity.putExtra("adhyay_no", (position + 1));
            startActivity(detailActivity);
            if (drawerLayout != null) drawerLayout.closeDrawers();
        }
        return true;
    }

    @Override
    public void listCallBack(int position) {
        Intent detailActivity = new Intent(this, ShalokActivity.class);
        detailActivity.putExtra("json_data", localBhagavadGita.get(position).getHindiJson());
        detailActivity.putExtra("adhyay_no", (position + 1));
        startActivity(detailActivity);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, getString(R.string.exit_message), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBackdrop();
        if (navView != null) navView.setCheckedItem(R.id.nav_home);
    }
}
