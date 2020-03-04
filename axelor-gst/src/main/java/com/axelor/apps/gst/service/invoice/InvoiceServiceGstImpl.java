package com.axelor.apps.gst.service.invoice;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.repo.InvoiceRepository;
import com.axelor.apps.account.service.app.AppAccountService;
import com.axelor.apps.account.service.config.AccountConfigService;
import com.axelor.apps.account.service.invoice.InvoiceLineService;
import com.axelor.apps.account.service.invoice.factory.CancelFactory;
import com.axelor.apps.account.service.invoice.factory.ValidateFactory;
import com.axelor.apps.account.service.invoice.factory.VentilateFactory;
import com.axelor.apps.base.service.PartnerService;
import com.axelor.apps.base.service.alarm.AlarmEngineService;
import com.axelor.apps.base.service.app.AppService;
import com.axelor.apps.businessproject.service.InvoiceServiceProjectImpl;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import com.google.inject.Inject;
import java.math.BigDecimal;

public class InvoiceServiceGstImpl extends InvoiceServiceProjectImpl implements InvoiceServiceGst {
  @Inject
  public InvoiceServiceGstImpl(
      ValidateFactory validateFactory,
      VentilateFactory ventilateFactory,
      CancelFactory cancelFactory,
      AlarmEngineService<Invoice> alarmEngineService,
      InvoiceRepository invoiceRepo,
      AppAccountService appAccountService,
      PartnerService partnerService,
      InvoiceLineService invoiceLineService,
      AccountConfigService accountConfigService) {
    super(
        validateFactory,
        ventilateFactory,
        cancelFactory,
        alarmEngineService,
        invoiceRepo,
        appAccountService,
        partnerService,
        invoiceLineService,
        accountConfigService);
  }

  @Inject InvoiceServiceGst invoiceServiceGst;

  @Override
  public Invoice compute(Invoice invoice) throws AxelorException {
    try {

      if (!Beans.get(AppService.class).isApp("gst")) {
        return super.compute(invoice);
      } else {
        super.compute(invoice);
      }

    } catch (Exception e) {

    }
    return setGstValues(invoice);
  }

  @Override
  public Invoice setGstValues(Invoice invoice) {
    try {
      BigDecimal igst, sgst;
      igst = BigDecimal.ZERO;
      sgst = BigDecimal.ZERO;
      for (InvoiceLine invoiceLine : invoice.getInvoiceLineList()) {
        igst = igst.add(invoiceLine.getIgst());
        sgst = sgst.add(invoiceLine.getSgst());
      }
      invoice.setNetIgst(igst);
      invoice.setNetCsgst(sgst);
      invoice.setNetSgst(sgst);
    } catch (Exception e) {

    }
    return invoice;
  }
}
