package com.example.javanet.netty.http_xml;

public class OrderFactory {
	public static Order create(long orderID) {
		Order order = new Order();
		order.setOrderNumber(orderID);
		
		Customer customer = new Customer();
		customer.setCustomerNumber(5678);
		customer.setFirstName("俊");
		customer.setLastName("陈");
		customer.setMiddleNames(null);
		order.setCustomer(customer);
		
		Address billTo = new Address();
		billTo.setCity("广州市");
		billTo.setCountry("天河区");
		billTo.setPostCode("95535");
		billTo.setState("中国");
		billTo.setStreet1("东圃镇");
		billTo.setStreet2("园丁1路");
		order.setBillTo(billTo);
		
		order.setShipping(Shipping.DOMESTIC_EXPRESS);
		
		Address shipTo = new Address();
		shipTo.setCity("厦门市");
		shipTo.setCountry("思明区");
		shipTo.setPostCode("95535");
		shipTo.setState("中国");
		shipTo.setStreet1("厦门大学");
		shipTo.setStreet2("学生公寓");
		
		order.setShipTo(shipTo);
		order.setTotal(new Float(12345.00));
		
		order.setRemark("好高兴呐...");
		return order;
	}
}
