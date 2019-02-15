package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import game_manager.GameManager;

import static game_manager.GameManager.ComputerScience;

public class MenuScreen extends BaseScreen{
    private SpriteBatch batch = new SpriteBatch();

    private Texture menuBackground = new Texture("menuBackground.png");
    private BitmapFont titleFont = new BitmapFont(); //Sets titleFont to Libgdx default font
    private Color titleColor = new Color(226F/255F, 223F/255F,164F/255F, 1);

    private Image background = new Image(menuBackground);
    private Label titleText;

    //Menu buttons, their font, style and texture
    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle myTextButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
    private Skin skin = new Skin();
    private TextButton startGame;
    private TextButton exitGame;

    public MenuScreen(GameManager game){
        super(game);
    }

    @Override
    public void update(float delta){ }

    @Override
    public void show() {
        titleFont.setColor(titleColor);
        titleFont.getData().setScale(4);

        //Adds textures to the Skin, sets Skin for Button Up and Down
        skin.addRegions(buttonAtlas);
        myTextButtonStyle.font = buttonFont;
        myTextButtonStyle.up = skin.getDrawable("buttonUp");
        myTextButtonStyle.down = skin.getDrawable("buttonDown");

        //Draws Menu Title and Background
        mainStage.addActor(background);
        this.background.setSize(viewwidth, viewheight);

        // This label is no longer needed as the title is now built into the background image.
        // this.titleText = new Label("SEPR GAME", new Label.LabelStyle(titleFont, titleColor));
        // this.titleText.setPosition(viewwidth/2f - 160, (viewheight*900)/1024);
        // stage.addActor(this.titleText);

        musicSetup("the-buccaneers-haul.mp3", true);

        /**
         * Creates Text buttons for the menu, Sets them up and Adds listeners to switch to correct screen
         */
        startGame = new TextButton("Start Game", myTextButtonStyle);
        exitGame = new TextButton("Exit Game", myTextButtonStyle);

        /**
        stage.addActor(runCombat);
        runCombat.setPosition(viewwidth/2f - 175, (viewheight*600)/1024);
        runCombat.setTransform(true); //Allows the Button to be Scaled
        runCombat.setScale(3);
        runCombat.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                changeScreen(new CombatScreen(game,false));
                return true;
            }
        });
         */

        mainStage.addActor(startGame);
        startGame.setPosition(viewwidth/2f - 175, (viewheight*480)/1024);
        startGame.setTransform(true); //Allows the Button to be Scaled
        startGame.setScale(3);
        startGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
	            Gdx.app.debug("Menu DEBUG", "Start button pressed");
            	changeScreen(new CombatScreen(game,true));
                return true;
            }
        });

        mainStage.addActor(exitGame);
        exitGame.setPosition(viewwidth/2f - 175, (viewheight*360)/1024);
        exitGame.setTransform(true); //Allows the Button to be Scaled
        exitGame.setScale(3);
        exitGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.debug("Menu DEBUG", "Exit button pressed");
                changeScreen(new DepartmentScreen(game, ComputerScience));
                return true;
            }
        });

        /**
        stage.addActor(exitGame);
        exitGame.setPosition(viewwidth/2f - 175, (viewheight*240)/1024);
        exitGame.setTransform(true); //Allows the Button to be Scaled
        exitGame.setScale(3);
        exitGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.exit(0);
                return true;
            }
        });
         */
    }

    @Override
    public void render(float delta) { super.render(delta); }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
        super.dispose();
        menuBackground.dispose();
        titleFont.dispose();
        buttonFont.dispose();
        skin.dispose();
        buttonAtlas.dispose();
        batch.dispose();
    }
}
