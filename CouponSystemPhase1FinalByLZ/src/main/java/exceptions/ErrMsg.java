package exceptions;

public enum ErrMsg {
    COMPANY_NAME_EXISTS("Cannot add company with existing name"),
    COMPANY_EMAIL_EXISTS("Cannot add company with existing email"),
    CUSTOMER_EMAIL_EXISTS("Cannot add customer with existing email"),
    COMPANY_UPDATE_FAILED("Cannot update company ID and Name"),
    COMPANY_IS_NOT_FOUND("Cannot delete a company that doesnt exist in the database"),
    CUSTOMER_NOT_FOUND_UPDATE("Can only update existing customer"),
    CUSTOMER_NOT_FOUND_DELETE("Can only delete existing customer"),
    COUPON_ALREADY_EXISTS("Coupon with the name of the same company already exists"),
    COUPON_DELETE_FAILED("Cannot delete a coupon that is not in the system"),
    COUPON_PURCHASE_FAILED_NOT_EXISTING("Cannot purchase a coupon that doesnt exist"),
    COUPON_PURCHASE_FAILED_EXPIRED("It is not possible to purchase an expired coupon"),
    COUPON_PURCHASE_FAILED_ONLY_ONCE("Coupon can't be purchased more than once"),
    LOGIN_FAILED("Login failed, check your mail and password again"),
    COMPANY_NULL("Cannot retrieve a company that doesnt exist in the database"),
    CUSTOMER_NULL("Cannot retrieve a customer that doesnt exist in the database");

    private String message;

    ErrMsg(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
