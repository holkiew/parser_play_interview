package model.pojo;

import lombok.Getter;
import lombok.Setter;
import model.asn1.datatypes.CdrType;

@Getter
@Setter
public class InputArguments {
    private String inputDir;
    private long minCdrDate;
    private CdrType.CdrTypes cdrType;
    private String msisdnRegex;
    private String imsiRegex;
}
