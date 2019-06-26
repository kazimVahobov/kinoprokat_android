package com.example.kinoprokat.common_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kinoprokat.R;
import com.example.kinoprokat.models.Login;
import com.example.kinoprokat.models.Role;
import com.example.kinoprokat.models.Token;
import com.example.kinoprokat.models.User;
import com.example.kinoprokat.modules.theater.activities.TheaterMainPage;
import com.example.kinoprokat.services.NetworkService;
import com.example.kinoprokat.services.RoleService;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private Button sign_in;
    private EditText login, password;
    private ProgressBar progress_bar;
    private TextView error_view;
    private NetworkService networkService;
    private RoleService roleService;
    private LinearLayout login_layout;
    private Timer timer;
    private AnimTimer anim_task;
    private Animation anim_exit_to_left, anim_enter_from_right;
    private String permissionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        networkService = NetworkService.getInstance();
        roleService = RoleService.getInstance();

        findItems();
        tokenExist();
    }

    public void tokenExist() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String shared_token = sharedPreferences.getString(networkService.getToken_key(), null);
        String shared_role_id = sharedPreferences.getString(roleService.getRole_key(), null);
        String shared_job_id = sharedPreferences.getString(networkService.getJob_id_key(), null);

        if (shared_token != null && shared_role_id != null && shared_job_id != null) {
            progress_bar.setVisibility(View.VISIBLE);
            networkService.setToken(shared_token);
            networkService.setJobId(shared_job_id);
            getRoleById(shared_role_id);
        } else {
            login_layout.setVisibility(View.VISIBLE);
        }
    }

    private void getUserById(String user_id) {
        networkService.getApies().getUserById(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    getRoleById(response.body().getRoleId());
                    sharedPreferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedPreferences.edit();
                    ed.putString(roleService.getRole_key(), response.body().getRoleId());
                    ed.apply();
                    String job_id = response.body().getDistId() != null ? response.body().getDistId() : response.body().getTheaterId();
                    ed.putString(networkService.getJob_id_key(), job_id);
                    ed.apply();
                    networkService.setJobId(job_id);
                } else if (response.code() == 404) {
                    viewError(getResources().getString(R.string.login_not_found));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                viewError(getString(R.string.error_of_network));
            }
        });
    }

    private void getRoleById(String role_id) {
        progress_bar.setVisibility(View.VISIBLE);
        networkService.getApies().getRoleById(role_id).enqueue(new Callback<Role>() {
            @Override
            public void onResponse(Call<Role> call, Response<Role> response) {
                if (response.isSuccessful()) {
                    roleService.setCurrentRole(response.body());

                    redirectToOtherPage(response.body().getTypeOfRole());

                    progress_bar.setVisibility(View.INVISIBLE);
                } else if (response.code() == 404 || response.code() == 401) {
                    // view error - try authorize again
                    login_layout.setVisibility(View.VISIBLE);
                    viewError(getString(R.string.try_connect_again));
                }
            }

            @Override
            public void onFailure(Call<Role> call, Throwable t) {
                login_layout.setVisibility(View.VISIBLE);
                viewError(getString(R.string.error_of_network));
            }
        });
    }

    private void redirectToOtherPage(int typeOfRole) {
        switch (typeOfRole) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                permissionKey = roleService.th_report_key;
                if (roleService.checkPermission(1, permissionKey)) {
                    Intent intent = new Intent(this, TheaterMainPage.class);
                    startActivity(intent);
                } else {
                    viewError(getString(R.string.you_have_not_access));
                    login_layout.setVisibility(View.VISIBLE);
                }
                break;
            }
            case 3: {
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_btn: {
                if (login.getText().length() == 0) {
                    viewError(getResources().getString(R.string.enter_login));
                } else if (password.getText().length() == 0) {
                    viewError(getResources().getString(R.string.enter_password));
                } else {
                    progress_bar.setVisibility(View.VISIBLE);
                    sign_in.setEnabled(false);
                    Login model = new Login(login.getText().toString(), password.getText().toString());
                    networkService.getApies().login(model).enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            if (response.isSuccessful()) {
                                networkService.setToken(response.body().getToken());
                                sharedPreferences = getPreferences(MODE_PRIVATE);
                                SharedPreferences.Editor ed = sharedPreferences.edit();
                                ed.putString(networkService.getToken_key(), response.body().getToken());
                                ed.apply();
                                getUserById(response.body().getUserId());
                            } else if (response.code() == 404) {
                                viewError(getResources().getString(R.string.login_not_found));
                            } else if (response.code() == 409) {
                                viewError(getResources().getString(R.string.invalid_password));
                            }
                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            viewError(getString(R.string.error_of_network));
                        }
                    });
                }
                break;
            }
        }
    }

    public void viewError(String text) {
        error_view.setText(text);
        error_view.setVisibility(View.VISIBLE);
        error_view.startAnimation(anim_enter_from_right);
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        anim_task = new AnimTimer();
        timer.schedule(anim_task, 2000);
        progress_bar.setVisibility(View.INVISIBLE);
        sign_in.setEnabled(true);
    }

    class AnimTimer extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    error_view.startAnimation(anim_exit_to_left);
                    error_view.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    private void findItems() {
        login_layout = (LinearLayout) findViewById(R.id.login_layout);

        sign_in = (Button) findViewById(R.id.sign_in_btn);
        sign_in.setOnClickListener(this);

        login = (EditText) findViewById(R.id.login_txt);

        password = (EditText) findViewById(R.id.password_txt);

        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        error_view = (TextView) findViewById(R.id.login_error);

        anim_exit_to_left = AnimationUtils.loadAnimation(this, R.anim.anim_exit_to_left);
        anim_enter_from_right = AnimationUtils.loadAnimation(this, R.anim.anim_enter_from_right);
    }
}
