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

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.round;

//import javax.xml.soap.Text;

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
    private Label goldLabel;
	private ArrayList<Image> blankCardList;
	private Label messageLabel;
	/**
	 * Constructor
	 */
	public MinigameScreen(GameManager game){
		super(game);

		betAmount = 1;

		Texture backgroundTex = new Texture("logoBackground.png");
		Image backgroundImg = new Image(backgroundTex);
		backgroundImg.setSize(viewwidth, viewheight);
		mainStage.addActor(backgroundImg);

		Texture blankCardTexture = new Texture("card_back.png");

		blankCardList = new ArrayList<Image>();
		ArrayList<Image> bankerCardList = new ArrayList<Image>();
		bankerCardList.add(new Image(new Texture("pistol.png")));
		bankerCardList.add(new Image(new Texture("map.png")));
		bankerCardList.add(new Image(new Texture("hook.png")));


		ArrayList<Stack> stackList = new ArrayList<Stack>();

		for (int i=0; i<=2; i++){
			Image blankCard = new Image(blankCardTexture);
			Image card = bankerCardList.get(i);

			blankCard.setOrigin(Align.center);
			card.setOrigin(Align.center);
			blankCard.rotateBy(180);
			card.rotateBy(180);

			Stack stack = new Stack();
			stack.addActor(card);
			stack.addActor(blankCard);

			stackList.add(stack);
			blankCardList.add(blankCard);
		}



		Table table = new Table();
		table.setFillParent(true);
		mainStage.addActor(table);

		messageLabel = new Label("", skin, "title");

		ImageButton pistol = createImageButton("pistol.png", 0);
		ImageButton map = createImageButton("map.png", 1);
		ImageButton hook = createImageButton("hook.png", 2);

		betSlider = new Slider(1, 1000, 1, false, skin);
		betAmountLabel = new Label("" + betAmount, skin, "title");
//        betSlider.setColor(Color.YELLOW);

		Table bankerTable = new Table();
		bankerTable.add(stackList.get(0));
		bankerTable.add(stackList.get(1)).pad(0, viewwidth/15, 0, viewwidth/15);
		bankerTable.add(stackList.get(2));

		Table cardTable = new Table();
		cardTable.add(pistol);
		cardTable.add(map).pad(0, viewwidth/15, 0, viewwidth/15);
		cardTable.add(hook);

		goldLabel = new Label("" + game.getGold(), skin, "title");

		Table goldTable = new Table();
		Table betTable = new Table();

		goldTable.add(new Label("Your Gold: ", skin, "title"));
		goldTable.add(goldLabel);

		betTable.add(new Label("Bet Amount: ", skin, "title"));
		betTable.add(betAmountLabel);

		table.add(new Label("Banker", skin, "default-button")).colspan(2);
		table.row();
		table.add(bankerTable).colspan(2);
		table.row();
		table.add(messageLabel).colspan(2).height(viewheight/24f);
		table.row();
		table.add(cardTable).colspan(2);
		table.row();
		table.add(betSlider).fill().colspan(2);
		table.row().uniform();
		table.add(goldTable).right().padRight(viewwidth/48f);
		table.add(betTable).left().padLeft(viewwidth/48f);

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
				playMinigame(betAmount, choice);
				return true;
			}
		});
		return button;
	}

	@Override
	public void update(float delta){
		betAmount = Math.round(betSlider.getValue());
		betAmountLabel.setText("" + betAmount);
		goldLabel.setText("" + game.getGold());
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
			int AIChoice = pickRandom(3); // AI picks a choice at random.
			System.out.println(AIChoice);
			blankCardList.get(AIChoice).setVisible(false);
			if (playerChoice == AIChoice) { // Player and AI draw.
				Gdx.app.log("Minigame", "Player has drawn.");
				//TODO What should happen when there's a draw? Currently returns the bet.
				messageLabel.setText("You drew, bet returned.");
				game.addGold(betAmount);
			} else if ((playerChoice == 0 && AIChoice == 2) || (playerChoice == 1 && AIChoice == 0) || (playerChoice == 2 && AIChoice == 1)) { // Player wins.
				Gdx.app.log("Minigame", "Player has won.");
				game.addGold(round(betAmount * mult));
				messageLabel.setText("You won "+ round(betAmount * mult) + " gold.");
				//TODO Display "You win" text
			} else { // Player loses.
				Gdx.app.log("Minigame", "Player has lost.");
				messageLabel.setText("You lost your bet.");
				//TODO Display "You lose" text or something.
			}
		}
		else {
			messageLabel.setText("You can't afford that bet!");
		}
	}
}