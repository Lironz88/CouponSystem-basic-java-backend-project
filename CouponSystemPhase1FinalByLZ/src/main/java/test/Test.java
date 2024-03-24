package test;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.ConnectionPool;
import db.DatabaseManager;
import enums.Category;
import enums.ClientType;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import jobs.DailyRemovalExpiredCoupons;
import jobs.LoginManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

public class Test {
    public static void Testy() throws SQLException {
        DatabaseManager.dropAndCreateStrategy();
        try {
            LoginManager manager = LoginManager.getInstance();
            DailyRemovalExpiredCoupons dailyJob = new DailyRemovalExpiredCoupons();
            Thread thread = new Thread(dailyJob);
            thread.start();

            AdminFacade adminFacade;
            System.out.println("@@@-Admin facade-@@@");
            adminFacade = (AdminFacade) manager.login("admin@admin.com", "admin", ClientType.Administrator);
            Company company = new Company("catFood", "snacks@gmail.com", "12345");
            adminFacade.addCompany(company);
            company.setPassword("123456");
            adminFacade.updateCompany(company, 1);
            System.out.println("**New company after update**");
            System.out.println(adminFacade.getSingleCompany(1));
            System.out.println("**All companies**");
            System.out.println(adminFacade.getAllCompanies());

            Customer customer = new Customer("Lesh", "blindCat", "Lesh@lesh.com", "12345678");
            adminFacade.addCustomer(customer);
            System.out.println("**New customer**");
            System.out.println(adminFacade.getSingleCustomer(1));
            customer.setLastName("blindOrangeCat");
            adminFacade.updateCustomer(customer,1);
            System.out.println("**Customer after update**");
            System.out.println(adminFacade.getSingleCustomer(1));
            System.out.println("**All customers**");
            System.out.println(adminFacade.getAllCustomers());
            System.out.println("@@@-Company facade-@@@");
            CompanyFacade companyFacade = (CompanyFacade) manager.login("snacks@gmail.com", "123456", ClientType.Company);
            Coupon coupon1 = new Coupon(company.getId(), Category.FOOD, "freeSnacks", "salmonBites",
                    new Date(2021 - 1900, Calendar.AUGUST, 15), new Date(2024 - 1900, Calendar.AUGUST, 15), 70,
                    100, "snacks");
            companyFacade.addCoupon(coupon1);
            System.out.println("**New coupon**");
            System.out.println(companyFacade.getCompanyCoupons(1));
            coupon1.setDescription("freeCoffee");
            coupon1.setPrice(100);
            companyFacade.updateCoupon(coupon1,1);
            System.out.println("**Coupon by category**");
            System.out.println(companyFacade.getCompanyCoupons(Category.FOOD));
            System.out.println("**Coupon after update**");
            System.out.println(companyFacade.getCompanyCouponsByPrice(1000));
            System.out.println("**Company details**");
            System.out.println(companyFacade.getCompanyDetails());

            System.out.println("@@@-Customer facade-@@@");
            CustomerFacade customerFacade = (CustomerFacade) manager.login("Lesh@lesh.com", "12345678", ClientType.Customer);
            customerFacade.purchaseCoupon(coupon1);
            System.out.println("**Purchased coupon**");
            System.out.println(customerFacade.getCustomerCoupons());
            System.out.println("**Purchased coupon by category**");
            System.out.println(customerFacade.getCustomerCoupons(coupon1.getCategory()));
            System.out.println("**Purchased coupon by price**");;
            System.out.println(customerFacade.getCustomerCoupons(300));
            System.out.println("**Customer details**");
            System.out.println(customerFacade.getCustomerDetails());

            adminFacade.deleteCompany(1);
            adminFacade.deleteCustomer(1);
            companyFacade.deleteCoupon(1);

            ConnectionPool.getInstance().closeAll();
            dailyJob.stopped();
            thread.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
