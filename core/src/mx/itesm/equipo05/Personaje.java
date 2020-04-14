package mx.itesm.equipo05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.itesm.equipo05.Object;

class Personaje extends Object {
    private Animation animacion;
    private float timerAnimacion; //frames en tiempos definidos
    private float x, y;
    private EstadoMovimiento estado = EstadoMovimiento.QUIETO;
    public Personaje(Texture textura, float x, float y){
        super(textura,x,y);
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturaPersonaje = region.split(32,64);
        animacion = new Animation( 0.15f, texturaPersonaje[0][3],texturaPersonaje[0][2], texturaPersonaje[0][1]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(x,y);
        this.x= x;
        this.y= y;
    }
    public void setEstado(EstadoMovimiento estado){
        this.estado  = estado;
    }
    public void render(SpriteBatch batch){
        if (estado == EstadoMovimiento.QUIETO){
            sprite.draw(batch);
        }else{
            timerAnimacion+= Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion)animacion.getKeyFrame(timerAnimacion);
            batch.draw(region, x, y);
        }
    }

    public enum  EstadoMovimiento {
        QUIETO,
        CAMINANDO
    }
}
