package com.axelor.apps.gst.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.account.service.invoice.print.InvoicePrintServiceImpl;
import com.axelor.apps.account.web.InvoiceController;
import com.axelor.apps.account.web.InvoiceLineController;
import com.axelor.apps.businessproject.service.InvoiceLineProjectServiceImpl;
import com.axelor.apps.businessproject.service.InvoiceServiceProjectImpl;
import com.axelor.apps.gst.service.invoice.InvoicePrintServiceImplGst;
import com.axelor.apps.gst.service.invoice.InvoiceServiceGst;
import com.axelor.apps.gst.service.invoice.InvoiceServiceGstImpl;
import com.axelor.apps.gst.service.invoice.InvoiceServiceProjectImplGst;
import com.axelor.apps.gst.service.invoiceline.InvoiceLineServiceGst;
import com.axelor.apps.gst.service.invoiceline.InvoiceLineServiceGstImpl;
import com.axelor.apps.gst.service.invoiceline.InvoiceLineServiceImplGst;
import com.axelor.apps.gst.service.web.InvoiceControllerGst;
import com.axelor.apps.gst.service.web.InvoiceLineControllerGst;

public class GstModule extends AxelorModule {
  @Override
  protected void configure() {
    bind(InvoiceLineProjectServiceImpl.class).to(InvoiceLineServiceImplGst.class);
    bind(InvoiceLineServiceGst.class).to(InvoiceLineServiceGstImpl.class);
    bind(InvoiceServiceGst.class).to(InvoiceServiceGstImpl.class);
    bind(InvoiceServiceProjectImpl.class).to(InvoiceServiceProjectImplGst.class);
    bind(InvoiceController.class).to(InvoiceControllerGst.class);
    bind(InvoicePrintServiceImpl.class).to(InvoicePrintServiceImplGst.class);
    
  }
}
