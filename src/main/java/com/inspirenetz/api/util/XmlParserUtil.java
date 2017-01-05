package com.inspirenetz.api.util;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CashBackConfig;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saneesh-ci on 13/5/14.
 */
@Component
public class XmlParserUtil {

    private static Logger log = LoggerFactory.getLogger(XmlParserUtil.class);

    @Autowired
    Environment environment;


    public List<String> readFromXmlFile(String identifier,String property) throws InspireNetzException {

        //array list for storing the retrieved properties
        List<String> propertyValueList = new ArrayList<>(0);

        try {

            String cashBackFilePath = environment.getProperty("integration.cashback.file.path");

            //read the file data
            File fXmlFile = new File(cashBackFilePath);

            //create an instance of document builder factory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            //document builder object from document builder factory
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //parse the file content
            Document doc = dBuilder.parse(fXmlFile);

            //normalize the content
            doc.getDocumentElement().normalize();

            //read the nodes with tag cashback
            NodeList nList = doc.getElementsByTagName("cashback");

            boolean nodeFound = false;

            //Loop throught the cashbacks to find the requested one
            for (int temp = 0; temp < nList.getLength(); temp++) {

                //get
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    //get the cashback type
                    String cBType = eElement.getAttribute("name");

                    //if the cashback type matches , get the values
                    if(cBType.equals(identifier)){

                        nodeFound = true;
                        //start from the first node
                        int i=0;

                        //if element tagname is the required property, get the value
                        while(eElement.getElementsByTagName(property).item(i) != null){

                            //get the value of the tag
                            String valName =eElement.getElementsByTagName(property).item(i).getTextContent();

                            //add the value to the return list
                            propertyValueList.add(valName);

                            //increment the node pointer
                            i++;


                        }

                        //log the property retrieved
                        log.info("Retrieved property : "+propertyValueList.toString());

                        //break from the loop
                        break;
                    }

                }

            }

            if(!nodeFound) {

                log.error("readFromXmlFile : No property found in the configuration file" + identifier);

                throw new InspireNetzException(APIErrorCode.ERR_NO_PROPERTY_FOUND_IN_CASHBACK_FILE);

            }

        } catch (Exception e){

            //log the error
            log.error("readFromFile : Exception in reading from cashback-config.xml");

            //print the stack trace
            e.printStackTrace();

            //throw new error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }


        return propertyValueList;
    }

    public Map<String,CashBackConfig> getCashBackMap() throws InspireNetzException {

        //array list for storing the retrieved properties
        List<String> propertyValueList = new ArrayList<>(0);

        HashMap<String,CashBackConfig> cashBackConfigHashMap = new HashMap<>(0);

        try {

            String cashBackFilePath = environment.getProperty("integration.cashback.file.path");

            //read the file data
            File fXmlFile = new File(cashBackFilePath);

            //create an instance of document builder factory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            //document builder object from document builder factory
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //parse the file content
            Document doc = dBuilder.parse(fXmlFile);

            //normalize the content
            doc.getDocumentElement().normalize();

            //read the nodes with tag cashback
            NodeList nList = doc.getElementsByTagName("cashback");

            boolean nodeFound = false;

            //Loop throught the cashbacks to find the requested one
            for (int temp = 0; temp < nList.getLength(); temp++) {

                //get
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    //get the cashback type
                    String cBType = eElement.getAttribute("name");

                    CashBackConfig cashBackConfig = new CashBackConfig();

                    cashBackConfig.setCashBackName(cBType.toLowerCase());

                    cashBackConfig = getValidationData(cashBackConfig,eElement);

                    cashBackConfig = getPreApiData(cashBackConfig, eElement);

                    cashBackConfig = getPostApiData(cashBackConfig, eElement);

                    cashBackConfig = getNotificationData(cashBackConfig, eElement);

                    cashBackConfig = getCustomerSpielData(cashBackConfig, eElement);

                    cashBackConfig = getMerchantSpielData(cashBackConfig, eElement);

                    /*cashBackConfig = getRewardCurrencyInfo(cashBackConfig,eElement);*/

                    cashBackConfigHashMap.put(cBType.toLowerCase(),cashBackConfig);

                }

            }


        } catch (Exception e){

            //log the error
            log.error("readFromFile : Exception in reading from cashback-config.xml");

            //print the stack trace
            e.printStackTrace();

            //throw new error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }


        return cashBackConfigHashMap;
    }

