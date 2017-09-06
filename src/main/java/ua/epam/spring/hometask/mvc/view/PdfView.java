package ua.epam.spring.hometask.mvc.view;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import ua.epam.spring.hometask.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created on 9/6/2017.
 */
@Component
public class PdfView extends AbstractPdfView{

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        List<User> users = (List<User>) map.get("users");
        for (User user:users) {
            document.add(new Paragraph(user.getFirstName()));
        }
    }
}
