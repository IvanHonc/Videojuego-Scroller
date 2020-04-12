package mx.itesm.equipo05;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Object {
    protected Sprite sprite;

    public Object(Texture textura, float x, float y){
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }
}
