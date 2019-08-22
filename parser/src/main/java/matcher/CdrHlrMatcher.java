package matcher;

import model.asn1.cdr.hlr.version3.CDRHLRVERSION3;
import model.pojo.InputArguments;
import util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CdrHlrMatcher {
    private InputArguments inputArguments;

    public CdrHlrMatcher(InputArguments inputArguments) {
        this.inputArguments = inputArguments;
    }

    public boolean isMatching(CDRHLRVERSION3 cdrhlrversion3) {
        if (isDateMatching(cdrhlrversion3)) {
            if (isCdrTypeMatching(cdrhlrversion3)) {
                if (isRegexMatching(inputArguments.getMsisdnRegex(), cdrhlrversion3.getMsisdn().value)) {
                    return isRegexMatching(inputArguments.getImsiRegex(), cdrhlrversion3.getImsi().value);
                }
            }
        }
        return false;
    }

    private boolean isDateMatching(CDRHLRVERSION3 cdrhlrversion3) {
        long cdrhlrTime = Long.parseLong(Utils.octalStrToString(cdrhlrversion3.getTimestamp().value));
        return inputArguments.getMinCdrDate() <= cdrhlrTime;
    }

    private boolean isCdrTypeMatching(CDRHLRVERSION3 cdrhlrversion3) {
        return inputArguments.getCdrType().value == cdrhlrversion3.getType().value.intValue();
    }

    private boolean isRegexMatching(String pattern, byte[] buffer) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(Utils.octalStrToString(buffer));
        return matcher.find();
    }
}
