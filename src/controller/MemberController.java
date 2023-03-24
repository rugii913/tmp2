package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Member;

public class MemberController extends Controller {
	public List<Member> members = new ArrayList<>();
	Scanner sc;
	int lastId = 3;

	public MemberController(Scanner sc) {
		this.sc = sc;
	}

	@Override
	public void doAction(String actionName, String cmd) {

		switch (actionName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("명령어 확인 바람m");
		}
	}

	private void doJoin() {
		String loginId;
		String loginPw;

		while (true) {
			System.out.print("아이디: ");
			loginId = sc.nextLine();
			if (isLoginIdDup(loginId)) {
				System.out.println("이미 존재하는 아이디");
				continue;
			}
			break;
		}

		while (true) {
			System.out.print("비밀번호: ");
			loginPw = sc.nextLine();
			System.out.print("비밀번호 확인:");
			String loginPwConfirm = sc.nextLine();

			if (!loginPw.equals(loginPwConfirm)) {
				System.out.println("비밀번호와 비밀번호 확인 불일치");
				continue;
			}
			break;
		}

		System.out.print("이름: ");
		String name = sc.nextLine();

		members.add(new Member(++lastId, now, now, loginId, loginPw, name));
		System.out.println(lastId + "번 회원, 가입 완료");
	}

	private void doLogin() {
		String loginId;
		String loginPw;

		while (true) {
			System.out.print("아이디: ");
			loginId = sc.nextLine();
			if (getIndexByLoginId(loginId) == -1) {
				System.out.println("일치하는 회원이 존재하지 않음");
				continue;
			}
			break;
		}

		Member foundMember = getMemberByLoginId(loginId);

		while (true) {
			System.out.print("비밀번호: ");
			loginPw = sc.nextLine();
			if (!foundMember.loginPw.equals(loginPw)) {
				System.out.println("비밀번호 불일치");
				continue;
			}
			break;
		}

		System.out.println(loginId + " 님 로그인 성공");
		loginedMember = foundMember;
	}

	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃 완료");
	}

	private boolean isLoginIdDup(String loginId) {
		if (getIndexByLoginId(loginId) == -1) {
			return false;
		}
		return true;
	}

	private int getIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private Member getMemberByLoginId(String loginId) {
		int index = getIndexByLoginId(loginId);
		if (index == -1) {
			return null;
		}
		return members.get(index);
	}

	@Override
	public void makeTestData() {
		System.out.println("회원 테스트데이터 생성");
		members.add(new Member(1, now, now, "test1", "test1", "1철수"));
		members.add(new Member(2, now, now, "test2", "test2", "2철수"));
		members.add(new Member(3, now, now, "test3", "test3", "3철수"));
	}

}
