import model.asn1.cdr.hlr.version3.CDRHLRVERSION3;
import service.Asn1ParserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Parser {
    public static void main(String[] args) {
        Asn1ParserService asn1ParserService = new Asn1ParserService();
        Optional<List<CDRHLRVERSION3>> optionalCdrhlrversion3s = asn1ParserService.parseAsn1(args);
        optionalCdrhlrversion3s.ifPresent(asn1ParserService::printResults);
    }
}
