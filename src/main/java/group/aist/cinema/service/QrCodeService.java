package group.aist.cinema.service;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import group.aist.cinema.model.Ticket;
import group.aist.cinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.google.zxing.BarcodeFormat.QR_CODE;
import static group.aist.cinema.util.TicketUtil.QR_CODE_URL_TEMPLATE;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final TicketRepository ticketRepository;

    public byte[] generatePdfWithQrCode(Long ticketId) throws WriterException, IOException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        String qrCodeText = QR_CODE_URL_TEMPLATE + ticketId;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, QR_CODE, 250, 250);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(pdfOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Ticket Information:"));
        document.add(new Paragraph("Movie: " + ticket.getMovieSession().getMovie().getName()));
        document.add(new Paragraph("Date: " + ticket.getStartDate()));
        document.add(new Paragraph("Price: " + ticket.getPrice() + " " + ticket.getCurrency()));
        document.add(new Paragraph("Ticket ID: " + ticket.getId()));

        document.add(new Paragraph("\nScan the QR code below to validate your ticket:"));

        ImageData imageData = ImageDataFactory.create(pngData);
        Image qrCodeImage = new Image(imageData);
        document.add(qrCodeImage);

        document.close();

        return pdfOutputStream.toByteArray();
    }
}