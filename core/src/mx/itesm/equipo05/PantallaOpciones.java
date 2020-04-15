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

    private Stage escenaOpc;
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
        escenaOpc=new Stage(vista);
        Texture texturaBtnReturn = new Texture("btnAtras.png");
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(texturaBtnReturn));

        //img btn presionado

        Texture texturaBtnReturnP = new Texture("btnAtrasPresionado.png");
        TextureRegionDrawable trdReturnP = new TextureRegionDrawable(new TextureRegion(texturaBtnReturnP));
        ImageButton btnReturn = new ImageButton(trdReturn,trdReturnP);
        btnReturn.setPosition(ANCHO/2-btnReturn.getWidth()/2,2*ALTO/10);

        //listener
        btnReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        escenaOpc.addActor(btnReturn);
        Gdx.input.setInputProcessor(escenaOpc);
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
        escenaOpc.draw();
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
