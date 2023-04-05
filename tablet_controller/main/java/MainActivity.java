package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    Button toggle, btnD, btnR, btnLT, btnRT, btnUp,
            btnDown, btnClose, btnOpen;
    public EditText text;
    public static String ipaddress;
    public static String CMD;
    public static int val;
    static GraphView graph;
    public static DataInputStream input;



    public static LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0),
            new DataPoint(1, 0),
            new DataPoint(2, 0),
            new DataPoint(3, 0),
            new DataPoint(4, 0),
    });

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graph = findViewById(R.id.idGraphView);
        text = (EditText) findViewById(R.id.name);
        btnD = (Button) findViewById(R.id.btnD);
        btnR = (Button) findViewById(R.id.btnR);
        btnLT = (Button) findViewById(R.id.btnLT);
        btnRT = (Button) findViewById(R.id.btnRT);
        btnUp = (Button) findViewById(R.id.btnUp);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnClose = (Button) findViewById(R.id.btnClose);
        toggle = findViewById(R.id.switch1);


        graph.addSeries(series);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(11);
        graph.getViewport().setYAxisBoundsManual(true);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipaddress = text.getText().toString();
                CMD = "9";
                AppSocket ap = new AppSocket();
                ap.execute();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MetalD md = new MetalD();
                md.start();
            }
        });

        btnUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        ipaddress = text.getText().toString();
                        CMD = "4";
                        AppSocket drive = new AppSocket();
                        drive.execute();
                        return false;

                    case MotionEvent.ACTION_UP:
                        ipaddress = text.getText().toString();
                        CMD = "8";
                        drive = new AppSocket();
                        drive.execute();
                        return true;
                }
                return false;
            }
        });

        btnDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        ipaddress = text.getText().toString();
                        CMD = "5";
                        AppSocket drive = new AppSocket();
                        drive.execute();
                        return false;

                    case MotionEvent.ACTION_UP:
                        ipaddress = text.getText().toString();
                        CMD = "8";
                        drive = new AppSocket();
                        drive.execute();
                        return true;
                }
                return false;
            }
        });

        btnOpen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        ipaddress = text.getText().toString();
                        CMD = "7";
                        AppSocket drive = new AppSocket();
                        drive.execute();
                        return false;

                    case MotionEvent.ACTION_UP:
                        ipaddress = text.getText().toString();
                        CMD = "8";
                        drive = new AppSocket();
                        drive.execute();
                        return true;
                }
                return false;
            }
        });

        btnClose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        ipaddress = text.getText().toString();
                        CMD = "6";
                        AppSocket drive = new AppSocket();
                        drive.execute();
                        return false;

                    case MotionEvent.ACTION_UP:
                        ipaddress = text.getText().toString();
                        CMD = "8";
                        drive = new AppSocket();
                        drive.execute();
                        return true;
                }
                return false;
            }
        });

        btnD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        ipaddress = text.getText().toString();
                        CMD = "0";
                        AppSocket drive = new AppSocket();
                        drive.execute();
                        return false;

                    case MotionEvent.ACTION_UP:
                        ipaddress = text.getText().toString();
                        CMD = "8";
                        drive = new AppSocket();
                        drive.execute();
                        return true;
                }
                return false;
            }
        });

        btnR.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        ipaddress = text.getText().toString();
                        CMD = "1";
                        AppSocket drive = new AppSocket();
                        drive.execute();
                        return false;

                    case MotionEvent.ACTION_UP:
                        ipaddress = text.getText().toString();
                        CMD = "8";
                        drive = new AppSocket();
                        drive.execute();
                        return true;
                }
                return false;
            }
        });

        btnLT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        ipaddress = text.getText().toString();
                        CMD = "2";
                        AppSocket drive = new AppSocket();
                        drive.execute();
                        return false;

                    case MotionEvent.ACTION_UP:
                        ipaddress = text.getText().toString();
                        CMD = "8";
                        drive = new AppSocket();
                        drive.execute();
                        return true;
                }
                return false;
            }
        });

        btnRT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        ipaddress = text.getText().toString();
                        CMD = "3";
                        AppSocket drive = new AppSocket();
                        drive.execute();
                        return false;

                    case MotionEvent.ACTION_UP:
                        ipaddress = text.getText().toString();
                        CMD = "8";
                        drive = new AppSocket();
                        drive.execute();
                        return true;
                }
                return false;
            }
        });

    }


    public static class AppSocket extends AsyncTask<Void, Void, Void> implements Runnable{

        Socket socket;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket(ipaddress, 65500);
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                output.writeBytes(CMD);
                output.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void run() {
            AppSocket as = new AppSocket();
            as.execute();
        }
    }

    public class AppSock extends Thread{
        Socket socket;
        public void run(){
            try {
                socket = new Socket(ipaddress, 65500);
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                output.writeBytes(CMD);
                output.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class MetalD extends Thread{
        Socket socket2;
        int[] zero = {0,0,0,0,0};

        public void run(){
            try {
                socket2 = new Socket(ipaddress, 65501);
                while (true) {
                    input = new DataInputStream(socket2.getInputStream());
                    zero[0] = zero[1];
                    zero[1] = zero[2];
                    zero[2] = zero[3];
                    zero[3] = zero[4];
                    zero[4] = input.readByte();
                    graph.removeSeries(series);

                    //zero[4] = val;
                    series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(0, zero[0]),
                            new DataPoint(1, zero[1]),
                            new DataPoint(2, zero[2]),
                            new DataPoint(3, zero[3]),
                            new DataPoint(4, zero[4]),
                    });
                    graph.addSeries(series);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

