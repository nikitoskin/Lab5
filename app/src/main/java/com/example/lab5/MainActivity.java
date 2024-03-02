package com.example.lab5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lab5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static int NOTIFICATION_ID = 1;
    private static String CHANNEL_ID="ch1";
    private NotificationManagerCompat notificationManagerCompat;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManagerCompat=NotificationManagerCompat.from(this);
    }

    public void onclick_feedback(View view) {
        EditText fioInput = findViewById(R.id.fioInput);
        EditText emailInput = findViewById(R.id.emailInput);
        if (!fioInput.getText().toString().isEmpty() && !emailInput.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Данные успешно отправлены", Toast.LENGTH_LONG).show();
            fioInput.getText().clear();
            emailInput.getText().clear();
        }
        else if (fioInput.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Введите ФИО", Toast.LENGTH_SHORT).show();
        }
        else if (emailInput.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Введите Email", Toast.LENGTH_SHORT).show();
        }
    }

    public void open_dialog (View View) {
        final String[] listItems = new String[]{"7", "11", "121", "8"};
        final int[] checkedItem = {-1};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Выберите правильный ответ")
                .setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
                    checkedItem[0] = which;
                })
                .setNegativeButton("Отмена", (dialog, which) -> {

                })
                .setPositiveButton("Принять",  (dialog, which) -> {
                    if (String.valueOf(listItems[checkedItem[0]]).equals("8")) {
                        Toast.makeText(MainActivity.this, "ПРАВИЛЬНО!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Не верно, попробуй ещё.", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("MissingPermission")
    public void onclick_notification(View view) {
        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Уведомление")
                        .setContentText("Телегин Никита Константинович Т-403901")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
}