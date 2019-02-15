package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import game_manager.GameManager;

import java.util.Random;

import static java.lang.Math.round;

public class MinigameScreen extends BaseScreen {

	/**
	 * Booleans used to track if the Game is over and won/lost, plus buttons to display the info
	 */
	private TextButton youWin;
	private TextButton youLose;

	/**
	 * Variables for the minigame.
	 */
	private float mult = 1.5f; // Odds of winning are 1/3 so a 50% return is pretty generous.

	private int betAmount;
	private Slider betSlider;
	private Label betAmountLabel;

	/**
	 * Constructor
	 */
	public MinigameScreen(GameManager game){
		super(game);

		betAmount = 1;

		Texture backgroundTex = new Texture("menuBackground.png");
		Image backgroundImg = new Image(backgroundTex);
		backgroundImg.setSize(viewwidth, viewheight);
		mainStage.addActor(backgroundImg);


		Table table = new Table();
		table.setFillParent(true);
		mainStage.addActor(table);

		ImageButton pistol = createImageButton("pistol.png", 0);
		ImageButton map = createImageButton("map.png", 1);
		ImageButton hook = createImageButton("hook.png", 2);

		betSlider = new Slider(1, 1000, 1, false, skin);
		betAmountLabel = new Label("" + betAmount, skin);
//        betSlider.setColor(Color.YELLOW);

		Table bankerTable = new Table();
		bankerTable.add();
		bankerTable.add();
		bankerTable.add();

		Table cardTable = new Table();
		cardTable.add(pistol);
		cardTable.add(map).pad(0, viewwidth/15, 0, viewwidth/15);
		cardTable.add(hook);

		table.add(bankerTable);
		table.row();
		table.add(cardTable).colspan(2);
		table.row();
		table.add(betSlider).fill().colspan(2);
		table.row().uniform();
		table.add(new Label("Bet Amount: ", skin)).right();
		table.add(betAmountLabel).left();

		table.setDebug(true);
		drawEndButtons();
	}

	@Override
	public void show() {
		musicSetup("the-buccaneers-haul.mp3");
		/**
		 * Creates Text buttons for the menu, Sets them up and Adds listeners to switch to correct screen
		 */


	}

	public ImageButton createImageButton(String file, final int choice){
		Drawable texture = new TextureRegionDrawable(new TextureRegion(new Texture(file)));
		ImageButton button = new ImageButton(texture);
		button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				playMinigame(50, choice);
				return true;
			}
		});
		return button;
	}

	@Override
	public void update(float delta){
		betAmount = Math.round(betSlider.getValue());
		betAmountLabel.setText("" + betAmount);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.input.setInputProcessor(mainStage);
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
	}

	/**
	 * Picks a Random integer
	 */
	private int pickRandom(int max){
		Random rand = new Random();
		return rand.nextInt(max);
	}

	/**
	 * Draws buttons which display if you Win or Lose
	 */
	private void drawEndButtons(){
		youWin = new TextButton("You Win", skin);
		youWin.align(Align.center);
		mainStage.addActor(youWin);
		youWin.setVisible(false);

		youLose = new TextButton("You Lose", skin);
		youLose.align(Align.center);
		mainStage.addActor(youLose);
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
				Gdx.app.log("Minigame", "Player has won.");
				//TODO What should happen when there's a draw? Currently returns the bet.
				game.addGold(betAmount);
			} else if ((playerChoice == 0 && AIChoice == 2) || (playerChoice == 1 && AIChoice == 0) || (playerChoice == 2 && AIChoice == 1)) { // Player wins.
				Gdx.app.log("Minigame", "Player has drawn.");
				game.addGold(round(betAmount * mult));
				//TODO Display "You win" text
			} else { // Player loses.
				Gdx.app.log("Minigame", "Player has lost.");
				//TODO Display "You lose" text or something.
			}
		}
	}
}