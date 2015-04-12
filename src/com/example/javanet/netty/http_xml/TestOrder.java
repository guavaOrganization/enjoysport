package com.example.javanet.netty.http_xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

public class TestOrder {
	private IBindingFactory factory = null;
	private StringWriter writer = null;
	private StringReader reader = null;
	private final static String CHARSET_NAME = "UTF-8";

	private String encode2Xml(Order order) throws JiBXException, IOException {
		factory = BindingDirectory.getFactory(Order.class);
		writer = new StringWriter();
		IMarshallingContext mctx = factory.createMarshallingContext();
		mctx.setIndent(2);
		mctx.marshalDocument(order, CHARSET_NAME, null, writer);
		String xmlStr = writer.toString();
		writer.close();
		System.out.println(xmlStr);
		return xmlStr;
	}

	private Order decode2Order(String xmlBody) throws JiBXException {
		reader = new StringReader(xmlBody);
		IUnmarshallingContext ctx = factory.createUnmarshallingContext();
		return (Order) ctx.unmarshalDocument(reader);
	}

	public static void main(String[] args) throws JiBXException, IOException {
		TestOrder test = new TestOrder();
		Order order = new Order();
		order.setOrderNumber(1234);
		
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
		
		String body = test.encode2Xml(order);
		Order o = test.decode2Order(body);
		
		System.out.println(o);
	}
}
