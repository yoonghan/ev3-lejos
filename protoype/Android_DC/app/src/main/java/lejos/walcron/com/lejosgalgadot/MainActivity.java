package lejos.walcron.com.lejosgalgadot;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * CHANGE TO THE CORRECT DEVICE NAME of the Lego brick.
     * The device name is set in Lejos, Device Settings.
     * This device name can be obtained when the phone has already connected to the Lego brick before. Using the device name specified on the phone, rename DEVICE_NAME variable.
     **/
    private static String DEVICE_NAME = "lejoshan";

    private final static String TAG = "MainActivity";
    private BluetoothAdapter mBluetoothAdapter;
    private static BluetoothDevice mDevice;
    private Button mSendBN;

    private static String MY_UUID = "00001101-0000-1000-8000-00805f9b34fb";
    private static BluetoothSocket mSocket = null;
    private static String mMessage = "Stop";
    private static PrintStream sender;


    private void findBrick() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "Finding bonded devices");
        for (BluetoothDevice device : pairedDevices) {
            Log.d(TAG, "device:"+device.getName());
            if (device.getName().equals(DEVICE_NAME)) {
                this.mDevice = device;
                Log.d(TAG, "Found" + device.getAddress());
                break;
            }
        }
    }

    private void initBluetooth() {
        Log.d(TAG, "Checking Bluetooth...");
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "Device does not support Bluetooth");
            mSendBN.setClickable(false);
        } else {
            Log.d(TAG, "Bluetooth supported");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mSendBN.setClickable(false);
            Log.d(TAG, "Bluetooth not enabled");
        } else {
            Log.d(TAG, "Bluetooth enabled");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "SpeechRecognizer gestartet", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        mSendBN = (Button) findViewById(R.id.lassoOfTruth);
        mSendBN.setOnClickListener(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initBluetooth();
        findBrick();

        if (mDevice == null) {
            mSendBN.setClickable(false);
            Toast.makeText(this, "No Devices found or BT disabled", Toast.LENGTH_SHORT).show();
            Log.d("onC", "Connected to " + mDevice);
        }
        else {

            try {
                createSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        startService();

    }

    private void startService() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH_ADMIN)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.BLUETOOTH)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BLUETOOTH},
                        1);
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.BLUETOOTH_ADMIN)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                        1);
            }
        }
        else {
            startService(this.getIntent());
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startService();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    public void onClick(View view) {
        try {
            OutputStream os = mSocket.getOutputStream();
            sender = new PrintStream(os);
            Log.d("onSend", "Message = " +  mMessage);
            sender.println(mMessage);
            sender.flush();
            Log.d("onSend", "Message sent");
            mSocket.close();
            Log.d("onSend", "Socket closed");
        } catch (IllegalStateException | NullPointerException | IOException e) {
            e.printStackTrace();
        }

    }

    public void createSocket() throws IOException {
        try {
            UUID uuid = UUID.fromString(MY_UUID);
            mSocket = mDevice.createInsecureRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("createSocket", "Adapter");

        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        mSocket.connect();
        OutputStream os = mSocket.getOutputStream();
        sender = new PrintStream(os);

        Log.d("createSocket", "Standby, " + "Socket: " + mSocket + " Sender: " + sender + " OutputStream: " + os + " mDevice: " + mDevice.getName());
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "App destroyed");
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("onDestroy", "App fully destroyed");
    }
}
