import java.util.Scanner;

import controller.ArticleContorller;
import controller.Controller;
import controller.MemberController;

public class App {
	public void start() {
		System.out.println("프로그램 시작");
		Scanner sc = new Scanner(System.in);
		
		Controller controller;
		MemberController memberController = new MemberController(sc);
		ArticleContorller articleContorller = new ArticleContorller(sc, memberController);
		
		memberController.makeTestData();
		articleContorller.makeTestData();
		
		while (true) {
			System.out.print("명령어 >> ");
			String cmd = sc.nextLine().trim();
			
			if (cmd.length() == 0) {
				System.out.println("명령어 입력 바람");
				continue;
			}
			
			if (cmd.equals("exit")) {
				break;
			}
			
			String[] cmdDiv = cmd.split(" ");
			
			if (cmdDiv.length == 1) {
				System.out.println("명령어 확인 바람");
				continue;
			}
			
			String controllerName = cmdDiv[0];
			String actionName = cmdDiv[1];
			String strForLoginCheck = controllerName + "/" + actionName;
			
			if (controllerName.equals("article")) {
				controller = articleContorller;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.println("명령어 확인 바람");
				continue;
			}
			
			switch (strForLoginCheck) {
			case "article/write":
			case "article/modify":
			case "article/delete":
			case "member/logout":
				if (!controller.isLogined()) {
					System.out.println("로그인 후 사용 바람");
					continue;
				}
				break;
			case "member/join":
			case "member/login":
				if (controller.isLogined()) {
					System.out.println("로그아웃 후 사용 바람");
					continue;
				}
				break;
			}
			
			controller.doAction(actionName, cmd);
			
		}
		
		
		
		sc.close();
		System.out.println("프로그램 종료");
	}
}
