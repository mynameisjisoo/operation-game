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

	// 화면
	Image img;
	Graphics img_g;
	JLabel score;
	JLabel timer;
	JButton pass;
	JButton bonus;
	JPanel jp1; // 랜덤숫자, 연산자
	JPanel jp2; // 제시 숫자
	JPanel jp3; // 클릭한 숫자 나옴
	JTextArea jt; // jt를 jp3에 붙여서 클릭한 숫자나오게함
	JButton jb[] = new JButton[16]; // 패널에 버튼 달음

	GetXY getxy[] = new GetXY[10];
	OperationAction oAction[] = new OperationAction[getxy.length];


	int timeCount = 30;
	
	
	
	// 화면에 보일 숫자와 연산자
	int[] num = new int[10];
	String[] operator = { "+", "-", "X", "/", "=" };
	int target; // 맞춰야 하는 숫자
	int cnt = 0; // 점수(score)



	Font font = new Font("고딕", Font.BOLD, 50);

	
	// 버튼에 값넣기
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

		score.setFont(new Font("고딕",Font.BOLD,30));
		timer.setFont(new Font("고딕",Font.BOLD,30));
		jp1.setBackground(Color.pink);
		jp2.setBackground(Color.pink);
		jp3.setBackground(Color.pink);
		pass.setBackground(Color.lightGray);
		

		//타이머
		Timer my_timer=new Timer(); //java.util
		TimerTask my_task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(timeCount>0) {
					timeCount--;
					timer.setText("남은시간:"+Integer.toString(timeCount)+"초");
					bonusMove();
				}
				else { //시간초과 시 종료
					my_timer.cancel();
					dispose();
				}
			}
		};
		my_timer.schedule(my_task, 0,1000);
		
		
		//버튼초기화
		initNum();
		for (int i = 0; i < jb.length; i++) {
			if (i < 10) {
				jb[i] = new JButton(Integer.toString(num[i])); // 랜덤숫자(0~9)
				jb[i].addActionListener(this);
				jb[i].setBackground(Color.cyan);
			} else if (i >= 10 && i < 15) {
				jb[i] = new JButton(operator[i - 10]); // 연산자(10~14)
				jb[i].addActionListener(this);
				jb[i].setBackground(Color.cyan);
			} else {
				jb[i] = new JButton(Integer.toString(target)); // 맞출숫자 (15)
				jb[i].setBackground(Color.ORANGE);
			}
		}

		// penel에 버튼 달기 (jb1: 랜덤숫자와 연산자 , jb2:target number)
		for (int i = 0; i < jb.length; i++) {
			jb[i].setFont(font);
			jb[i].setPreferredSize(new Dimension(120, 120)); // 버튼크기
			if (i < 15)
				jp1.add(jb[i]);
			else
				jp2.add(jb[i]);
		}


		score.setBounds(100, 50, 150, 50);
		score.setFont(new Font("고딕", Font.BOLD, 30));


		
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

		this.setBounds(100, 100, 800, 800); // 다시 맞춰야됨! 클래스로 받아오기
		this.setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (int i = 0; i < jb.length - 1; i++) {
			if (jb[i] == e.getSource()) { // 클릭하면
				if (e.getSource() != jb[14]) { //(클릭한 숫자 화면에 보이게)
					jt.append(jb[i].getText());
					jt.setFont(new Font("고딕", Font.ITALIC, 40));
					jt.setBackground(Color.ORANGE);
					jp3.add(jt);
					this.add(jp3);
				}

				if (e.getSource() == jb[14]) { // =하면 연산
					calculate();
					jt.setText("");
					initValue();
				}
			}
			
			jb[i].getKeyListeners();
		} // for
		if(e.getSource()==pass) { //pass누른경우
			initValue();
			jt.setText("");
		}
		if(e.getSource()==bonus) { //보너스 누른 경우
			cnt+=1;
			score.setText("score : " + cnt);
		}
	}

	private void calculate() { //계산

		try { 		
			ArrayList<Character> resultMath = new ArrayList<Character>();
			ArrayList<String> resultNum = new ArrayList<String>();
			ArrayList<Integer> resultNum2 = new ArrayList<Integer>();
			String a = jt.getText();
			int numLength = -1;

			if (a.contains("+") | a.contains("-") | a.contains("X") | a.contains("/")) {
				for (int i = 0; i < a.split("\\+|-|X|/").length; i++) { // 입력받은 숫자 쪼개서 arraylist에 저장
					resultNum.add(a.split("\\+|-|X|/")[i]);
					resultNum2.add(Integer.parseInt(a.split("\\+|-|X|/")[i]));
				}
				for (int i = 0; i < a.split("\\+|-|X|/").length - 1; i++) {
					numLength += resultNum.get(i).length() + 1; // 클릭한 숫자의 자릿수 구하기
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
			// 연산자 클릭 안한경우
			else if (!(a.contains("+") | a.contains("-") | a.contains("X") | a.contains("/"))) {
				resultNum2.add(Integer.parseInt(a));
				System.out.println(resultNum2.get(0));
			}

			// 정답확인
			if (resultNum2.get(0) == Integer.parseInt(jb[15].getText())) {
				cnt += 5;
				score.setText("score : " + cnt);
			}	

		} //try
		catch (Exception e) { //연산자만 클릭한 경우 예외처리
			initValue();
			jt.setText("");
		}
	}

	void initNum() { //target number, 클릭할 숫자 랜덤 생성
		for (int i = 0; i < num.length; i++) { 
			num[i] = (int) (Math.random() * 30 + 1); 
		}
		
		target = (int) (Math.random() * 99) + 5;


	}
	
	void bonusMove() { //보너스 버튼 좌표 생성, 스레드(OperationAction)로 좌표 바꿔줌
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
