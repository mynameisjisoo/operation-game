package miniGame.OperationGame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Finish extends JFrame {

	JOptionPane joption = new JOptionPane();
	Operation o;
	//최종 cnt값 받아와야 함, 메인에서 타이머 끝나고 실행, 중간에 창 끄면 실행되도록 구현해야함
	
	
	public Finish() {

		int result = JOptionPane.showConfirmDialog(null, "cnt점 "+"게임다시하기", "나가기", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.OK_OPTION) { //예 누를 경우
			o = new Operation();
		} else { // 아니오 누를 경우, 그냥 나갈 경우
			System.exit(0);
		}
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

	}

	public static void main(String[] args) {
		new Finish();
	}
}