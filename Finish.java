package miniGame.OperationGame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Finish extends JFrame {

	JOptionPane joption = new JOptionPane();
	Operation o;
	//���� cnt�� �޾ƿ;� ��, ���ο��� Ÿ�̸� ������ ����, �߰��� â ���� ����ǵ��� �����ؾ���
	
	
	public Finish() {

		int result = JOptionPane.showConfirmDialog(null, "cnt�� "+"���Ӵٽ��ϱ�", "������", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.OK_OPTION) { //�� ���� ���
			o = new Operation();
		} else { // �ƴϿ� ���� ���, �׳� ���� ���
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