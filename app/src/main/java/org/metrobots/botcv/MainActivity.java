package org.metrobots.botcv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.widget.Toast;

import net.sf.lipermi.net.Server;

import org.metrobots.botcv.communication.CommImpl;
import org.metrobots.botcv.communication.CommInterface;
import org.metrobots.botcv.communication.CommServer;
import org.metrobots.botcv.cv.BotCameraView;
import org.metrobots.botcv.cv.CameraImpl;
import org.metrobots.botcv.cv.LimiterSlider;
import org.metrobots.botcv.peripheral.PeripheralManager;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    @SuppressWarnings("FieldCanBeLocal")
    private BotCameraView cameraView;
    private LimiterSlider limiterSlider = new LimiterSlider();
    private CameraImpl cameraImpl = new CameraImpl();
    private PeripheralManager peripheralManager;

    static {
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        peripheralManager = new PeripheralManager(this);

        cameraView = (BotCameraView) findViewById(R.id.cameraView);
        cameraView.setVisibility(SurfaceView.VISIBLE);
        cameraView.enableFpsMeter();
        cameraView.isHardwareAccelerated();
        cameraView.setCvCameraViewListener(cameraImpl);
        cameraView.enableView();


        try {
            new CommServer(new CommImpl(this)).start(5800);
            System.out.println("Server started.");
            //Socket sock = new Socket();
            //System.out.println(sock.getRemoteSocketAddress().toString());
        } catch (Exception e) { e.printStackTrace(); }
    }


    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public PeripheralManager getPeripheralManager() {
        return peripheralManager;
    }

    public CameraImpl getCameraImpl() {
        return cameraImpl;
    }
}