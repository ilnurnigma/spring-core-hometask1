package ua.epam.spring.hometask.converter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class PdfHttpMessageConverter implements HttpMessageConverter<List<Ticket>> {
    @Override
    public boolean canRead(Class aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class aClass, MediaType mediaType) {
        return MediaType.APPLICATION_PDF.equals(mediaType);

    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_PDF);
    }

    @Override
    public void write(List<Ticket> tickets, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, httpOutputMessage.getBody());
            document.open();
            document.add(new Paragraph("Booked tickets"));
            document.add(new Paragraph(" "));

            for (Ticket ticket:tickets) {
                User user = ticket.getUser();
                document.add(new Paragraph("Ticket for: " + user.getFirstName() + " "
                        + user.getLastName() + " " + user.getEmail()));
                Event event = ticket.getEvent();
                document.add(new Paragraph("Event: " + event.getName() + ", price: " + event.getBasePrice()));
                String dateTime = ticket.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                document.add(new Paragraph("Date and time: " + dateTime + "    seat: " + ticket.getSeat()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }

    @Override
    public List<Ticket> read(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }
}
