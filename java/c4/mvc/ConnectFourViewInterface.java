package c4.mvc;

public interface ConnectFourViewInterface {
	public void createView();
	public void playGame();
	public void enableColumn(int col);
	public void disableColumn(int col);
	public void announceWinner(int winner);
	public void announceDraw();
}
