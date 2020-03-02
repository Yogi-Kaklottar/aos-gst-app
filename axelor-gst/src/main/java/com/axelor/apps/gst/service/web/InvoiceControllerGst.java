package com.axelor.apps.gst.service.web;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.web.InvoiceController;
import com.axelor.apps.gst.service.invoice.InvoiceServiceGst;
import com.axelor.apps.gst.service.invoiceline.InvoiceLineServiceGst;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import java.util.List;

public class InvoiceControllerGst extends InvoiceController {
  @Inject InvoiceServiceGst invoiceServiceGst;
  @Inject InvoiceLineServiceGst gstInvoiceLineService;

  @Override
  public void computeAddressStr(ActionRequest request, ActionResponse response) {
    // TODO Auto-generated method stub
    try {
      super.computeAddressStr(request, response);
      Invoice invoice = request.getContext().asType(Invoice.class);
      if (!invoice.getInvoiceLineList().isEmpty() || invoice.getInvoiceLineList() != null) {
        List<InvoiceLine> invoiceLineList = invoice.getInvoiceLineList();
        for (InvoiceLine invoiceLine : invoiceLineList) {
          invoiceLine = gstInvoiceLineService.getInvoiceLineData(invoice, invoiceLine);
        }
        invoice.setInvoiceLineList(invoiceLineList);
        response.setValue("invoiceLineList", invoiceLineList);
        Invoice invoiceNetGstData = invoiceServiceGst.setGstValues(invoice);
        invoice.setNetIgst(invoiceNetGstData.getNetIgst());
        invoice.setNetCsgst(invoiceNetGstData.getNetCsgst());
        invoice.setNetSgst(invoiceNetGstData.getNetSgst());
        response.setValue("netIgst", invoice.getNetIgst());
        response.setValue("netCsgst", invoice.getNetCsgst());
        response.setValue("netSgst", invoice.getNetSgst());
      }
    } catch (Exception e) {

    }
  }
}
