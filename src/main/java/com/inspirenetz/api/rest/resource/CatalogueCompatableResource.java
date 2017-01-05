package com.inspirenetz.api.rest.resource;

/**
 * Created by alameen on 15/12/14.
 */
public class CatalogueCompatableResource extends BaseResource {

  Long cat_prd_no;

  String cat_prd_category_text;

  Integer cat_prd_category;

  String cat_prd_code;

  String cat_prd_desc;

  String cat_prd_long_desc;

  String cat_image;

  Long cat_image_id;

  double cat_rwd_points;

  double cat_cash;

  Long  cat_rwd_currency;

  String cat_merchant_name;

  double cat_rwd_balance;

  Integer cat_has_balance;

  public Long getCat_prd_no() {
        return cat_prd_no;
    }

    public void setCat_prd_no(Long cat_prd_no) {
        this.cat_prd_no = cat_prd_no;
    }

    public String getCat_prd_category_text() {
        return cat_prd_category_text;
    }

    public void setCat_prd_category_text(String cat_prd_category_text) {
        this.cat_prd_category_text = cat_prd_category_text;
    }

    public Integer getCat_prd_category() {
        return cat_prd_category;
    }

    public void setCat_prd_category(Integer cat_prd_category) {
        this.cat_prd_category = cat_prd_category;
    }

    public String getCat_prd_code() {
        return cat_prd_code;
    }

    public void setCat_prd_code(String cat_prd_code) {
        this.cat_prd_code = cat_prd_code;
    }

    public String getCat_prd_desc() {
        return cat_prd_desc;
    }

    public void setCat_prd_desc(String cat_prd_desc) {
        this.cat_prd_desc = cat_prd_desc;
    }

    public String getCat_prd_long_desc() {
        return cat_prd_long_desc;
    }

    public void setCat_prd_long_desc(String cat_prd_long_desc) {
        this.cat_prd_long_desc = cat_prd_long_desc;
    }

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }

    public Long getCat_image_id() {
        return cat_image_id;
    }

    public void setCat_image_id(Long cat_image_id) {
        this.cat_image_id = cat_image_id;
    }

    public double getCat_rwd_points() {
        return cat_rwd_points;
    }

    public void setCat_rwd_points(double cat_rwd_points) {
        this.cat_rwd_points = cat_rwd_points;
    }

    public double getCat_cash() {
        return cat_cash;
    }

    public void setCat_cash(double cat_cash) {
        this.cat_cash = cat_cash;
    }

    public Long getCat_rwd_currency() {
        return cat_rwd_currency;
    }

    public void setCat_rwd_currency(Long cat_rwd_currency) {
        this.cat_rwd_currency = cat_rwd_currency;
    }

    public String getCat_merchant_name() {
        return cat_merchant_name;
    }

    public void setCat_merchant_name(String cat_merchant_name) {
        this.cat_merchant_name = cat_merchant_name;
    }

    public double getCat_rwd_balance() {
        return cat_rwd_balance;
    }

    public void setCat_rwd_balance(double cat_rwd_balance) {
        this.cat_rwd_balance = cat_rwd_balance;
    }

    public Integer getCat_has_balance() {
        return cat_has_balance;
    }

    public void setCat_has_balance(Integer cat_has_balance) {
        this.cat_has_balance = cat_has_balance;
    }
}
