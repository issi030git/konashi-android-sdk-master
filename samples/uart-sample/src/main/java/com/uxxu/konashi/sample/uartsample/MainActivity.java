package com.uxxu.konashi.sample.uartsample;

import android.bluetooth.BluetoothGattCharacteristic;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.uxxu.konashi.lib.Konashi;
import com.uxxu.konashi.lib.KonashiListener;
import com.uxxu.konashi.lib.KonashiManager;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import info.izumin.android.bletia.BletiaException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final MainActivity self = this;

    private KonashiManager mKonashiManager;

    private EditText mSendEdit;
    private TextView mResultText;
    private TextView mResultText2;
    private TextView mResultText3;
    private TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i = 0;int b =0;int c = 0;int d = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_find).setOnClickListener(this);

        findViewById(R.id.s).setOnClickListener(this);
        findViewById(R.id.c).setOnClickListener(this);
        findViewById(R.id.e).setOnClickListener(this);
        findViewById(R.id.n).setOnClickListener(this);
        findViewById(R.id.p).setOnClickListener(this);

        tl = (TableLayout) findViewById(R.id.table);
        mSendEdit = (EditText) findViewById(R.id.edit_send);
        mResultText = (TextView) findViewById(R.id.text_read);
        mResultText2 = (TextView) findViewById(R.id.text_read2);
        mResultText3 = (TextView) findViewById(R.id.gesture);

        mKonashiManager = new KonashiManager(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mKonashiManager.addListener(mKonashiListener);
        refreshViews();
    }

    @Override
    protected void onPause() {
        mKonashiManager.removeListener(mKonashiListener);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mKonashiManager.isConnected()){
                    mKonashiManager.reset()
                            .then(new DoneCallback<BluetoothGattCharacteristic>() {
                                @Override
                                public void onDone(BluetoothGattCharacteristic result) {
                                    mKonashiManager.disconnect();
                                }
                            });
                }
            }
        }).start();
        super.onDestroy();
    }

    private void refreshViews() {
        boolean isReady = mKonashiManager.isReady();
        findViewById(R.id.btn_find).setVisibility(!isReady ? View.VISIBLE : View.GONE);
        findViewById(R.id.btn_send).setVisibility(isReady ? View.VISIBLE : View.GONE);
        mSendEdit.setVisibility(isReady ? View.VISIBLE : View.GONE);
        mResultText.setVisibility(isReady ? View.VISIBLE : View.GONE);
        mResultText2.setVisibility(isReady ? View.VISIBLE : View.GONE);
        mResultText3.setVisibility(isReady ? View.VISIBLE : View.GONE);
        tl.setVisibility(isReady ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_find:
                mKonashiManager.find(this);
                break;
            case R.id.btn_send:
                mKonashiManager.uartWrite(mSendEdit.getText().toString().getBytes())
                .fail(new FailCallback<BletiaException>() {
                    @Override
                    public void onFail(BletiaException result) {
                        Toast.makeText(self, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.s:
                mKonashiManager.uartWrite("s".getBytes())
                        .fail(new FailCallback<BletiaException>() {
                            @Override
                            public void onFail(BletiaException result) {
                                Toast.makeText(self, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case R.id.c:
                mKonashiManager.uartWrite("c".getBytes())
                        .fail(new FailCallback<BletiaException>() {
                            @Override
                            public void onFail(BletiaException result) {
                                Toast.makeText(self, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case R.id.e:
                mKonashiManager.uartWrite("e".getBytes())
                        .fail(new FailCallback<BletiaException>() {
                            @Override
                            public void onFail(BletiaException result) {
                                Toast.makeText(self, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case R.id.n:
                mKonashiManager.uartWrite("n".getBytes())
                        .fail(new FailCallback<BletiaException>() {
                            @Override
                            public void onFail(BletiaException result) {
                                Toast.makeText(self, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case R.id.p:
                mKonashiManager.uartWrite("p".getBytes())
                        .fail(new FailCallback<BletiaException>() {
                            @Override
                            public void onFail(BletiaException result) {
                                Toast.makeText(self, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case R.id.r:
                mKonashiManager.uartWrite("r".getBytes())
                        .fail(new FailCallback<BletiaException>() {
                            @Override
                            public void onFail(BletiaException result) {
                                Toast.makeText(self, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }

    private final KonashiListener mKonashiListener = new KonashiListener() {
        @Override
        public void onConnect(KonashiManager manager) {
            refreshViews();
            mKonashiManager.uartMode(Konashi.UART_ENABLE)
            .then(new DoneCallback<BluetoothGattCharacteristic>() {
                @Override
                public void onDone(BluetoothGattCharacteristic result) {
                    mKonashiManager.uartBaudrate(Konashi.UART_RATE_115K2);
                }
            })
            .fail(new FailCallback<BletiaException>() {
                @Override
                public void onFail(BletiaException result) {
                    Toast.makeText(self, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onDisconnect(KonashiManager manager) {
            refreshViews();
        }

        @Override
        public void onError(KonashiManager manager, BletiaException e) {

        }

        @Override
        public void onUpdatePioOutput(KonashiManager manager, int value) {

        }

        @Override
        public void onUpdateUartRx(KonashiManager manager, byte[] value) {
            mResultText.setText(byteArrayToString(value));
            mResultText2.setText(new String(value));
            mResultText3.setText(byteToGesture(value[0]));
        }

        @Override
        public void onUpdateBatteryLevel(KonashiManager manager, int level) {

        }
    };

    String[] gestures = {"nd","sh","ti"};
    private String byteToGesture(byte b){
        if(b == 0x20)return "";

        int kind = b & 0x03;
        int strength = b & 0x3C;//４bit取り出す
        strength >>>= 2;
        return String.format("%s %2d",gestures[kind],strength);
    }

    // Byte配列を16進文字列に変換して返す
    private String byteArrayToString(byte[] by) {
        StringBuffer sb = new StringBuffer();
        for (int b : by) {
            sb.append(Character.forDigit(b >> 4 & 0xF, 16));
            sb.append(Character.forDigit(b & 0xF, 16));
            sb.append(" ");
        }
        return sb.toString();
    }

}