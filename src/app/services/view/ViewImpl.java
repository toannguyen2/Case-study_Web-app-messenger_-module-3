package app.services.view;

import app.config.PageConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewImpl implements View {
	protected Map<String, Parameter> param;

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	protected String layout;

	protected ViewImpl() {
	}

	protected ViewImpl(HttpServletRequest req, HttpServletResponse resp){
		request  = req;
		response = resp;
		setDefault();
	}

	protected ViewImpl(HttpServletRequest req, HttpServletResponse resp, String layout){
		request  = req;
		response = resp;
		this.layout = layout;
		setDefault();
	}

	private void setDefault(){
		setContentType(PageConfig.TYPE_CONTENT_TEXT);
		setCharacterEncoding(PageConfig.ENCODING_UTF_8);
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void setContentType(String contentType) {
		response.setContentType(contentType);
	}

	public void setCharacterEncoding(String encoding) {
		response.setCharacterEncoding(encoding);
	}

	public void setHeader(String key, String value) {
		response.setHeader(key, value);
	}

	public void setContentLength(int length) {
		response.setContentLength(length);
	}

	public void setAttribute(String name, Parameter parameter) {
		if (param == null){
			synchronized (ViewImpl.class){
				param = new HashMap<String , Parameter>();
			}
		}
		param.put(name, parameter);
	}

	public void pushTitle() {
		Parameter parameter = new Parameter(PageConfig.TITLE);
		request.setAttribute("title", parameter);
	}

	public void pushDesc() {
		Parameter parameter = new Parameter(PageConfig.DESCRIPTION);
		request.setAttribute("desc", parameter);
	}

	public void pushAttribute() {
		if (param == null){
			pushTitle();
			pushDesc();
		} else {
			if (param.get("title") == null) pushTitle();
			if (param.get("desc") == null)  pushDesc();

			for (Map.Entry<String, Parameter> entry : param.entrySet()){
				request.setAttribute(entry.getKey(), entry.getValue());
			}
		}
	}
}
