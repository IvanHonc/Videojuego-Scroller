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

    private Stage escenaAD;
    private final Juego juego;
    private Texture texturaFondo;

    private Texto textoTitulo;
    private Texto textoDescripcion1;
    private Texto textoDescripcion2;
    private Texto textoDescripcion3;
    private Texto textoDescripcion4;
    private String descripcion1 = "Desarrolladores:";
    private String descripcion2 = "Ivan Honc, Bruno Vazquez, Jesus Alcala y Emiliano Heredia";
    private String descripcion3 = "Desarrollado para la clase de desarrollo de videojuegos.";
    private String descripcion4 = "Semestre Febrero-Junio 2020";

    public PantallaAcercaDe(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("fondoSpace.jpg");
        crear();
    }

    private void crear() {
        escenaAD=new Stage(vista);
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
        escenaAD.addActor(btnReturn);
        Gdx.input.setInputProcessor(escenaAD);
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
        textoTitulo.render(batch, "Acerca De", ANCHO/2, ALTO-ALTO/12);
        textoDescripcion1 = new Texto("fuenteTecnoChica.fnt");
        textoDescripcion1.render(batch, descripcion1, ANCHO/2, ALTO-ALTO/4);
        textoDescripcion2 = new Texto("fuenteTecnoChica.fnt");
        textoDescripcion2.render(batch, descripcion2, ANCHO/2, ALTO-5*ALTO/16);
        textoDescripcion3 = new Texto("fuenteTecnoChica.fnt");
        textoDescripcion3.render(batch, descripcion3, ANCHO/2, ALTO-7*ALTO/16);
        textoDescripcion4 = new Texto("fuenteTecnoChica.fnt");
        textoDescripcion4.render(batch, descripcion4, ANCHO/2, ALTO-9*ALTO/16);
        batch.end();
        escenaAD.draw();
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
