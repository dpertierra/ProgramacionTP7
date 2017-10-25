package com.example.a42025177.tp7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {
    CCGLSurfaceView vistaprincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        vistaprincipal = new CCGLSurfaceView(this);
        setContentView(vistaprincipal);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Game Ogame = new Game(vistaprincipal);
        Ogame.startgame();

    }
}
