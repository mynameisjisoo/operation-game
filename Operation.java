package miniGame.OperationGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Operation extends JFrame implements ActionListener {

	// ȭ��
	Image img;
	Graphics img_g;
	JLabel score;
	JLabel timer;
	JButton pass;
	JButton bonus;
	JPanel jp1; // ��������, ������
	JPanel jp2; // ���� ����
	JPanel jp3; // Ŭ���� ���� ����
	JTextArea jt; // jt�� jp3�� �ٿ��� Ŭ���� ���ڳ�������
	JButton jb[] = new JButton[16]; // �гο� ��ư ����

	GetXY getxy[] = new GetXY[10];
	OperationAction oAction[] = new OperationAction[getxy.length];


	int timeCount = 30;
	
	
	
	// ȭ�鿡 ���� ���ڿ� ������
	int[] num = new int[10];
	String[] operator = { "+", "-", "X", "/", "=" };
	int target; // ����� �ϴ� ����
	int cnt = 0; // ����(score)



	Font font = new Font("���", Font.BOLD, 50);

	
	// ��ư�� ���ֱ�
	void initValue() {
		initNum();
		for (int i = 0; i < num.length; i++) {
			jb[i].setText(Integer.toString(num[i]));
		}
		jb[jb.length-1].setText(Integer.toString(target));

	}

	public Operation() {
		jp3 = new JPanel();
		jt = new JTextArea();
		jt.setAlignmentX(CENTER_ALIGNMENT);
		jt.setEditable(false);
		score = new JLabel("score : " + cnt);
		timer = new JLabel();
		pass = new JButton("PASS");
		bonus = new JButton("BONUS");
		pass.addActionListener(this);
		bonus.addActionListener(this);
		


		jp1 = new JPanel();
		jp2 = new JPanel();

		this.add(jp1);
		this.add(jp2);
		//		this.add(pan);
		this.add(jp3);
		this.add(score);
		this.add(timer);
		this.add(pass);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.pink);

		jp1.setBounds(40, 100, 500, 500);
		jp2.setBounds(600, 100, 120, 120);
		jp3.setBounds(40, 650, 500, 50);
		score.setBounds(100, 50, 150, 50);
		timer.setBounds(400, 50, 300, 50);
		pass.setBounds(600, 250, 120, 50);

		score.setFont(new Font("���",Font.BOLD,30));
		timer.setFont(new Font("���",Font.BOLD,30));
		jp1.setBackground(Color.pink);
		jp2.setBackground(Color.pink);
		jp3.setBackground(Color.pink);
		pass.setBackground(Color.lightGray);
		

		//Ÿ�̸�
		Timer my_timer=new Timer(); //java.util
		TimerTask my_task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(timeCount>0) {
					timeCount--;
					timer.setText("�����ð�:"+Integer.toString(timeCount)+"��");
					bonusMove();
				}
				else { //�ð��ʰ� �� ����
					my_timer.cancel();
					dispose();
				}
			}
		};
		my_timer.schedule(my_task, 0,1000);
		
		
		//��ư�ʱ�ȭ
		initNum();
		for (int i = 0; i < jb.length; i++) {
			if (i < 10) {
				jb[i] = new JButton(Integer.toString(num[i])); // ��������(0~9)
				jb[i].addActionListener(this);
				jb[i].setBackground(Color.cyan);
			} else if (i >= 10 && i < 15) {
				jb[i] = new JButton(operator[i - 10]); // ������(10~14)
				jb[i].addActionListener(this);
				jb[i].setBackground(Color.cyan);
			} else {
				jb[i] = new JButton(Integer.toString(target)); // ������� (15)
				jb[i].setBackground(Color.ORANGE);
			}
		}

		// penel�� ��ư �ޱ� (jb1: �������ڿ� ������ , jb2:target number)
		for (int i = 0; i < jb.length; i++) {
			jb[i].setFont(font);
			jb[i].setPreferredSize(new Dimension(120, 120)); // ��ưũ��
			if (i < 15)
				jp1.add(jb[i]);
			else
				jp2.add(jb[i]);
		}


		score.setBounds(100, 50, 150, 50);
		score.setFont(new Font("���", Font.BOLD, 30));


		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});

		this.setBounds(100, 100, 800, 800); // �ٽ� ����ߵ�! Ŭ������ �޾ƿ���
		this.setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (int i = 0; i < jb.length - 1; i++) {
			if (jb[i] == e.getSource()) { // Ŭ���ϸ�
				if (e.getSource() != jb[14]) { //(Ŭ���� ���� ȭ�鿡 ���̰�)
					jt.append(jb[i].getText());
					jt.setFont(new Font("���", Font.ITALIC, 40));
					jt.setBackground(Color.ORANGE);
					jp3.add(jt);
					this.add(jp3);
				}

				if (e.getSource() == jb[14]) { // =�ϸ� ����
					calculate();
					jt.setText("");
					initValue();
				}
			}
			
			jb[i].getKeyListeners();
		} // for
		if(e.getSource()==pass) { //pass�������
			initValue();
			jt.setText("");
		}
		if(e.getSource()==bonus) { //���ʽ� ���� ���
			cnt+=1;
			score.setText("score : " + cnt);
		}
	}

	private void calculate() { //���

		try { 		
			ArrayList<Character> resultMath = new ArrayList<Character>();
			ArrayList<String> resultNum = new ArrayList<String>();
			ArrayList<Integer> resultNum2 = new ArrayList<Integer>();
			String a = jt.getText();
			int numLength = -1;

			if (a.contains("+") | a.contains("-") | a.contains("X") | a.contains("/")) {
				for (int i = 0; i < a.split("\\+|-|X|/").length; i++) { // �Է¹��� ���� �ɰ��� arraylist�� ����
					resultNum.add(a.split("\\+|-|X|/")[i]);
					resultNum2.add(Integer.parseInt(a.split("\\+|-|X|/")[i]));
				}
				for (int i = 0; i < a.split("\\+|-|X|/").length - 1; i++) {
					numLength += resultNum.get(i).length() + 1; // Ŭ���� ������ �ڸ��� ���ϱ�
					resultMath.add(a.charAt(numLength));
				}
				for (int i = 0; i < resultMath.size(); i++) {
					if (resultMath.get(i) == 'X') {
						resultNum2.set(i, resultNum2.get(i) * resultNum2.get(i + 1));
						resultNum2.remove(i + 1);
						resultMath.remove(i);
						i--;
					}
				}
				for (int i = 0; i < resultMath.size(); i++) {
					if (resultMath.get(i) == '+') {
						resultNum2.set(i, resultNum2.get(i) + resultNum2.get(i + 1));
						resultNum2.remove(i + 1);
						resultMath.remove(i);
						i--;
					}
				}
				for (int i = 0; i < resultMath.size(); i++) {
					if (resultMath.get(i) == '-') {
						resultNum2.set(i, resultNum2.get(i) - resultNum2.get(i + 1));
						resultNum2.remove(i + 1);
						resultMath.remove(i);
						i--;
					}
				}
				System.out.println(resultNum2.get(0));
			}
			// ������ Ŭ�� ���Ѱ��
			else if (!(a.contains("+") | a.contains("-") | a.contains("X") | a.contains("/"))) {
				resultNum2.add(Integer.parseInt(a));
				System.out.println(resultNum2.get(0));
			}

			// ����Ȯ��
			if (resultNum2.get(0) == Integer.parseInt(jb[15].getText())) {
				cnt += 5;
				score.setText("score : " + cnt);
			}	

		} //try
		catch (Exception e) { //�����ڸ� Ŭ���� ��� ����ó��
			initValue();
			jt.setText("");
		}
	}

	void initNum() { //target number, Ŭ���� ���� ���� ����
		for (int i = 0; i < num.length; i++) { 
			num[i] = (int) (Math.random() * 30 + 1); 
		}
		
		target = (int) (Math.random() * 99) + 5;


	}
	
	void bonusMove() { //���ʽ� ��ư ��ǥ ����, ������(OperationAction)�� ��ǥ �ٲ���
		Color[] c = { Color.GREEN, Color.BLUE, Color.magenta, Color.black, Color.RED, Color.yellow };
		
		for (int i = 0; i < getxy.length; i++) {
			getxy[i]= new GetXY((int)(Math.random()*650)+100, (int)(Math.random()*600)+100);
			
		}
		for (int i = 0; i < getxy.length; i++) {
			bonus.setBounds(getxy[i].getX(), getxy[i].getY(), 100, 50);
			bonus.setBackground(c[(int)(Math.random()*6)]);
			this.add(bonus);

		}
		for (int i = 0; i < getxy.length; i++) {
			oAction[i] = new OperationAction(Operation.this, getxy[i]);
			oAction[i].start();
		}
	}
	


	public static void main(String[] args) {
		new Operation();
	}
}
