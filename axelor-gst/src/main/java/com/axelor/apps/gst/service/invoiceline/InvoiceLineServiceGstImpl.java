package com.axelor.apps.gst.service.invoiceline;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.service.AccountManagementAccountService;
import com.axelor.apps.account.service.AnalyticMoveLineService;
import com.axelor.apps.account.service.app.AppAccountService;
import com.axelor.apps.base.service.CurrencyService;
import com.axelor.apps.base.service.PriceListService;
import com.axelor.apps.base.service.app.AppService;
import com.axelor.apps.businessproject.service.InvoiceLineProjectServiceImpl;
import com.axelor.apps.purchase.service.PurchaseProductService;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import com.google.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InvoiceLineServiceGstImpl extends InvoiceLineProjectServiceImpl
    implements InvoiceLineServiceGst {

  @Inject
  public InvoiceLineServiceGstImpl(
      CurrencyService currencyService,
      PriceListService priceListService,
      AppAccountService appAccountService,
      AnalyticMoveLineService analyticMoveLineService,
      AccountManagementAccountService accountManagementAccountService,
      PurchaseProductService purchaseProductService) {
    super(
        currencyService,
        priceListService,
        appAccountService,
        analyticMoveLineService,
        accountManagementAccountService,
        purchaseProductService);
    // TODO Auto-generated constructor stub
  }

  @Override
  public Map<String, Object> fillProductInformation(Invoice invoice, InvoiceLine invoiceLine)
      throws AxelorException {
    Map<String, Object> productInformation = super.fillProductInformation(invoice, invoiceLine);
    if (Beans.get(AppService.class).isApp("gst")) {
      productInformation.putAll(fillGstValues(invoice, invoiceLine));
    }
    return productInformation;
  }

  @Override
  public Map<String, Object> fillGstValues(Invoice invoice, InvoiceLine invoiceLine) {
    Map<String, Object> gstRateMap = new HashMap<String, Object>();
    try {
      BigDecimal igst, sgst, csgst;
      BigDecimal amount = invoiceLine.getPrice();
      BigDecimal price;
      if (invoiceLine.getDiscountTypeSelect() == 1 || invoiceLine.getDiscountTypeSelect() == 2) {
        amount = invoiceLine.getPriceDiscounted();
      }
      if (invoiceLine.getTaxCode().equals("GSTTAX")) {
        BigDecimal rateGst;
        rateGst = invoiceLine.getTaxRate();
        gstRateMap.put("gstRate", rateGst.multiply(new BigDecimal(100)));
        if (invoice.getCompany().getAddress().getState().equals(invoice.getAddress().getState())) {
          igst = BigDecimal.ZERO;
          sgst =
              (amount.multiply(rateGst).divide(new BigDecimal(2))).multiply(invoiceLine.getQty());
          gstRateMap.put("igst", igst);
          gstRateMap.put("sgst", sgst);
          gstRateMap.put("cgst", sgst);
        } else {
          igst = (amount.multiply(rateGst)).multiply(invoiceLine.getQty());
          sgst = BigDecimal.ZERO;
          gstRateMap.put("igst", igst);
          gstRateMap.put("sgst", sgst);
          gstRateMap.put("cgst", sgst);
        }

      } else {

        gstRateMap.put("igst", BigDecimal.ZERO);
        gstRateMap.put("sgst", BigDecimal.ZERO);
        gstRateMap.put("cgst", BigDecimal.ZERO);
      }
    } catch (Exception e) {

    }

    return gstRateMap;
  }

  public InvoiceLine getInvoiceLineData(Invoice invoice, InvoiceLine invoiceLine) {
    try {
      Map<String, Object> gstRateMap = new HashMap<String, Object>();
      gstRateMap = fillGstValues(invoice, invoiceLine);
      BigDecimal igst = (BigDecimal) gstRateMap.get("igst");
      BigDecimal sgst = (BigDecimal) gstRateMap.get("sgst");
      BigDecimal cgst = (BigDecimal) gstRateMap.get("cgst");
      invoiceLine.setIgst(igst);
      invoiceLine.setSgst(sgst);
      invoiceLine.setCgst(cgst);
    } catch (Exception e) {

    }
    return invoiceLine;
  }
}
