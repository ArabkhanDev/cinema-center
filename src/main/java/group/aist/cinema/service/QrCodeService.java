package group.aist.cinema.service;

import com.google.zxing.BarcodeFormat;
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
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrCodeService {

    public byte[] generatePdfWithQrCode(Long ticketId) throws WriterException, IOException {
        String qrCodeText = "http://localhost:8080/v1/api/tickets/scanQrCode/" + ticketId;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 250, 250);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(pdfOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        ImageData imageData = ImageDataFactory.create(pngData);
        Image qrCodeImage = new Image(imageData);
        document.add(new Paragraph("Scan the QR code to return the ticket:"));
        document.add(qrCodeImage);

        document.close();

        return pdfOutputStream.toByteArray();
    }

}
