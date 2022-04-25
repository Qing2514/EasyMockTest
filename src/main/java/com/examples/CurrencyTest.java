package com.examples;

import junit.framework.TestCase;
import org.easymock.EasyMock;
// import org.easymock.classextension.EasyMock;

import java.io.IOException;

public class CurrencyTest extends TestCase {

    Currency testObject = new Currency(2.50, "USD");
    Currency expected = new Currency(3.75, "EUR");

    public void testToEuros() throws IOException {

        ExchangeRate mock = EasyMock.createMock(ExchangeRate.class);

        // EasyMock.anyInt()
        // EasyMock.anyShort()
        // EasyMock.anyByte()
        // EasyMock.anyLong()
        // EasyMock.anyFloat()
        // EasyMock.anyDouble()
        // EasyMock.anyBoolean()
        // EasyMock.eq()

        // EasyMock.expect(mock.getRate("USD", "EUR")).andReturn(1.5);
        // EasyMock.expect(mock.getRate((String) EasyMock.anyObject(), (String) EasyMock.anyObject())).andReturn(1.5);
        // EasyMock.expect(mock.getRate((String) EasyMock.notNull(), (String) EasyMock.notNull())).andReturn(1.5);
        EasyMock.expect(mock.getRate(
                (String) EasyMock.matches("[A-Z][A-Z][A-Z]"),
                (String) EasyMock.matches("[A-Z][A-Z][A-Z]"))).andReturn(1.5);
        EasyMock.replay(mock);

        Currency actual = testObject.toEuros(mock);
        assertEquals(expected, actual);
        // 检查是否只调用 getRate() 一次
        EasyMock.verify(mock);
    }

    public void testExchangeRateServerUnavailable() throws IOException {
        ExchangeRate mock = EasyMock.createMock(ExchangeRate.class);
        EasyMock.expect(mock.getRate("USD", "EUR")).andThrow(new IOException());
        EasyMock.replay(mock);
        Currency actual = testObject.toEuros(mock);
        assertNull(actual);
    }

}