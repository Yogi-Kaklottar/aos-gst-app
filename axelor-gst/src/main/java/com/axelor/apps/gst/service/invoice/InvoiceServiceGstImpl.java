package com.axelor.apps.gst.service.invoice;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import java.math.BigDecimal;

public class InvoiceServiceGstImpl implements InvoiceServiceGst {

  @Override
  public Invoice setGstValues(Invoice invoice) {
    try {

      invoice.setNetCsgst(BigDecimal.ZERO);
      invoice.setNetIgst(BigDecimal.ZERO);
      invoice.setNetSgst(BigDecimal.ZERO);
      for (InvoiceLine invoiceLine : invoice.getInvoiceLineList()) {
        invoice.setNetIgst(invoice.getNetIgst().add(invoiceLine.getIgst()));
        invoice.setNetCsgst(invoice.getNetCsgst().add(invoiceLine.getCgst()));
        invoice.setNetSgst(invoice.getNetSgst().add(invoiceLine.getSgst()));
      }

    } catch (Exception e) {

    }
    return invoice;
  }
}
