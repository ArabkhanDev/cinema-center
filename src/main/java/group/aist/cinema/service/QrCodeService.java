package group.aist.cinema.service;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import group.aist.cinema.dto.response.MovieResponseDTO;
import group.aist.cinema.mapper.MovieMapper;
import group.aist.cinema.model.Ticket;
import group.aist.cinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final TicketRepository ticketRepository;
    private final TemplateEngine templateEngine;
    private final MovieMapper movieMapper;

    public byte[] generatePdfWithQrCode(Long ticketId) {
        Context context = new Context();

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        String time = ticket.getMovieSession().getTime().toArray()[0].toString().substring(11, 16);
        String sector = ticket.getPrice().equals(BigDecimal.valueOf(20.00)) ? "VIP" : "STANDARD";
        Random random = new Random();
        long ticketNumberLong = random.nextLong(1000000000000000L, 9999999999999999L);
        String ticketNumber = String.valueOf(ticketNumberLong);
        List<String> digits = Arrays.asList(ticketNumber.split(""));
        System.out.println(digits);
        context.setVariable("ticketNumber", digits);
        context.setVariable("name", ticket.getMovieSession().getMovie().getName());
        context.setVariable("date", ticket.getStartDate().toString());
        context.setVariable("time", time);
        context.setVariable("price", ticket.getPrice());
        context.setVariable("sector", sector);

        String pdf = templateEngine.process("pdf", context);

        MovieResponseDTO movie = movieMapper.mapToDto(ticket.getMovieSession().getMovie());
        BarcodeQRCode qrCode = new BarcodeQRCode(movie.toString());

        File file = new File("ticket.pdf");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PdfWriter pdfWriter = null;
        try {
            pdfWriter = new PdfWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        PdfFormXObject formXObject = qrCode.createFormXObject(ColorConstants.BLACK, pdfDocument);
        Image image = new Image(formXObject).setWidth(150f).setHeight(150f);

        Document parsedDoc = Jsoup.parse(pdf);
        Element elementById = parsedDoc.getElementById("qr-code");

        HtmlConverter.convertToDocument(pdf, pdfWriter);
        com.itextpdf.layout.Document document = HtmlConverter.convertToDocument(pdf, pdfWriter);
        document.add(new Paragraph().add(image).setFixedPosition(elementById.siblingIndex() * 40, pdfDocument.getDefaultPageSize().getHeight() - 385, 150));
        document.close();

        try {
            return fileToByteArray(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private byte[] fileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }
}