package com.bitcamp221.didabara.testpdf;

import com.bitcamp221.didabara.model.UserInfoEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePdfReport {

  public static ByteArrayInputStream userInfosReport(UserInfoEntity userInfoEntityList) {

    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {

      PdfPTable table = new PdfPTable(3);
      table.setWidthPercentage(100);
      table.setWidths(new int[]{1, 10, 3});

      Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

      PdfPCell hcell;
      hcell = new PdfPCell(new Phrase("Id", headFont));
      hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(hcell);

      hcell = new PdfPCell(new Phrase("Name", headFont));
      hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(hcell);

      hcell = new PdfPCell(new Phrase("Population", headFont));
      hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(hcell);


      PdfPCell cell;

      cell = new PdfPCell(new Phrase(userInfoEntityList.getId().toString()));
      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setColspan(1000=);
      table.addCell(cell);

      String text1 = new String(userInfoEntityList.getFilename().getBytes(), "utf-8");

      cell = new PdfPCell(new Phrase(text1));
      cell.setPaddingLeft(5);
      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      table.addCell(cell);

      cell = new PdfPCell(new Phrase(String.valueOf(userInfoEntityList.getFileOriName())));
      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      cell.setPaddingRight(100);
      table.addCell(cell);

      PdfWriter.getInstance(document, out);
      document.open();
      document.add(table);

      document.close();

    } catch (DocumentException ex) {
      Logger.getLogger(GeneratePdfReport.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    return new ByteArrayInputStream(out.toByteArray());
  }
}
