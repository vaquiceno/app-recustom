package com.dashboard.app.controller;

import com.dashboard.app.model.Log;
import com.dashboard.app.model.User;
import com.dashboard.app.service.LogService;
import com.dashboard.app.service.UserService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdfs")
@RequiredArgsConstructor
@Tag(name = "Pdf Controller", description = "Endpoints for managing pdfs")
public class PDFController {


    private final UserService userService;
    private final LogService logService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<InputStreamResource> getUserPdf(@PathVariable Long userId) throws IOException {
        User user = userService.getUserById(userId);
        Log log = logService.getLogByUserId(userId);
        logService.increaseDownloadCount(userId);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        Paragraph paragraph = new Paragraph("User Info");
        paragraph.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
        document.add(paragraph);

        Table table = new Table(2);

        Cell cell = new Cell().add(new Paragraph("id"));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(String.valueOf(user.getId())));
        table.addCell(cell);

        cell = new Cell().add(new Paragraph("name"));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(user.getName()));
        table.addCell(cell);

        cell = new Cell().add(new Paragraph("email"));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(user.getEmail()));
        table.addCell(cell);

        cell = new Cell().add(new Paragraph("role"));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(user.getRole().toString()));
        table.addCell(cell);

        document.add(table);

        paragraph = new Paragraph("User Logs");
        paragraph.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
        document.add(paragraph);

        table = new Table(3);

        cell = new Cell().add(new Paragraph("userId log"));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph("number of loggings"));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph("number of downloads"));
        table.addCell(cell);

        cell = new Cell().add(new Paragraph(String.valueOf(log.getId())));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(String.valueOf(log.getNumberOfLoggings())));
        table.addCell(cell);
        cell = new Cell().add(new Paragraph(String.valueOf(log.getNumberOfDownloads())));
        table.addCell(cell);

        document.add(table);
        document.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "user_" + user.getId() + ".pdf");

        return ResponseEntity.ok().headers(headers).body(inputStreamResource);
    }
}