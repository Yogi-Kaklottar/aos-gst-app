package com.axelor.apps.gst.service.web;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.gst.service.invoiceline.InvoiceLineServiceGst;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import java.util.Map;

public class InvoiceLineController {

  @Inject InvoiceLineServiceGst gstInvoiceLineService;

  public void setInvoiceLineGstComputation(ActionRequest request, ActionResponse response) {
    try {
      InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);
      Invoice invoice = request.getContext().getParent().asType(Invoice.class);
      Map<String, Object> gstRateMap = gstInvoiceLineService.fillGstValues(invoice, invoiceLine);
      gstRateMap.forEach(
          (key, value) -> {
            response.setValue(key, value);
          });
    } catch (Exception e) {

    }
  }
}
