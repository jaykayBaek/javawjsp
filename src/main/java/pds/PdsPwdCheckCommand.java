package pds;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import conn.SecurityUtil;

public class PdsPwdCheckCommand implements PdsInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pag = request.getParameter("pag")==null ? 0 : Integer.parseInt(request.getParameter("pag"));
		String part = request.getParameter("part")==null ? "전체" : request.getParameter("part");
		
		int idx = request.getParameter("idx")==null ? 0 : Integer.parseInt(request.getParameter("idx"));
		String pwd = request.getParameter("pwd")==null ? "" : request.getParameter("pwd");
		String fSName_ = request.getParameter("fSName")==null ? "" : request.getParameter("fSName");
		String[] fSNames = fSName_.split("/");
		
		// 비밀번호를 암호화 한후 DB의 비밀번호와 같으면 작업을 계속 수행처리한다.
		SecurityUtil security = new SecurityUtil();
		pwd = security.encryptSHA256(pwd);
		
		PdsDAO dao = new PdsDAO();
		
		PdsVO vo = dao.getIdxSearch(idx);
		String res = "";
		
		if(vo.getPwd().equals(pwd)) {
			// 파일이 저장되어 있는 서버의 실제 경로를 찾아온다.
			String realPath = request.getServletContext().getRealPath("/data/pds/");
			
			// 서버 파일시스템에 실제로 존재하는 파일들을 찾아서 삭제처리한다.
			for(String fSName : fSNames) {
				/*
				File file = new File(realPath + fSName);
				file.delete();
				*/
				new File(realPath + fSName).delete();
			}
			
			// 파일 삭제 완료후 DB에 저장된 자료파일을 삭제한다.
			res = dao.setPdsDelete(idx);
			
			// response.getWriter().write(res);
		}
		
		if(res.equals("1")) {
			request.setAttribute("msg", "pdsDeleteOk");
		}
		else {
			request.setAttribute("msg", "pdsDeleteNo");
		}
		request.setAttribute("url", request.getContextPath()+"/pdsList.pds?part="+part+"&pag="+pag);
	}

}
