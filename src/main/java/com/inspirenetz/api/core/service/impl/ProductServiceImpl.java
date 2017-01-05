package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.ProductRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.ProductService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    ProductRepository productRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private CustomerService customerService;


    public ProductServiceImpl() {

        super(Product.class);

    }


    @Override
    protected BaseRepository<Product,Long> getDao() {
        return productRepository;
    }


    @Override
    public Page<Product> findByPrdMerchantNoAndPrdLocation(Long prdMerchantNo,Long prdLocation,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<Product> productList = productRepository.findByPrdMerchantNoAndPrdLocation(prdMerchantNo,prdLocation,pageable);

        // Return the list
        return productList;

    }


    @Override
    public Product findByPrdId(Long prdId) {

        // Get the product for the given product id from the repository
        Product product = productRepository.findByPrdId(prdId);

        // Return the product
        return product;

    }

    @Override
    public Product findByPrdMerchantNoAndPrdCode(Long prdMerchantNo, String prdCode) {

        // Get the product using the product code and the merchant number
        Product product = productRepository.findByPrdMerchantNoAndPrdCode(prdMerchantNo, prdCode);

        // Return the product object
        return product;

    }


    @Override
    public Page<Product> findByPrdMerchantNoAndPrdLocationAndPrdNameLike(Long prdMerchantNo,Long prdLocation, String prdName,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<Product> productList = productRepository.findByPrdMerchantNoAndPrdLocationAndPrdNameLike(prdMerchantNo, prdLocation,prdName,pageable);

        // Return the list
        return productList;

    }


    @Override
    public Page<Product> findByPrdMerchantNoAndPrdLocationAndPrdCodeLike(Long prdMerchantNo,Long prdLocation, String prdCode,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<Product> productList = productRepository.findByPrdMerchantNoAndPrdLocationAndPrdCodeLike(prdMerchantNo, prdLocation,prdCode,pageable);

        // Return the list
        return productList;

    }



    @Override
    public boolean isProductCodeDuplicateExisting(Product product) {

        // Get the product information
        Product exProduct = productRepository.findByPrdMerchantNoAndPrdCode(product.getPrdMerchantNo(), product.getPrdCode());

        // If the brnId is 0L, then its a new product so we just need to check if there is ano
        // ther product code
        if ( product.getPrdId() == null || product.getPrdId() == 0L ) {

            // If the product is not null, then return true
            if ( exProduct != null ) {

                return true;

            }

        } else {

            // Check if the product is null
            if ( exProduct != null && product.getPrdId().longValue() != exProduct.getPrdId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public Product saveProduct(Product product ) throws InspireNetzException {

        // Save the product
        return productRepository.save(product);

    }

    @Override
    public boolean deleteProduct(Long brnId) throws InspireNetzException {


        // Delete the product
        productRepository.delete(brnId);

        // return true
        return true;

    }

    @Override
    public Product validateAndSaveProduct(Product product) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_PRODUCT);

        return saveProduct(product);

    }

    @Override
    public boolean validateAndDeleteProduct(Long prdId) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_PRODUCT);

        return  deleteProduct(prdId);

    }

    @Override
    public List<Product> findByPrdMerchantNoAndPrdLocation(Long prdMerchantNo, Long prdLocation) {

        // Get the data from the repository and store in the list
        List<Product> productList = productRepository.findByPrdMerchantNoAndPrdLocation(prdMerchantNo,prdLocation);

        // Return the list
        return productList;
    }

    @Override
    public Page<Product> listProductInCustomer(Long merchantNo,String filter,String query,Pageable pageable) throws InspireNetzException {

        //get user number
        Long userNo =authSessionUtils.getUserNo();

        //initialize pageable parameter
        Page<Product> products=null;

        //check user type is merchant user
        int userType =authSessionUtils.getUserType();

        if(userType == UserType.MERCHANT_USER || userType ==UserType.MERCHANT_ADMIN){

            //get merchant users
            merchantNo =authSessionUtils.getMerchantNo();
        }

        //find customer details
        Customer customer =customerService.findByCusUserNoAndCusMerchantNoAndCusStatus(userNo,merchantNo, CustomerStatus.ACTIVE);

        //if the customer null or not
        if(customer ==null){

            log.info("Product service impl->listProductInCustomer:Error customer not found");

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }


        //get customer location
        if(filter.equals("name")){

            products =findByPrdMerchantNoAndPrdLocationAndPrdNameLike(merchantNo,customer.getCusLocation()==null?0:customer.getCusLocation(),"%"+query+"%",pageable);

        }else if(filter.equals("code")){

            products =findByPrdMerchantNoAndPrdLocationAndPrdCodeLike(merchantNo,customer.getCusLocation()==null?0:customer.getCusLocation(),"%"+query+"%",pageable);

        }



        return products;
    }

    @Override
    public List<Product> saveAllProducts(List<Product> products){

        List<Product> validatedProducts=new ArrayList<>();

        for(Product product:products){

            Product productFetched=productRepository.findByPrdMerchantNoAndPrdCode(product.getPrdMerchantNo(),product.getPrdCode());

            if(productFetched!=null){

                productFetched.setPrdName(product.getPrdName());

                productFetched.setPrdDescription(product.getPrdDescription());

                productFetched.setPrdSalePrice(product.getPrdSalePrice());

                validatedProducts.add(productFetched);

            }else{

                validatedProducts.add(product);
            }
        }

        return saveAll(validatedProducts);
    }
}
