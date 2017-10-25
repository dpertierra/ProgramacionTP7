package com.example.a42025177.tp7;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import org.cocos2d.actions.instant.CallFuncN;
import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

/**
 * Created by 42025177 on 10/8/2017.
 */

public class Game {
    CCGLSurfaceView _VistaJuego;
    CCSize PantallaDispositivo;
    Sprite sprite;
    Sprite sprite2;
    boolean rojo = false;
    boolean verde = false;

    public Game(CCGLSurfaceView Vistadeljuego)
    {
        _VistaJuego = Vistadeljuego;
    }
    public void startgame()
    {
        Director.sharedDirector().attachInView(_VistaJuego);
        PantallaDispositivo = Director.sharedDirector().displaySize();
        Director.sharedDirector().runWithScene(Gamescene());
        rojo = false;
        verde = false;
    }
    private Scene Gamescene()
    {
        Scene scene = Scene.node();

        Capa C = new Capa();
        scene.addChild(C);

        return scene;

    }
    class Capa extends Layer {
        public Capa()

        {
            creaSprite();
            this.setIsTouchEnabled(true);
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            if (EstaEntre((int)event.getX(),
                    (int)(sprite.getPositionX()-sprite.getWidth()/2),
                    (int)(sprite.getPositionX()+sprite.getWidth()/2)))
                if (EstaEntre((int)(PantallaDispositivo.getHeight()-event.getY()-sprite.getHeight()),
                        (int)(sprite.getPositionY()-sprite.getHeight()/2),
                        (int)(sprite.getPositionY()+sprite.getHeight()/2)))
                    verde = true;

            if (EstaEntre((int)event.getX(),
                    (int)(sprite2.getPositionX()-sprite2.getWidth()/2),
                    (int)(sprite2.getPositionX()+sprite2.getWidth()/2)))
                if (EstaEntre((int)(PantallaDispositivo.getHeight()-event.getY()-sprite2.getHeight()),
                        (int)(sprite2.getPositionY()-sprite2.getHeight()/2),
                        (int)(sprite2.getPositionY()+sprite2.getHeight()/2)))
                    rojo = true;
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            if (verde)
                moverObjeto(sprite, event.getX(), PantallaDispositivo.getHeight() - event.getY() - sprite2.getHeight());
            if (rojo)
                moverObjeto(sprite2, event.getX(), PantallaDispositivo.getHeight() - event.getY() - sprite2.getHeight());

            if (Chocaron(sprite,sprite2)){
                IntervalAction Intervalo = Sequence.actions(MoveBy.action(0.3f, 100f, 0f),
                        MoveBy.action(0.3f, 0f, -100f), MoveBy.action(0.3f, -100f, 0f), MoveBy.action(0.3f, 0f, 100f));
                if (verde) {
                    sprite2.runAction(Intervalo);
                } else {
                    sprite.runAction(Intervalo);
                }
            }
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            rojo = false;
            verde = false;
            return true;
        }

        private void moverObjeto(Sprite sprite, float x, float y){
            sprite.setPosition(x,y);
        }


        public void creaSprite(){
            sprite = Sprite.sprite("cuadradoverde.png");
            sprite2 = Sprite.sprite("cuadradorojo.png");
            Random random = new Random();
            do {

                float ancho = PantallaDispositivo.width - sprite.getWidth();
                float alto = PantallaDispositivo.height - sprite.getHeight();

                float ancho2 = PantallaDispositivo.width - sprite2.getWidth();
                float alto2 = PantallaDispositivo.height - sprite2.getHeight();
                CCPoint posicionInicial = new CCPoint();
                posicionInicial.x = random.nextInt((int)(ancho));
                posicionInicial.y = random.nextInt((int)(alto));;

                posicionInicial.x = posicionInicial.x +(sprite.getWidth()/2);
                posicionInicial.y = posicionInicial.y +(sprite.getHeight()/2);

                sprite.setPosition(posicionInicial.x, posicionInicial.y);

                CCPoint posicionInicial2 = new CCPoint();
                posicionInicial2.x = random.nextInt((int)(ancho2));
                posicionInicial2.y = random.nextInt((int)(alto2));

                posicionInicial2.x = posicionInicial2.x +(sprite2.getWidth()/2);
                posicionInicial2.y = posicionInicial2.y +(sprite2.getHeight()/2);

                sprite2.setPosition(posicionInicial2.x, posicionInicial2.y);

            }while (Chocaron(sprite,sprite2));
            super.addChild(sprite);
            super.addChild(sprite2);
        }

    }

