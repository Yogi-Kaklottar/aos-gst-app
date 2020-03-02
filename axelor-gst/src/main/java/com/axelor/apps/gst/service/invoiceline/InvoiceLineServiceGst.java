package com.axelor.apps.gst.service.invoiceline;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import java.util.Map;

public interface InvoiceLineServiceGst {
  public Map<String, Object> fillGstValues(Invoice invoice, InvoiceLine invoiceLine);

  public InvoiceLine getInvoiceLineData(Invoice invoice, InvoiceLine invoiceLine);
}
