package mx.itesm.equipo05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaJuego extends Pantalla {
    private final Juego juego;
    // MAPA
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;
    //AUDIO
    private Music audioFondo; //Largo
    private Sound efecto;  // cortos
    //personaje
    private Personaje mario;
    //hud joystick
    private Stage escenaHUD; //controles
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;

    public PantallaJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarMapa();
        cargarMario();
        crearHUD();
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());
        Gdx.input.setInputProcessor(escenaHUD);
    }

    private void crearHUD() {
        camaraHUD = new OrthographicCamera(ANCHO, ALTO);
        camaraHUD.position.set(ANCHO/2, ALTO/2,0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO,ALTO,camaraHUD);
        Skin skin = new Skin();
        skin.add("fondo", new Texture("Joystick.png"));
        skin.add("boton", new Texture("SmallHandle.png"));
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");
        //crear pad
        Touchpad pad = new Touchpad(64,estilo);
        pad.setBounds(16,16,256,256);
        pad.setColor(1,1,1,0.8f);
        //eventos
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                if (pad.getKnobPercentX()>0 || pad.getKnobPercentX()<0){
                    mario.setEstado(Personaje.EstadoMovimiento.CAMINANDO);
                }else{
                    mario.setEstado(Personaje.EstadoMovimiento.QUIETO);
                }
            }
        });
        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
    }

    private void cargarMario() {
        Texture texturaMario = new Texture("marioSprite.png");
        mario = new Personaje(texturaMario,100,64);

    }

    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapaTutorial.tmx", TiledMap.class);
        manager.load("musicaNueva.mp3",Music.class);
        manager.load("moneda.mp3", Sound.class);
        manager.finishLoading(); //carga
        mapa = manager.get("mapaTutorial.tmx");
        rendererMapa = new OrthogonalTiledMapRenderer(mapa);
        audioFondo = manager.get("musicaNueva.mp3");
        audioFondo.setLooping(true); //infinita
        audioFondo.play();
        //efectos
        efecto = manager.get("moneda.mp3");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(.11f,.42f,.60f);
        batch.setProjectionMatrix(camara.combined);
        rendererMapa.setView(camara);
        rendererMapa.render();
        batch.begin();
        mario.render(batch);
        batch.end();
        //HUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private class ProcesadorEntrada implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            efecto.play();
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}