    boolean Chocaron(Sprite spriteA, Sprite spriteB)
    {
        boolean devolver = false;
        int spriteAIzquierda, spriteADerecha, spriteAArriba, spriteAAbajo;
        int spriteBIzquierda, spriteBDerecha, spriteBArriba, spriteBAbajo;
        spriteAIzquierda = (int)(spriteA.getPositionX()-spriteA.getWidth()/2);
        spriteADerecha = (int)(spriteA.getPositionX()+spriteA.getWidth()/2);
        spriteAAbajo = (int)(spriteA.getPositionY()-spriteA.getHeight()/2);
        spriteAArriba = (int)(spriteA.getPositionY()+spriteA.getHeight()/2);

        spriteBIzquierda = (int)(spriteB.getPositionX()-spriteB.getWidth()/2);
        spriteBDerecha = (int)(spriteB.getPositionX()+spriteB.getWidth()/2);
        spriteBAbajo = (int)(spriteB.getPositionY()-spriteB.getHeight()/2);
        spriteBArriba = (int)(spriteB.getPositionY()+spriteB.getHeight()/2);

        if (EstaEntre(spriteAIzquierda,spriteBIzquierda,spriteBDerecha)&& EstaEntre(spriteAAbajo,spriteBAbajo,spriteBArriba))
        {
            devolver = true;
        }
        if (EstaEntre(spriteAIzquierda,spriteBIzquierda,spriteBDerecha)&& EstaEntre(spriteAArriba,spriteBAbajo,spriteBArriba))
        {
            devolver = true;
        }
        if (EstaEntre(spriteADerecha,spriteBIzquierda,spriteBDerecha)&& EstaEntre(spriteAArriba,spriteBAbajo,spriteBArriba))
        {
            devolver = true;
        }
        if (EstaEntre(spriteADerecha,spriteBIzquierda,spriteBDerecha)&& EstaEntre(spriteAAbajo,spriteBAbajo,spriteBArriba))
        {
            devolver = true;
        }
        if (EstaEntre(spriteBIzquierda,spriteAIzquierda,spriteADerecha)&& EstaEntre(spriteBAbajo,spriteAAbajo,spriteAArriba))
        {
            devolver = true;
        }
        if (EstaEntre(spriteBIzquierda,spriteAIzquierda,spriteADerecha)&& EstaEntre(spriteBArriba,spriteAAbajo,spriteAArriba))
        {
            devolver = true;
        }
        if (EstaEntre(spriteBDerecha,spriteAIzquierda,spriteADerecha)&& EstaEntre(spriteBArriba,spriteAAbajo,spriteAArriba))
        {
            devolver = true;
        }
        if (EstaEntre(spriteBDerecha,spriteAIzquierda,spriteADerecha)&& EstaEntre(spriteBAbajo,spriteAAbajo,spriteAArriba))
        {
            devolver = true;
        }
        return devolver;
    }
    boolean EstaEntre(int NumComparar, int NumMenor, int NumMayor)
    {
        boolean Devolver;
        if (NumMenor>NumMayor)
        {
            int auxiliar = NumMayor;
            NumMayor = NumMenor;
            NumMenor = auxiliar;
        }
        if (NumComparar>=NumMenor && NumComparar<=NumMayor) {
            Devolver = true;
        }
        else {Devolver = false;}
        return Devolver;
    }

}
