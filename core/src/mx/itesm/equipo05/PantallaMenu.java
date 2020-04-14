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
        Texture texturaBtnJugar = new Texture("button_xd.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));

        Texture texturaBtnAD = new Texture("button_xd.png");
        TextureRegionDrawable trdAD = new TextureRegionDrawable(new TextureRegion(texturaBtnAD));

        Texture texturaBtnO = new Texture("button_xd.png");
        TextureRegionDrawable trdO = new TextureRegionDrawable(new TextureRegion(texturaBtnO));


        //img btn presionado

        Texture texturaBtnJugarP = new Texture("btnp.png");
        TextureRegionDrawable trdJugarP = new TextureRegionDrawable(new TextureRegion(texturaBtnJugarP));
        ImageButton btnJugar = new ImageButton(trdJugar,trdJugarP);
        btnJugar.setPosition(ANCHO/2-btnJugar.getWidth()/2,8*ALTO/10);

        Texture texturaBtnADP = new Texture("btnp.png");
        TextureRegionDrawable trdADP = new TextureRegionDrawable(new TextureRegion(texturaBtnADP));
        ImageButton btnAD = new ImageButton(trdAD,trdADP);
        btnAD.setPosition(ANCHO/2-btnAD.getWidth()/2,5*ALTO/10);

        Texture texturaBtnOP = new Texture("btnp.png");
        TextureRegionDrawable trdOP = new TextureRegionDrawable(new TextureRegion(texturaBtnOP));
        ImageButton btnO = new ImageButton(trdO,trdOP);
        btnO.setPosition(ANCHO/2-btnO.getWidth()/2,2*ALTO/10);

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
                juego.setScreen(new PantallaOpciones(juego));

            }
        });
        btnO.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDe(juego));

            }
        });

        escenaMenu.addActor(btnJugar);
        escenaMenu.addActor(btnAD);
        escenaMenu.addActor(btnO);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        textoTitulo = new Texto("fuenteTecno.fnt");
        textoTitulo.render(batch, "Ultimate Scroller", ANCHO/2, ALTO/2);
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
