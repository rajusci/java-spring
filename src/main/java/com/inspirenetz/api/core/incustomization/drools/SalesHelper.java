package com.inspirenetz.api.core.incustomization.drools;

import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.service.SaleSKUService;
import com.inspirenetz.api.core.service.SaleService;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 10/9/14.
 */
@Component
public class SalesHelper {

    @Autowired
    SaleService saleService;

    @Autowired
    GeneralUtils generalUtils;



    /**
     * @purpose:  get the purchased product
     * @param referrer
     * @param customer
     * @return  productList
     */
    public List getPurchasedProduct(Customer referrer, Customer customer) {

       Page<Sale> salesPage = saleService.findBySalMerchantNoAndSalLoyaltyId(referrer.getCusMerchantNo(), customer.getCusMobile(), new PageRequest(0, 100));

       List<Sale> salesList = salesPage.getContent();

        List<SaleSKU> saleSKUList = new ArrayList<SaleSKU>(0);

        for (Sale sale : salesList){

            // Add the sku set to the list
            saleSKUList.addAll(sale.getSaleSKUSet());

        }

        String product = "";

        //create an array list to store the products
        List productList = new ArrayList (0);

        for(SaleSKU saleSKU : saleSKUList){

            //Get the sale product
            product = saleSKU.getSsuProductCode();

            //Add the  product to the product list
            productList.add(product);
        }

        return productList;
    }

    //method to get the sales list
    public Double getTotalAmountSpent(String salLoyaltyId, Long salMerchantNo, Date salDate, int months){

        //Get date
        java.util.Date utilDate = generalUtils.addMonthsToDate(salDate,months);

        Date startDate = new java.sql.Date(utilDate.getTime());
        //Get the sales
        Page<Sale> salesPage = saleService.findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(salMerchantNo,salLoyaltyId,startDate,salDate,new PageRequest(0,100));

        List<Sale> salesList = salesPage.getContent();

        List<SaleSKU> saleSKUList = new ArrayList<SaleSKU>(0);

        Double totalAmount = 0.0;

        if(salesList != null){

            Double totalSaleAmount = 0.0;

            Double totalSkuAmount = 0.0;

            for (Sale sale : salesList){

                //check if the sale is item based
                if(sale.getSalType() == SaleType.ITEM_BASED_PURCHASE){

                    // Add the sku set to the list
                    saleSKUList.addAll(sale.getSaleSKUSet());

                } else if( sale.getSalType() == SaleType.STANDARD_PURCHASE){

                    //Get the salamount
                    double salAmount = sale.getSalAmount();

                    totalSaleAmount += salAmount;
                }

            }

            //Check if the saleSKULIst is null
            for (SaleSKU saleSKU : saleSKUList ){

                double amount = saleSKU.getSsuPrice() * saleSKU.getSsuQty();

                totalSkuAmount += amount;

            }

            //add total sku and total sale amount
            totalAmount = totalSaleAmount + totalSkuAmount;

        }

        return totalAmount;
    }

}
