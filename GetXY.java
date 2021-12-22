package miniGame.OperationGame;

public class GetXY { //ÁÂÇ¥°ª º¯°æ

	public int x,y;
	public int ox,oy;

	
	public GetXY() {
		super();
	}

	public GetXY(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public GetXY(int x, int y, int ox, int oy) {
		super();
		this.x = x;
		this.y = y;
		this.ox = ox;
		this.oy = oy;
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getOx() {
		return ox;
	}

	public void setOx(int ox) {
		this.ox = ox;
	}

	public int getOy() {
		return oy;
	}

	public void setOy(int oy) {
		this.oy = oy;
	}

	
}
