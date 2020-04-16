package mx.itesm.equipo05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import mx.itesm.equipo05.Object;

class Personaje extends Object {
    private Animation animacion;
    private float timerAnimacion; //frames en tiempos definidos
    private float x, y;
    private float sizex=32, sizey=64;
    private float width, height, knobPercentY;
    private EstadoMovimiento estado = EstadoMovimiento.QUIETO;
    private final int MAX_VEL = 2;

    public float getX(){return x;}
    public float getY(){return y;}

    public void setMovimiento(EstadoMovimiento estado){
        this.estado = estado;
    }

    public float getSizex(){
        return sizex;
    }

    public float getSizey(){
        return sizey;
    }

    public boolean getReady(){
        return !(x<(width/2));
    }

    public EstadoMovimiento getMovimiento(){
        return estado;
    }

    public Personaje(Texture textura, float x, float y, float width, float height){
        super(textura,x,y);
        this.width = width;
        this.height = height;
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturaPersonaje = region.split((int)sizex,(int)sizey);
        animacion = new Animation( 0.15f, texturaPersonaje[0][3],texturaPersonaje[0][2], texturaPersonaje[0][1]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(x,y);
        this.x= x;
        this.y= y;
    }

    public void render(SpriteBatch batch){
        if (estado == EstadoMovimiento.QUIETO){
            sprite.draw(batch);
        }else{
            if(x<(width/2)) this.x += MAX_VEL;
            this.y += 5*knobPercentY;
            timerAnimacion+= Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion)animacion.getKeyFrame(timerAnimacion);
            batch.draw(region, x, y);
        }
    }
    public void render(SpriteBatch batch, boolean test ){
        float x = sprite.getX();
        float y = sprite.getY();
        sprite.setPosition(width/2, this.y);
        sprite.draw(batch);
        sprite.setPosition(x, y);
    }

    public void setTouchPad(float knobPercentY) {
        this.knobPercentY = knobPercentY;

    }

    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.sizex, this.sizey);
    }

    public enum  EstadoMovimiento {
        QUIETO,
        CAMINANDO
    }
}
