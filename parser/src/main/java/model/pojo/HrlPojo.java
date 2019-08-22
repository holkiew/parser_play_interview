package model.pojo;

import lombok.Getter;
import model.asn1.cdr.hlr.version3.CDRHLRVERSION3;
import model.asn1.datatypes.CdrType;
import util.Utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public class HrlPojo {
    private String imsi;
    private String type;
    private String msisdn;
    private String vlr;
    private String timestamp;

    public HrlPojo(CDRHLRVERSION3 cdrhlrversion3) {
        this.imsi = Utils.octalStrToString(cdrhlrversion3.getImsi().value);
        this.msisdn = Utils.octalStrToString(cdrhlrversion3.getMsisdn().value);
        Optional<CdrType.CdrTypes> first = Stream.of(CdrType.CdrTypes.values()).filter(cdrTypes -> cdrTypes.value == cdrhlrversion3.getType().value.intValue()).findFirst();
        this.type = first.get().name;
        this.vlr = Utils.octalStrToString(cdrhlrversion3.getVlr().value);
        this.timestamp = getTimestamp(cdrhlrversion3);
    }

    private String getTimestamp(CDRHLRVERSION3 cdrhlrversion3) {
        String timestampLongString = Utils.octalStrToString(cdrhlrversion3.getTimestamp().value);
        Long timestampLong = Long.parseLong(timestampLongString);
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestampLong).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern(Utils.DATE_FORMAT));
    }
}
