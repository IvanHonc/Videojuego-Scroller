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

    private final MyGdxGame juego;
    private Texture texturaFondo;

    private Stage escenaMenu;

    public PantallaMenu(MyGdxGame juego) {
        this.juego = juego;
    }


    @Override
    public void show() {
        texturaFondo = new Texture("fondoSpace.jpg");
        crearMenu();
    }

    private void crearMenu(){
        escenaMenu = new Stage(vista);

        //boton solito
        Texture texturaBtnJugar = new Texture("btnJugar.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));

        //boton presionado
        Texture texturaBtnJugarP = new Texture("btnJugarP.png");
        TextureRegionDrawable trdJugarP = new TextureRegionDrawable(new TextureRegion(texturaBtnJugarP));


        ImageButton btnJugar = new ImageButton(trdJugar, trdJugarP);


        btnJugar.setPosition(ANCHO/2-btnJugar.getWidth()/2, 2*ALTO/3);

        //Listener
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
//				juego.setScreen(new PantallaSpaceInvaders(juego));
//              juego.setScreen(new PantallaMario(juego));
            }
        });

        escenaMenu.addActor(btnJugar);

        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
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
