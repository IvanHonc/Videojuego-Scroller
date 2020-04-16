package mx.itesm.equipo05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaMenu extends Pantalla{

    private final Juego juego;
    private Texture texturaFondo;
    private Stage escenaMenu;
    private Texto textoTitulo;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }


    @Override
    public void show() {
        texturaFondo = new Texture("fondoSpace.jpg");
        crearMenu();
    }

    private void crearMenu(){
        escenaMenu = new Stage(vista);
        Texture texturaBtnJugar = new Texture("btnJugar.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));

        Texture texturaBtnAD = new Texture("btnAcercaDe.png");
        TextureRegionDrawable trdAD = new TextureRegionDrawable(new TextureRegion(texturaBtnAD));

        Texture texturaBtnO = new Texture("btnOpciones.png");
        TextureRegionDrawable trdO = new TextureRegionDrawable(new TextureRegion(texturaBtnO));


        //img btn presionado

        Texture texturaBtnJugarP = new Texture("btnJugarPresionado.png");
        TextureRegionDrawable trdJugarP = new TextureRegionDrawable(new TextureRegion(texturaBtnJugarP));
        ImageButton btnJugar = new ImageButton(trdJugar,trdJugarP);
        btnJugar.setPosition(ANCHO/2-btnJugar.getWidth()/2,6*ALTO/10);

        Texture texturaBtnADP = new Texture("btnAcercaDePresionado.png");
        TextureRegionDrawable trdADP = new TextureRegionDrawable(new TextureRegion(texturaBtnADP));
        ImageButton btnAD = new ImageButton(trdAD,trdADP);
        btnAD.setPosition(ANCHO/2-btnAD.getWidth()/2,2*ALTO/20);

        Texture texturaBtnOpcP = new Texture("btnOpcionesPresionado.png");
        TextureRegionDrawable trdOpcP = new TextureRegionDrawable(new TextureRegion(texturaBtnOpcP));
        ImageButton btnOpc = new ImageButton(trdO,trdOpcP);
        btnOpc.setPosition(ANCHO/2-btnOpc.getWidth()/2,7*ALTO/20);

        //listener
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaJuego(juego));

            }
        });
        btnAD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDe(juego));

            }
        });
        btnOpc.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaOpciones(juego));

            }
        });

        escenaMenu.addActor(btnJugar);
        escenaMenu.addActor(btnAD);
        escenaMenu.addActor(btnOpc);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        textoTitulo = new Texto("fuenteTecno.fnt");
        textoTitulo.render(batch, "Bullet Time Reloaded", ANCHO/2, ALTO-ALTO/12);
        batch.end();
        escenaMenu.draw();

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
