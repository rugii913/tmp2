package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Article;
import dto.Member;

public class ArticleContorller extends Controller {
	MemberController memberController;
	List<Article> articles = new ArrayList<>();
	Scanner sc;
	int lastId = 3;

	public ArticleContorller(Scanner sc, MemberController memberController) {
		this.sc = sc;
		this.memberController = memberController;
	}

	@Override
	public void doAction(String actionName, String cmd) {
		switch (actionName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail(cmd);
			break;
		case "modify":
			doModify(cmd);
			break;
		case "delete":
			doDelete(cmd);
			break;
		default:
			System.out.println("명령어 확인 바람a");
		}

	}

	private void doWrite() {
		System.out.print("제목: ");
		String title = sc.nextLine();
		System.out.print("내용: ");
		String body = sc.nextLine();

		articles.add(new Article(++lastId, now, now, loginedMember.id, title, body, 0));
		System.out.println(lastId + "번 게시글 작성 완료");
	}

	private void showList() {
		System.out.println("  글번호  //  제목  //  작성자  ");
		Article foundArticle;
		String writerName = null;
		List<Member> members = memberController.members;
		
		for (int i = articles.size() - 1 ; i >= 0 ; i--) {
			foundArticle = articles.get(i);
			
			for (Member member : members) {
				if (foundArticle.memberId == member.id) {
					writerName = member.name;
					break;
				} else {
					writerName = "알 수 없는 게시자";
				}
			}
			
			System.out.printf("  %d  //  %s  //  %s  \n", foundArticle.id, foundArticle.title, writerName);
			
		}
	}

	private void showDetail(String cmd) {
		String [] cmdDiv = cmd.split(" ");
		if (cmdDiv.length != 3) {
			System.out.println("명령어 확인 바람");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);  
		
		int index = getIndexById(id);
		
		if (index == -1) {
			System.out.println(id + "번 게시글은 존재하지 않습니다.");
			return;
		}
		
		
		Article foundArticle;
		String writerName = null;
		List<Member> members = memberController.members;
		
		foundArticle = articles.get(index);
		
		for (Member member : members) {
			if (foundArticle.memberId == member.id) {
				writerName = member.name;
				break;
			} else {
				writerName = "알 수 없는 게시자";
			}
		}

		System.out.printf("제목: %s \n", foundArticle.title);
		System.out.printf("내용: %s \n", foundArticle.body);
		System.out.printf("작성자: %s \n", writerName);
	}

	private void doModify(String cmd) {
		String [] cmdDiv = cmd.split(" ");
		if (cmdDiv.length != 3) {
			System.out.println("명령어 확인 바람");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);  
		
		if (getIndexById(id) == -1) {
			System.out.println(id + "번 게시글은 존재하지 않습니다.");
			return;
		}
		
		Article foundArticle = articles.get(getIndexById(id));
		
		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}
		
		System.out.print("새 제목: ");
		String title = sc.nextLine();
		System.out.print("새 내용: ");
		String body = sc.nextLine();
		
		foundArticle.title = title;
		foundArticle.body = body;
		foundArticle.updateDate = now;
		System.out.println(id + "번 게시글 수정 완료");
	}

	private void doDelete(String cmd) {
		String [] cmdDiv = cmd.split(" ");
		if (cmdDiv.length != 3) {
			System.out.println("명령어 확인 바람");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);  
		
		if (getIndexById(id) == -1) {
			System.out.println(id + "번 게시글은 존재하지 않습니다.");
			return;
		}
		
		Article foundArticle = articles.get(getIndexById(id));
		
		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}
		
		articles.remove(getIndexById(id));
		
		System.out.println(id + "번 게시글 삭제 완료");
	}

	private int getIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (id == article.id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	public void makeTestData() {
		System.out.println("게시글 테스트데이터 생성");
		articles.add(new Article(1, now, now, 2, "제목1", "내용1", 11));
		articles.add(new Article(2, now, now, 2, "제목2", "내용2", 22));
		articles.add(new Article(3, now, now, 3, "제목3", "내용3", 33));
	}

}
