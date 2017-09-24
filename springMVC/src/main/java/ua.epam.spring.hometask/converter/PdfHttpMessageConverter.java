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

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PdfHttpMessageConverter<T> implements HttpMessageConverter<T> {
    @Override
    public boolean canRead(Class aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class aClass, MediaType mediaType) {
        if (MediaType.APPLICATION_PDF.equals(mediaType)) {
            return true;
        }

        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_PDF);
    }

    @Override
    public void write(T t, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, httpOutputMessage.getBody());
            document.open();
            document.add(new Paragraph("Hello world!"));
            document.close();
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }

    @Override
    public T read(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }
}
