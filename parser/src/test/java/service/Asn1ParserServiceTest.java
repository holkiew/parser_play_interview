package service;

import model.asn1.cdr.hlr.version3.CDRHLRVERSION3;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class Asn1ParserServiceTest {

    private Asn1ParserService asn1ParserService;

    @Before
    public void init() {
        asn1ParserService = new Asn1ParserService();
    }

    @Test
    public void parseAsn1ShouldReturnTwoRecords() {
        String[] args = {"'src/main/resources'", "'2017-12-11'", "'00:00:00'", "updateLocation", "'48\\d{9}'", "'.*'"};
        Optional<List<CDRHLRVERSION3>> optionalCdrhlrversion3s = asn1ParserService.parseAsn1(args);
        assertEquals(2, optionalCdrhlrversion3s.map(List::size).orElse(-1).intValue());
        asn1ParserService.printResults(optionalCdrhlrversion3s.get());
    }

    @Test
    public void parseAsn1ShouldReturnOneRecord() {
        String[] args = {"'src/main/resources'", "'2017-12-11'", "'00:00:00'", "updateLocation", "'48\\d{9}'", "'227'"};
        Optional<List<CDRHLRVERSION3>> optionalCdrhlrversion3s = asn1ParserService.parseAsn1(args);
        assertEquals(1, optionalCdrhlrversion3s.map(List::size).orElse(-1).intValue());
        asn1ParserService.printResults(optionalCdrhlrversion3s.get());
    }



}