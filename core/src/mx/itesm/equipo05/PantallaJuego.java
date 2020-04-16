package mx.itesm.equipo05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

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
    private OrthographicCamera camaraHUD, scrollingCamera;
    private Viewport vistaHUD;
    private TiledMapTileLayer  collisionObjectLayer;
    private MapObjects objects;
    private boolean pause = false;
    private boolean pauseHelper = true;
    private ImageButton btnReturn;

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
        scrollingCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scrollingCamera.translate(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        scrollingCamera.update();
        vistaHUD = new ExtendViewport(ANCHO,ALTO,camaraHUD);
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
                mario.setTouchPad(pad.getKnobPercentY());
            }
        });
        escenaHUD = new Stage(vistaHUD);
        Texture texturaBtnReturn = new Texture("btnAtras.png");
        TextureRegionDrawable trdReturn = new TextureRegionDrawable(new TextureRegion(texturaBtnReturn));

        //img btn presionado

        Texture texturaBtnReturnP = new Texture("btnAtrasPresionado.png");
        TextureRegionDrawable trdReturnP = new TextureRegionDrawable(new TextureRegion(texturaBtnReturnP));
        btnReturn = new ImageButton(trdReturn,trdReturnP);
        btnReturn.setPosition(ANCHO/2-btnReturn.getWidth()/2,ALTO/2);
        btnReturn.setVisible(false);
        //listener
        btnReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaHUD.addActor(btnReturn);
        escenaHUD.addActor(pad);
    }

    private void cargarMario() {
        Texture texturaMario = new Texture("marioSprite.png");
        mario = new Personaje(texturaMario,100,64, ANCHO, ALTO);
    }

    private void cargarMapa() {
        int objectLayerId = 2;
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapaTutorial.tmx", TiledMap.class);
        manager.load("musicaNueva.mp3", Music.class);
        manager.load("moneda.mp3", Sound.class);
        manager.finishLoading(); //carga
        mapa = manager.get("mapaTutorial.tmx");
        rendererMapa = new OrthogonalTiledMapRenderer(mapa);
        audioFondo = manager.get("musicaNueva.mp3");
        audioFondo.setLooping(true); //infinita
        if(on_off) {
            audioFondo.play();
        }
        //efectos
        efecto = manager.get("moneda.mp3");

        objects = mapa.getLayers().get(objectLayerId).getObjects();
    }

    @Override
    public void render(float delta) {
        btnReturn.setVisible(false);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            // Use a helper so that a held-down button does not continuously switch between states with every tick
            if (pauseHelper) {
                pause = !pause;
                pauseHelper = false;
            }
        }
        else {
            pauseHelper = true;
        }
        if(!pause) {
            borrarPantalla(.11f, .42f, .60f);
            batch.setProjectionMatrix(camara.combined);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || mario.getMovimiento() == Personaje.EstadoMovimiento.CAMINANDO) {

                mario.setMovimiento(Personaje.EstadoMovimiento.CAMINANDO);
                if (mario.getReady()) {
                    scrollingCamera.translate(2, 0);
                    scrollingCamera.update();
                }
            }
            rendererMapa.render();
            //if(scrollingCamera.position.x==326.0){                Este lo pones para hacer el de error y tienes que poner el de abajo en comentario, chocas con el tronco del principio y tienes que cmabiar en la clase FinDelJuego el texto a que digas que ganaste
               // juego.setScreen(new FinDelJuego(juego));
            //}
            if(scrollingCamera.position.x==3872.0){
                juego.setScreen(new FinDelJuego(juego));
            }
            System.out.println(scrollingCamera.position.x);
            rendererMapa.setView(scrollingCamera);
            System.out.println("------------------------------------");
            Rectangle playerRect = new Rectangle(scrollingCamera.position.x - ANCHO / 4 + mario.getX(), mario.getY(), mario.getSizex(), mario.getSizey());
            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(rectangle, playerRect)) {
                    System.out.println("choque");
                }
            }

            batch.begin();


            mario.render(batch);
            batch.end();
            //HUD
            batch.setProjectionMatrix(camaraHUD.combined);
            escenaHUD.draw();
        } else {
            btnReturn.setVisible(true);
            borrarPantalla(.11f, .42f, .60f);
            batch.setProjectionMatrix(camara.combined);
            scrollingCamera.update();
            camara.update();
            rendererMapa.render();
            rendererMapa.setView(scrollingCamera);
            batch.begin();
            mario.render(batch, true);
            batch.end();
            batch.setProjectionMatrix(camaraHUD.combined);
            escenaHUD.draw();
        }
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