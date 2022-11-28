package member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.mem")
public class MemberController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberInterface command = null;
		String viewPage = "/WEB-INF/member";
		String uri = request.getRequestURI();
		String com = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
		
		if(com.equals("/memLogin")) {
			viewPage += "/memLogin.jsp";
		}
		else if(com.equals("/memLoginOk")) {
			command = new MemLoginOkCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		else if(com.equals("/memLogout")) {
			command = new MemLogoutCommand();
			command.execute(request, response);
			viewPage = "/include/message.jsp";
		}
		//멤버메인은 댓글에 대한 답장 등등 command가 필요함.
		else if(com.equals("/memMain")) {
			command = new MemMainCommand();
			command.execute(request, response);
			viewPage += "/memMain.jsp";
		}
		else if(com.equals("/memIdCheck")) {
			command = new MemIdCheckCommand();
			command.execute(request, response);
			viewPage += "/memIdCheck.jsp";
		}
		else if(com.equals("/memJoin")) {
			viewPage += "/memJoin.jsp";
		}
		else if(com.equals("/memJoinOk")) {
			viewPage += "/memLogin.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
