package mx.itesm.equipo05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaAcercaDe extends Pantalla{

    private final Juego juego;
    private Texture texturaFondo;

    private Stage escenaAD;

    public PantallaAcercaDe(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("fondoSpace.jpg");
        crear();
    }

    private void crear() {
        escenaAD = new Stage(vista);
        Texture texturaBtnJugar = new Texture("button_xd.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));

        //img btn presionado

        Texture texturaBtnJugarP = new Texture("btnp.png");
        TextureRegionDrawable trdJugarP = new TextureRegionDrawable(new TextureRegion(texturaBtnJugarP));
        ImageButton btnJugar = new ImageButton(trdJugar,trdJugarP);
        btnJugar.setPosition(ANCHO/2-btnJugar.getWidth()/2,2*ALTO/3);

        //listener
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //juego.setScreen(new PantallaSpaceInvaders(juego));
                //juego.setScreen(new PantallaMario(juego));
            }
        });

        escenaAD.addActor(btnJugar);
        Gdx.input.setInputProcessor(escenaAD);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
