package gg.jte.generated.ondemand;
import hexlet.code.dto.RootPage;
public final class JterootGenerated {
	public static final String JTE_NAME = "root.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,3,3,6,6,8,8,8,10,10,10,11,11,11,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, RootPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"mb-3\">\n        <h1>");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getMessage());
				jteOutput.writeContent("</h1>\n    </div>\n");
			}
		});
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		RootPage page = (RootPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
