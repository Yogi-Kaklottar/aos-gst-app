package com.axelor.apps.gst.service.invoiceline;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InvoiceLineServiceGstImpl implements InvoiceLineServiceGst {

  @Override
  public Map<String, Object> fillGstValues(Invoice invoice, InvoiceLine invoiceLine) {
    Map<String, Object> gstRateMap = new HashMap<String, Object>();
    try {
      BigDecimal igst, sgst, csgst;
      BigDecimal amount = invoiceLine.getPrice();
      BigDecimal price;
      if (invoiceLine.getDiscountTypeSelect() == 1 || invoiceLine.getDiscountTypeSelect() == 2) {
        price = invoiceLine.getPriceDiscounted();
      } else {
        price = amount;
      }
      amount = price;
      if (invoiceLine.getTaxCode().equals("GSTTAX")) {
        BigDecimal rateGst;
        rateGst = invoiceLine.getTaxRate();
        gstRateMap.put("gstRate", rateGst.multiply(new BigDecimal(100)));
        if (invoice.getCompany().getAddress().getState().equals(invoice.getAddress().getState())) {
          igst = BigDecimal.ZERO;
          sgst = amount.multiply(rateGst).divide(new BigDecimal(2));
          csgst = amount.multiply(rateGst).divide(new BigDecimal(2));
          sgst = sgst.multiply(invoiceLine.getQty());
          csgst = csgst.multiply(invoiceLine.getQty());
          gstRateMap.put("igst", igst);
          gstRateMap.put("sgst", sgst);
          gstRateMap.put("cgst", csgst);
        } else {
          igst = amount.multiply(rateGst);
          igst = igst.multiply(invoiceLine.getQty());
          sgst = BigDecimal.ZERO;
          csgst = BigDecimal.ZERO;
          gstRateMap.put("igst", igst);
          gstRateMap.put("sgst", sgst);
          gstRateMap.put("cgst", csgst);
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
      // e.printStackTrace();
    }
    return invoiceLine;
  }
}
