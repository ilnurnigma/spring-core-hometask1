/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2017-09-18 13:46:47 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class eventServiceForm_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<html>\r\n");
      out.write("<head></head>\r\n");
      out.write("<body>\r\n");
      out.write("<h1>Event service form</h1>\r\n");
      out.write("\r\n");
      out.write("<h2>Save event</h2>\r\n");
      out.write("<form method=\"post\" action=\"save\">\r\n");
      out.write("    Name: <input type=\"text\" name=\"name\" />\t<br/>\r\n");
      out.write("  \tBase price:  <input type=\"number\" name=\"basePrice\" />\t<br/>\r\n");
      out.write("  \tRating:    <input type=\"text\" name=\"rating\" />\t<br/>\r\n");
      out.write("  \t<input type=\"submit\" value=\"Save\" />\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<h2>Get event by name</h2>\r\n");
      out.write("<form method=\"post\" action=\"name\">\r\n");
      out.write("    Name: <input type=\"text\" name=\"name\" />\t<br/>\r\n");
      out.write("  \t<input type=\"submit\" value=\"Find\" />\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<h2>Delete event by name</h2>\r\n");
      out.write("<form method=\"post\" action=\"delete\">\r\n");
      out.write("    Name: <input type=\"text\" name=\"name\" />\t<br/>\r\n");
      out.write("  \t<input type=\"submit\" value=\"Delete\" />\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<h2>Get events for a date range</h2>\r\n");
      out.write("<form method=\"post\" action=\"date\">\r\n");
      out.write("    E-mail: <input type=\"date\" name=\"from\"/>\t<br/>\r\n");
      out.write("    E-mail: <input type=\"date\" name=\"to\"/>\t<br/>\r\n");
      out.write("  \t<input type=\"submit\" value=\"Find\" />\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<h2>Get next events</h2>\r\n");
      out.write("<form method=\"post\" action=\"next\">\r\n");
      out.write("    E-mail: <input type=\"datetime-local\" name=\"to\"/>\t<br/>\r\n");
      out.write("  \t<input type=\"submit\" value=\"Find\" />\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<a href=\"all\">Get all events</a>\r\n");
      out.write("</br>\r\n");
      out.write("\r\n");
      out.write("<a href=\"/\">Main page</a>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
