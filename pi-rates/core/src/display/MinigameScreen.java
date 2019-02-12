package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import combat.ship.RoomFunction;
import game_manager.GameManager;

import java.util.Random;

import static java.lang.Math.round;

public class MinigameScreen extends BaseScreen {

	/**
	 * Used to Draw Assets on the Screen
	 */
	private SpriteBatch batch = new SpriteBatch();
//    private Stage stage = new Stage();

	/**
	 * Booleans used to track if the Game is over and won/lost, plus buttons to display the info
	 */
	private TextButton youWin;
	private TextButton youLose;

	/**
	 * Variables for the minigame.
	 */
	private float mult = 1.5f; // Odds of winning are 1/3 so a 50% return is pretty generous.

	/**
	 * Main style used for buttons
	 */
	private BitmapFont buttonFont = new BitmapFont();
	private TextButton.TextButtonStyle textButtonStyle;

	/**
	 * TextButtons for the three choices.
	 */
	private TextButton pickRock;
	private TextButton pickPaper;
	private TextButton pickScissors;

	/**
	 * Constructor
	 */
	public MinigameScreen(GameManager game){
		super(game);
		TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
		skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = buttonFont;
		textButtonStyle.up = skin.getDrawable("buttonUp");
		textButtonStyle.down = skin.getDrawable("buttonDown");

		Texture backgroundTex = new Texture("battleBackground.png");
		Image backgroundImg = new Image(backgroundTex);
		backgroundImg.setSize(viewwidth, viewheight);
		stage.addActor(backgroundImg);
		setUpTextures();
	}

	@Override
	public void show() {
		musicSetup("the-buccaneers-haul.mp3");
		TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
		skin.addRegions(buttonAtlas);

		/**
		 * Creates Text buttons for the menu, Sets them up and Adds listeners to switch to correct screen
		 */
		pickRock = new TextButton("Rock", textButtonStyle);
		pickPaper = new TextButton("Paper", textButtonStyle);
		pickScissors = new TextButton("Scissors", textButtonStyle);

		stage.addActor(pickRock);
		pickRock.setPosition(viewwidth/2f - 175, (viewheight*700)/1024);
		pickRock.setTransform(true); //Allows the Button to be Scaled
		pickRock.setScale(3);
		pickRock.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				playMinigame(50, 0);
				return true;
			}
		});

		stage.addActor(pickPaper);
		pickPaper.setPosition(viewwidth/2f - 175, (viewheight*580)/1024);
		pickPaper.setTransform(true); //Allows the Button to be Scaled
		pickPaper.setScale(3);
		pickPaper.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				playMinigame(50, 1);
				return true;
			}
		});

		stage.addActor(pickScissors);
		pickScissors.setPosition(viewwidth/2f - 175, (viewheight*460)/1024);
		pickScissors.setTransform(true); //Allows the Button to be Scaled
		pickScissors.setScale(3);
		pickScissors.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				playMinigame(50, 2);
				return true;
			}
		});
	}

	public void setUpTextures(){
		drawEndButtons();
	}

	@Override
	public void update(float delta){
		Gdx.input.setInputProcessor(stage);
		batch.begin();

		batch.end();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
	}

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
		batch.dispose();
	}

	/**
	 * Picks a Random integer
	 */
	private int pickRandom(int max){
		Random rand = new Random();
		return rand.nextInt(max);
	}


	private void addButtonListener(final RoomFunction room, ImageButton button){
		button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//TODO Fill in listener code or remove function.
				return true;
			}
		});
	}

	/**
	 * Draws buttons which display if you Win or Lose
	 */
	private void drawEndButtons(){
		youWin = new TextButton("You win", textButtonStyle);
		youWin.align(Align.center);
		stage.addActor(youWin);
		youWin.setVisible(false);

		youLose = new TextButton("You Lose", textButtonStyle);
		youLose.align(Align.center);
		stage.addActor(youLose);
		youLose.setVisible(false);
	}

	private void playMinigame(int betAmount, int playerChoice) {
		// Choices are represented by the numbers 0-2 (0 = Rock, 1 = Paper, 2 = Scissors).
		if (game.payGold(betAmount)) { // If the player can actually afford the bet...

			int AIChoice = pickRandom(100); // AI picks a choice at random.
			if (AIChoice < 33) { AIChoice = 0; }
			else if (AIChoice < 66) { AIChoice = 1; }
			else { AIChoice = 2; }

			if (playerChoice == AIChoice) { // Player and AI draw.
				System.out.println("Player has won!");
				//TODO What should happen when there's a draw? Currently returns the bet.
				game.addGold(betAmount);
			} else if ((playerChoice == 0 && AIChoice == 2) || (playerChoice == 1 && AIChoice == 0) || (playerChoice == 2 && AIChoice == 1)) { // Player wins.
				System.out.println("Player has drawn.");
				game.addGold(round(betAmount * mult));
				//TODO Display "You win" text
			} else { // Player loses.
				System.out.println("Player has lost...");
				//TODO Display "You lose" text or something.
			}
		}
	}
}