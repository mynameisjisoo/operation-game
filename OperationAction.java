package miniGame.OperationGame;

public class OperationAction extends Thread { 
	
	public Operation o;
	public GetXY g;
	int speed ;

	public OperationAction(Operation o, GetXY g) {
		super();
		this.o = o;
		this.g = g;
		this.speed = (int)(Math.random()*10)+7;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Thread.sleep(100);
				g.setX(g.getX()+speed);
				g.setY(g.getY()+speed);
//				System.out.println("¾ß");
				/*if(g.getX()>o.getPan().getWidth()) {
					g.setY((int)(Math.random()*800));
					g.setX(-100);
				}
				if(g.getY()>o.getPan().getHeight()) {
					g.setX((int)(Math.random()*800));
					g.setY(-100);
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	

}
