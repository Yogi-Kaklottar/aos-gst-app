package com.axelor.apps.gst.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.account.service.invoice.print.InvoicePrintServiceImpl;
import com.axelor.apps.account.web.InvoiceController;
import com.axelor.apps.businessproject.service.InvoiceLineProjectServiceImpl;
import com.axelor.apps.businessproject.service.InvoiceServiceProjectImpl;
import com.axelor.apps.gst.service.invoice.InvoicePrintGstServiceImpl;
import com.axelor.apps.gst.service.invoice.InvoiceServiceGst;
import com.axelor.apps.gst.service.invoice.InvoiceServiceGstImpl;
import com.axelor.apps.gst.service.invoiceline.InvoiceLineServiceGst;
import com.axelor.apps.gst.service.invoiceline.InvoiceLineServiceGstImpl;
import com.axelor.apps.gst.service.web.InvoiceGstController;

public class GstModule extends AxelorModule {
  @Override
  protected void configure() {
    bind(InvoiceLineProjectServiceImpl.class).to(InvoiceLineServiceGstImpl.class);
    bind(InvoiceLineServiceGst.class).to(InvoiceLineServiceGstImpl.class);
    bind(InvoiceServiceGst.class).to(InvoiceServiceGstImpl.class);
    bind(InvoiceServiceProjectImpl.class).to(InvoiceServiceGstImpl.class);
    bind(InvoiceController.class).to(InvoiceGstController.class);
    bind(InvoicePrintServiceImpl.class).to(InvoicePrintGstServiceImpl.class);
  }
}
