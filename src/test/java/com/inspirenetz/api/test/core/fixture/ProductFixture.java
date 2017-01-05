package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.test.core.builder.ProductBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
public class ProductFixture {

    public static Product standardProduct() {

        Product product = ProductBuilder.aProduct()
                .withPrdMerchantNo(1L)
                .withPrdCode("PRD100031")
                .withPrdName("Pepsi Can")
                .withPrdDescription("Pepsi Can 200ML")
                .withPrdBrand("1")
                .withPrdCategory1("1")
                .withPrdCategory2("2")
                .withPrdCategory3("3")
                .withPrdMsfEnabledInd(IndicatorStatus.YES)
                .withPrdMsfValue(100.0)
                .withPrdMemberSalePrice(100)
                .withPrdPurchaseCurrency(356)
                .withPrdSaleCurrency(356)
                .withPrdSalePrice(150)
                .withPrdPurchasePrice(130)
                .withPrdOnSaleInd(IndicatorStatus.YES)
                .withPrdPriceEditable(IndicatorStatus.NO)
                .withPrdImage(1)
                .withPrdStockQuantity(10)
                .withPrdTaxPerc(2.5)
                .withPrdServiceTaxPerc(12.36)
                .withPrdLocation(1L)
                .build();


        return product;


    }


    public static Product updatedStandardProduct(Product product) {

        product.setPrdName("CocaCola");
        product.setPrdDescription("Another Soft Drink");

        return product;

    }


    public static Set<Product> standardProducts() {

        Set<Product> products = new HashSet<Product>(0);

        Product pepsi = ProductBuilder.aProduct()
                .withPrdMerchantNo(1L)
                .withPrdCode("PRD1000031")
                .withPrdName("Pepsi Can")
                .withPrdDescription("Pepsi Can 200ML")
                .withPrdBrand("1")
                .withPrdCategory1("1")
                .withPrdCategory2("2")
                .withPrdCategory3("3")
                .withPrdMsfEnabledInd(IndicatorStatus.YES)
                .withPrdMsfValue(100.0)
                .withPrdMemberSalePrice(100)
                .withPrdPurchaseCurrency(356)
                .withPrdSaleCurrency(356)
                .withPrdSalePrice(150)
                .withPrdPurchasePrice(130)
                .withPrdOnSaleInd(IndicatorStatus.YES)
                .withPrdPriceEditable(IndicatorStatus.NO)
                .withPrdImage(1)
                .withPrdStockQuantity(10)
                .withPrdTaxPerc(2.5)
                .withPrdServiceTaxPerc(12.36)
                .build();

        products.add(pepsi);



        Product coke  = ProductBuilder.aProduct()
                .withPrdMerchantNo(1L)
                .withPrdCode("PRD1000043")
                .withPrdName("Coke Can")
                .withPrdDescription("Coke Can 200ML")
                .withPrdBrand("1")
                .withPrdCategory1("1")
                .withPrdCategory2("2")
                .withPrdCategory3("3")
                .withPrdMsfEnabledInd(IndicatorStatus.YES)
                .withPrdMsfValue(100.0)
                .withPrdMemberSalePrice(100)
                .withPrdPurchaseCurrency(356)
                .withPrdSaleCurrency(356)
                .withPrdSalePrice(120)
                .withPrdPurchasePrice(100)
                .withPrdOnSaleInd(IndicatorStatus.YES)
                .withPrdPriceEditable(IndicatorStatus.NO)
                .withPrdImage(1)
                .withPrdStockQuantity(20)
                .withPrdTaxPerc(2.5)
                .withPrdServiceTaxPerc(12.36)
                .build();


        products.add(coke);



        return products;



    }
}
