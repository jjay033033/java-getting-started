package top.lmoon.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.lmoon.shadowsupdate.qrcode.ZxingQRcoder;

@WebServlet(urlPatterns="/s/qrcode",name="qrcodeServlet")
public class QrcodeServlet extends HttpServlet{

	private static final long serialVersionUID = 8373787911565159057L;
	
	private static ZxingQRcoder qrcoder = new ZxingQRcoder();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("url");
//		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		qrcoder.encode(content, resp.getOutputStream());
		resp.setContentType("image/jpeg;charset=GB2312");
	}
	
	

}
