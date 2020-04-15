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
    private boolean on_off;
    private Texto textoTitulo;

    public PantallaOpciones(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("fondoSpace.jpg");
        on_off=true;
        crear();
    }

    private void crear() {
        escenaOpc=new Stage(vista);

        Texture texturaBtnReturn = new Texture("btnAtras.png");
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(texturaBtnReturn));
        Texture texturaBtnReturnP = new Texture("btnAtrasPresionado.png");
        TextureRegionDrawable trdReturnP = new TextureRegionDrawable(new TextureRegion(texturaBtnReturnP));
        ImageButton btnReturn = new ImageButton(trdReturn,trdReturnP);
        btnReturn.setPosition(ANCHO/2-btnReturn.getWidth()/2,2*ALTO/10);

        Texture texturaBtnMusica = new Texture("btnMusicaSi.png");
        TextureRegionDrawable trdMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnMusica));
        Texture texturaBtnMusicaNo = new Texture("btnMusicaNo.png");
        TextureRegionDrawable trdMusicaNo = new TextureRegionDrawable(new TextureRegion(texturaBtnMusicaNo));
        ImageButton btnMusica;
        if(on_off==true) {
            btnMusica = new ImageButton(trdMusica, trdMusicaNo);
        }else{
            btnMusica = new ImageButton(trdMusicaNo, trdMusica);
        }
        btnMusica.setPosition(ANCHO / 2 - btnMusica.getWidth() / 2, ALTO / 2);
        //listener
        btnReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        btnMusica.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(on_off==true){
                    on_off=false;
                }else{
                    on_off=true;
                }
                crear();
                //Desactivar Música
            }
        });
        escenaOpc.addActor(btnReturn);
        escenaOpc.addActor(btnMusica);
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
