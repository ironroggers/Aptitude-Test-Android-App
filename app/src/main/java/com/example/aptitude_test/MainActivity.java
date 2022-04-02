package com.example.aptitude_test;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button q1, q2, q3, submit;
    String[] ans1 = {"Mahatma Gandhi","Neil Armstrong","Buzz Aldrin","Margret Thatcher"};
    String[] ans2 = {"1000","2000","200","500"};
    boolean[] itemsChecked = {false, false, false, false};
    ProgressBar pb;
    int a1=-1, a3=-1,ques=0;
    ArrayList<String> arr = new ArrayList();
    String text_ans="";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        q1 = findViewById(R.id.ques1);
        q2 = findViewById(R.id.ques2);
        q3 = findViewById(R.id.ques3);
        submit = findViewById(R.id.submit);
        pb = findViewById(R.id.determinateBar);
        arr.clear();
        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Who was the first person to land on Moon?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getBaseContext(), "Q1 Answered", Toast.LENGTH_SHORT).show();
                                a1=((AlertDialog)dialog).getListView().getCheckedItemPosition();
                                ques++;
                                pb.setProgress(pb.getProgress()+33);
                            }
                        })
                        .setNegativeButton("Clear", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_SHORT).show();
                                a1=-1;
                            }
                        })
                        .setSingleChoiceItems(ans1, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }

                        }).create();
                dialog.show();
            }
        });

        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog1 = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("What denomination of currency was/were demonetised in Nov-18?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getBaseContext(), "Q2 Answered", Toast.LENGTH_SHORT).show();
                                pb.setProgress(pb.getProgress()+33);
                                ques++;
                            }
                        })
                        .setNegativeButton("Clear", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_SHORT).show();
                                ((AlertDialog) dialog).getListView().setItemChecked(which, false);
                                arr.clear();

                            }
                        })
                        .setMultiChoiceItems(ans2, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(!arr.contains(ans2[which])){
                                    arr.add(ans2[which]);
                                }else{
                                    arr.remove(ans2[which]);
                                }
                            }
                        }).create();
                dialog1.show();
            }
        });

        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailog();
            }
        });

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel(notificationManager);

        submit.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure you want to submit?"+"\nYou have attempted "+ques+" Questions")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getBaseContext(), "Evaluating your result \n Check Notification", Toast.LENGTH_SHORT).show();
                                ques++;Intent i = new Intent(MainActivity.this, score.class);
                                i.putExtra("ans1", a1);
                                i.putStringArrayListExtra("ans2", arr);
                                i.putExtra("ans3", a3);
                                Log.d("VR", "ans "+a1+"   "+arr+"   "+"   "+a3);
                                PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

                                NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(MainActivity.this, "mychannel.Apps");
                                Notification n = nBuilder
                                        .setSmallIcon(R.drawable.ic_baseline_assignment_turned_in_24)
                                        .setContentTitle("Aptitude Test - Click here to check result")
                                        .setWhen(System.currentTimeMillis())
                                        .setContentIntent(pi)
                                        .setAutoCancel(true)
                                        .build();
                                notificationManager.notify(11, n);
                                Log.d("VR", "notification Created");
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_SHORT).show();
                                arr.clear();
                                ques--;
                            }
                        })
                        .create();
                dialog.show();


            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void createChannel(NotificationManager mgr){
        NotificationChannel appsChannel = new NotificationChannel("mychannel.Apps", "Apps", NotificationManager.IMPORTANCE_DEFAULT);
        appsChannel.setLightColor(Color.GRAY);
        mgr.createNotificationChannel(appsChannel);
    }

    public void showDailog(){

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.input);

        Button ok, cancel;
        EditText input=dialog.findViewById(R.id.input);
        ok=dialog.findViewById(R.id.ok_click);
        cancel = dialog.findViewById(R.id.cancel_click);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_ans = input.getText().toString();ques++;
                if(text_ans.equalsIgnoreCase("3")){
                    a3=1;
                }
                pb.setProgress(pb.getProgress()+33);
                Log.d(String.valueOf(this), "Answer   :"+text_ans);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}