    private CashBackConfig getRewardCurrencyInfo(CashBackConfig cashBackConfig, Element eElement) {

        //if element tagname is the required property, get the value
        if(eElement.getElementsByTagName("rwdcurrency").item(0) != null){

            Long currencyId = Long.parseLong(environment.getProperty(eElement.getElementsByTagName("rwdcurrency").item(0).getTextContent()));

            cashBackConfig.setRwdCurrencyId(currencyId);
        }

        return cashBackConfig;
    }



    private CashBackConfig getMerchantSpielData(CashBackConfig cashBackConfig, Element eElement) {

        //start from the first node
        int i=0;

        //if element tagname is the required property, get the value
        while(eElement.getElementsByTagName("merchantspiel").item(i) != null){

            //get the value of the tag
            String valName =eElement.getElementsByTagName("merchantspiel").item(i).getTextContent();

            i++;

            cashBackConfig.setMerchantSpiel(valName);
        }



        return cashBackConfig;
    }


    private CashBackConfig getCustomerSpielData(CashBackConfig cashBackConfig, Element eElement) {

        //start from the first node
        int i=0;

        //if element tagname is the required property, get the value
        while(eElement.getElementsByTagName("customerspiel").item(i) != null){

            //get the value of the tag
            String valName =eElement.getElementsByTagName("customerspiel").item(i).getTextContent();

            cashBackConfig.setCustomerSpiel(valName);

            //increment the node pointer
            i++;


        }

        return cashBackConfig;
    }

    private CashBackConfig getNotificationData(CashBackConfig cashBackConfig, Element eElement) {

        //start from the first node
        int i=0;

        List<String> propertyValueList = new ArrayList<>(0);

        //if element tagname is the required property, get the value
        while(eElement.getElementsByTagName("notification").item(i) != null){

            //get the value of the tag
            String valName =eElement.getElementsByTagName("notification").item(i).getTextContent();

            //add the value to the return list
            propertyValueList.add(valName);

            //increment the node pointer
            i++;


        }

        cashBackConfig.setNotifications(propertyValueList);

        return cashBackConfig;
    }

    private CashBackConfig getPostApiData(CashBackConfig cashBackConfig, Element eElement) {

        //start from the first node
        int i=0;

        //if element tagname is the required property, get the value
        while(eElement.getElementsByTagName("postapi").item(i) != null){

            //get the value of the tag
            String valName =eElement.getElementsByTagName("postapi").item(i).getTextContent();

            cashBackConfig.setPostapi(valName);

            //increment the node pointer
            i++;

        }

        return cashBackConfig;
    }

    private CashBackConfig getPreApiData(CashBackConfig cashBackConfig, Element eElement) {

        //start from the first node
        int i=0;

        List<String> propertyValueList = new ArrayList<>(0);

        //if element tagname is the required property, get the value
        while(eElement.getElementsByTagName("preapi").item(i) != null){

            //get the value of the tag
            String valName =eElement.getElementsByTagName("preapi").item(i).getTextContent();

            cashBackConfig.setPreapi(valName);

            //increment the node pointer
            i++;


        }

        return cashBackConfig;
    }

    private CashBackConfig getValidationData(CashBackConfig cashBackConfig, Element eElement) {

        //start from the first node
        int i=0;

        List<String> propertyValueList = new ArrayList<>(0);

        //if element tagname is the required property, get the value
        while(eElement.getElementsByTagName("validation").item(i) != null){

            //get the value of the tag
            String valName =eElement.getElementsByTagName("validation").item(i).getTextContent();

            //add the value to the return list
            propertyValueList.add(valName);

            //increment the node pointer
            i++;


        }

        cashBackConfig.setValidations(propertyValueList);

        return cashBackConfig;
    }
}
