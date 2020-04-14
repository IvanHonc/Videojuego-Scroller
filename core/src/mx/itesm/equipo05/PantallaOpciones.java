package mx.itesm.equipo05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaOpciones extends Pantalla{

    private Stage escenaO;
    private final Juego juego;
    private Texture texturaFondo;

    private Texto textoTitulo;

    public PantallaOpciones(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("fondoSpace.jpg");
        crear();
    }

    private void crear() {
        escenaO=new Stage(vista);
        Texture texturaBtnJugar = new Texture("button_xd.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));

        //img btn presionado

        Texture texturaBtnJugarP = new Texture("btnp.png");
        TextureRegionDrawable trdJugarP = new TextureRegionDrawable(new TextureRegion(texturaBtnJugarP));
        ImageButton btnJugar = new ImageButton(trdJugar,trdJugarP);
        btnJugar.setPosition(ANCHO/2-btnJugar.getWidth()/2,2*ALTO/10);

        //listener
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
                //juego.setScreen(new PantallaSpaceInvaders(juego));
                //juego.setScreen(new PantallaMario(juego));
            }
        });
        escenaO.addActor(btnJugar);
        Gdx.input.setInputProcessor(escenaO);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //Fondo
        batch.draw(texturaFondo, 0, 0);
        //Texto
        textoTitulo = new Texto("fuenteTecno.fnt");
        textoTitulo.render(batch, "Opciones", ANCHO/2, ALTO-ALTO/12);
        batch.end();
        escenaO.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
    }
}
